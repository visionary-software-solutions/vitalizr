package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodPressureStorageIntegrationTest {
    @Test
    void canStoreBloodPressure() {
        // Given: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // And: A blood pressure at a particular point in time and quantity to be stored
        final BloodPressure toStore = Combined.systolicAndDiastolicBloodPressure(Instant.now(), 153, 80, mom);
        // When: I call store
        Vitalizr.storeBloodPressureFor(toStore);
        // Then: the weight is stored
        final Collection<BloodPressure> stored = Vitalizr.getBloodPressuresFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
