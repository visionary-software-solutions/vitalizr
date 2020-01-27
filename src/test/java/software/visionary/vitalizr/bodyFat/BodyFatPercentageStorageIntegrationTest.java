package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyFatPercentageStorageIntegrationTest {
    @Test
    void canStoreBodyFat() {
        // Given: A person to store Body water for
        final Person mom = Fixtures.person();
        // And: A Body Water Percentage at a particular point in time and quantity to be stored
        final BodyFatPercentage toStore = Fixtures.bodyFatPercentageAt(50.2, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeBodyFatPercentage(toStore);
        // Then: the weight is stored
        final Collection<BodyFatPercentage> stored = Vitalizr.getBodyFatPercentagesFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
