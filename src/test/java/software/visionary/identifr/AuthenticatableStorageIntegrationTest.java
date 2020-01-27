package software.visionary.identifr;

import org.junit.jupiter.api.Test;
import software.visionary.identifr.api.Authenticatable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticatableStorageIntegrationTest {
    @Test
    void canStoreAuthenticatable() {
        // Given: An Authenticatble to store
        final Authenticatable toStore = Fixtures.randomAuthenticatable();
        // When: I call store
        Identifir.INSTANCE.storeAuthenticatable(toStore);
        // Then: the Authenticatable is stored
        final Optional<Authenticatable> stored = Identifir.INSTANCE.loadAuthenticatable(toStore.getID());
        assertTrue(stored.isPresent());
        final Authenticatable result = stored.get();
        assertEquals(result.getID(), toStore.getID());
        assertEquals(result.getCredentials(), toStore.getCredentials());
    }

}
