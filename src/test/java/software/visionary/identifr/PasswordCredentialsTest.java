package software.visionary.identifr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.Randomizr;
import software.visionary.identifr.api.Authenticatable;
import software.visionary.identifr.api.Credentials;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PasswordCredentialsTest {
    @Test
    void rejectsNullOwner() {
        final Authenticatable owner = null;
        final String password = Randomizr.INSTANCE.createRandomPassword();
        Assertions.assertThrows(NullPointerException.class, () -> new PasswordCredentials(owner, password));
    }

    @Test
    void rejectsNullPassword() {
        final Authenticatable owner = new Authenticatable() {
            @Override
            public Credentials getCredentials() {
                return null;
            }

            @Override
            public UUID getID() {
                return null;
            }
        };
        final String password = null;
        Assertions.assertThrows(NullPointerException.class, () -> new PasswordCredentials(owner, password));
    }

    @Test
    void rejectsEmptyPassword() {
        final Authenticatable owner = new Authenticatable() {
            @Override
            public Credentials getCredentials() {
                return null;
            }

            @Override
            public UUID getID() {
                return null;
            }
        };
        final String password = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PasswordCredentials(owner, password));
    }

    @Test
    void rejectsWhitespacePassword() {
        final Authenticatable owner = new Authenticatable() {
            @Override
            public Credentials getCredentials() {
                return null;
            }

            @Override
            public UUID getID() {
                return null;
            }
        };
        final String password = "\t\t\n\n\n";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PasswordCredentials(owner, password));
    }

    @Test
    void hashCodeIsImplementedCorrectly() {
        final Authenticatable owner = Fixtures.randomAuthenticatable();
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final PasswordCredentials creds = new PasswordCredentials(owner, password);
        final int firstHash = creds.hashCode();
        final int secondHash = creds.hashCode();
        Assertions.assertEquals(firstHash, secondHash);
        final PasswordCredentials same = new PasswordCredentials(owner, password);
        final int sameHash = same.hashCode();
        Assertions.assertEquals(firstHash, sameHash);
        final PasswordCredentials different = new PasswordCredentials(owner, Randomizr.INSTANCE.createRandomPassword());
        Assertions.assertNotEquals(firstHash, different.hashCode());
    }

    @Test
    void equalsIsImplementedCorrectly() {
        final Authenticatable owner = Fixtures.randomAuthenticatable();
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final PasswordCredentials creds = new PasswordCredentials(owner, password);
        Assertions.assertTrue(creds.equals(creds));
        final PasswordCredentials same = new PasswordCredentials(owner, password);
        Assertions.assertTrue(creds.equals(same));
        Assertions.assertTrue(same.equals(creds));
        final PasswordCredentials different = new PasswordCredentials(owner, Randomizr.INSTANCE.createRandomPassword());
        Assertions.assertFalse(creds.equals(different));
        Assertions.assertFalse(different.equals(creds));
    }

    @Test
    void canGetSalt() {
        final Authenticatable owner = Fixtures.randomAuthenticatable();
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final PasswordCredentials creds = new PasswordCredentials(owner, password);
        final byte[] salt1 = creds.getSalt();
        final byte[] salt2 = creds.getSalt();
        Assertions.assertFalse(Arrays.equals(salt1, salt2));
    }

    @Test
    void canEncryptDataWithStoredCredentials() {
        final Authenticatable owner = Fixtures.randomAuthenticatable();
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final PasswordCredentials creds = new PasswordCredentials(owner, password);
        // And: some data
        final CharSequence toWrite = Randomizr.INSTANCE.createRandomAlphabeticString();
        // When: I encrypt the data
        final byte[] result = creds.encrypt(toWrite.toString().getBytes());
        // Then: The result is not the same
        Assertions.assertFalse(Arrays.equals(result, toWrite.toString().getBytes()));
        // And: When I decrypt
        final byte[] decrypt = creds.decrypt(result);
        // Then: I get back the original
        final String decrypted = new String(decrypt, StandardCharsets.UTF_8);
        assertEquals(toWrite, decrypted);
    }
}
