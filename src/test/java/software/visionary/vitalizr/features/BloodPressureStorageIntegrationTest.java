package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodPressureStorageIntegrationTest {
    @Test
    void canStoreBloodPressure() {
        // Given: A systolic blood pressure at a particular point in time and quantity to be stored
        final BloodPressure toStore = Fixtures.bloodPressure();
        // And: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // When: I call store
        Vitalizr.storeBloodPressureFor(mom, toStore);
        // Then: the weight is stored
        final Collection<PersonBloodPressure> stored = Vitalizr.getBloodPressuresFor(mom);
        assertTrue(stored.contains(new PersonBloodPressure(mom, toStore)));
    }
}
