package software.visionary.identifr;

import org.junit.jupiter.api.Test;
import software.visionary.Randomizr;
import software.visionary.identifr.api.Authenticatable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticateWithPasswordIntegrationTest {
    @Test
    void canAuthenticateWithPassword() {
        // Given : A stored Authenticatable
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final Authenticatable toStore = Fixtures.createAuthenticatable(UUID.randomUUID(), password);
        Identifir.INSTANCE.storeAuthenticatable(toStore);
        // When: I try to authenticate with the password
        final boolean result = Identifir.INSTANCE.authenticateWithPassword(toStore.getID(), password);
        // Then: authentication is successful
        assertTrue(result);
    }

    @Test
    void nonExistantUserCannotAuthenticate() {
        // Given : A stored Authenticatable
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final Authenticatable toStore = Fixtures.createAuthenticatable(UUID.randomUUID(), password);
        // When: I try to authenticate with the password
        final boolean result = Identifir.INSTANCE.authenticateWithPassword(toStore.getID(), password);
        // Then: authentication is successful
        assertFalse(result);
    }

    @Test
    void cannotAuthenticateWithWrongPassword() {
        // Given : A stored Authenticatable
        final String password = Randomizr.INSTANCE.createRandomPassword();
        final Authenticatable toStore = Fixtures.createAuthenticatable(UUID.randomUUID(), password);
        Identifir.INSTANCE.storeAuthenticatable(toStore);
        // When: I try to authenticate with the password
        final boolean result = Identifir.INSTANCE.authenticateWithPassword(toStore.getID(), Randomizr.INSTANCE.createRandomPassword());
        // Then: authentication is successful
        assertFalse(result);
    }
}
