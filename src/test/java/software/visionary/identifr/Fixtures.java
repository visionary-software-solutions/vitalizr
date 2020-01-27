package software.visionary.identifr;

import software.visionary.Randomizr;
import software.visionary.identifr.api.Authenticatable;
import software.visionary.identifr.api.Credentials;

import java.util.UUID;

public enum Fixtures {
    INSTANCE;

    static Authenticatable randomAuthenticatable() {
        final UUID uuid = UUID.randomUUID();
        final String randomPassword = Randomizr.INSTANCE.createRandomPassword();
        return new Authenticatable() {
            @Override
            public Credentials getCredentials() {
                return new PasswordCredentials(this, randomPassword);
            }

            @Override
            public UUID getID() {
                return uuid;
            }
        };
    }
}
