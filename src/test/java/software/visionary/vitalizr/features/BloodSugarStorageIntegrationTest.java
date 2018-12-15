package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.BloodSugar;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodSugarStorageIntegrationTest {
    @Test
    void canStoreBloodSugar() {
        // Given: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // And: A blood oxygen at a particular point in time and quantity to be stored
        final BloodSugar toStore = Fixtures.bloodSugarAt(137, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeBloodSugarFor(toStore);
        // Then: the weight is stored
        final Collection<BloodSugar> stored = Vitalizr.getBloodSugarsFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
