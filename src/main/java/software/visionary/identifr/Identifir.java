package software.visionary.identifr;

import software.visionary.identifr.api.Authenticatable;
import software.visionary.identifr.api.Credentials;
import software.visionary.vitalizr.api.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

public enum  Identifir {
    INSTANCE;

    private final Repository<Authenticatable> store;

    Identifir() {
        store = new Repository<>() {
            private final List<Authenticatable> listy = new ArrayList<>();

            @Override
            public void save(final Authenticatable toSave) {
                listy.add(toSave);
            }

            @Override
            public void accept(final Consumer<Authenticatable> visitor) {
                listy.forEach(visitor);
            }
        };
    }

    public static void loadFromSecureLocation() {
        final String credentialsLocation = System.getProperty("visionary.software.identifier.credentials");
        if (credentialsLocation == null) {
            return;
        }
        final Properties credentials = new Properties();
        try {
            credentials.load(Files.newInputStream(Paths.get(credentialsLocation)));
            credentials.forEach((key, value) -> {
                final Authenticatable principal = createAuthenticatable(key, value);
                INSTANCE.storeAuthenticatable(principal);
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Authenticatable createAuthenticatable(final Object key, final Object value) {
        final String password = value.toString();
        final UUID id = UUID.fromString(key.toString());
        return new Authenticatable() {
            @Override
            public Credentials getCredentials() {
                return new PasswordCredentials(this, password);
            }

            @Override
            public UUID getID() {
                return id;
            }
        };
    }

    void storeAuthenticatable(final Authenticatable toStore) {
        store.save(toStore);
    }

    Optional<Authenticatable> loadAuthenticatable(final UUID id) {
        final List<Authenticatable> found = new ArrayList<>();
        store.accept(authenticatable -> {
            if (authenticatable.getID().equals(id)) {
                found.add(authenticatable);
            }
        });
        return found.stream().findFirst();
    }

    byte[] encryptFor(final UUID id, final byte[] data) {
        final Optional<Authenticatable> sought = loadAuthenticatable(id);
        return sought.map(a -> a.getCredentials().encrypt(data)).orElse(new byte[0]);
    }

    byte[] decryptFor(final UUID id, final byte[] result) {
        final Optional<Authenticatable> sought = loadAuthenticatable(id);
        return sought.map(a -> a.getCredentials().decrypt(result)).orElse(new byte[0]);
    }

    boolean authenticateWithPassword(final UUID id, final String password) {
        final Optional<Authenticatable> sought = loadAuthenticatable(id);
        return sought.map(a -> new PasswordCredentials(a, password).equals(a.getCredentials())).orElse(false);
    }
}
