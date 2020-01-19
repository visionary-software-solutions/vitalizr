package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodOxygenStorageIntegrationTest {
    @Test
    void canStoreBloodOxygen() {
        // Given: A person to store blood oxygen for
        final Person mom = Fixtures.person();
        // And: A blood oxygen at a particular point in time and quantity to be stored
        final BloodOxygen toStore = Fixtures.oxygenAt(94, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeBloodOxygenFor(toStore);
        // Then: the weight is stored
        final Collection<BloodOxygen> stored = Vitalizr.getBloodOxygensFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
