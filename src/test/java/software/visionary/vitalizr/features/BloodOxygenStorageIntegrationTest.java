package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.oxygen.PersonBloodOxygen;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodOxygenStorageIntegrationTest {
    @Test
    void canStoreBloodOxygen() {
        // Given: A blood oxygen at a particular point in time and quantity to be stored
        final BloodOxygen toStore = Fixtures.bloodOxygen();
        // And: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // When: I call store
        Vitalizr.storeBloodOxygenFor(mom, toStore);
        // Then: the weight is stored
        final Collection<PersonBloodOxygen> stored = Vitalizr.getBloodOxygensFor(mom);
        assertTrue(stored.contains(new PersonBloodOxygen(mom, toStore)));
    }
}
