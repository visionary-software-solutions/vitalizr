package software.visionary.identifr;

import software.visionary.identifr.api.Authenticatable;
import software.visionary.vitalizr.api.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
