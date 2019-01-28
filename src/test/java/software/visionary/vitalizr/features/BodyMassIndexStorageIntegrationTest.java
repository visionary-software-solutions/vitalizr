package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyMassIndexStorageIntegrationTest {
    @Test
    void canStoreBodyMassIndex() {
        // Given: A person to store Body Mass Index for
        final Person mom = Fixtures.person();
        // And: A Body Mass Index at a particular point in time and quantity to be stored
        final BodyMassIndex toStore = Fixtures.bmiAt(43.1, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeBodyMassIndexFor(toStore);
        // Then: the weight is stored
        final Collection<BodyMassIndex> stored = Vitalizr.getBodyMassIndicesFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
