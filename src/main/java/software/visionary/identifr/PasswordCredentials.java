package software.visionary.identifr;

import software.visionary.identifr.api.Authenticatable;
import software.visionary.identifr.api.Credentials;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Objects;

public class PasswordCredentials implements Credentials {
    private final Authenticatable owner;
    private final byte[] value;
    private final SecretKey key;

    public PasswordCredentials(final Authenticatable human, final String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.owner = Objects.requireNonNull(human);
        this.key = asSecretKey(password);
        this.value = this.key.getEncoded();
    }

    private SecretKey asSecretKey(final String password) {
        try {
            final PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndTripleDES");
            return secretKeyFactory.generateSecret(pbeKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PasswordCredentials that = (PasswordCredentials) o;
        return owner.equals(that.owner) &&
                Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, value);
    }

    @Override
    public byte[] encrypt(final byte[] source) {
        try(final ByteArrayInputStream in = new ByteArrayInputStream(source);
            final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final byte[] salt = getSalt();
            final PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
            final Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            out.write(salt);
            final byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = in.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) {
                    out.write(output);
                }
            }

            final byte[] output = cipher.doFinal();
            if (output != null) {
                out.write(output);
            }
            return out.toByteArray();
        } catch (final IOException | NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getSalt() {
        final byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    @Override
    public byte[] decrypt(final byte[] source) {
        try (final InputStream fis = new ByteArrayInputStream(source);
             final ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            final byte[] salt = new byte[8];
            fis.read(salt);

            final PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);

            final Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);

            final byte[] in = new byte[64];
            int read;
            while ((read = fis.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null) {
                    fos.write(output);
                }
            }

            final byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }
            return fos.toByteArray();
        } catch (final IOException | IllegalBlockSizeException | BadPaddingException
                | NoSuchPaddingException | InvalidAlgorithmParameterException
                | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
