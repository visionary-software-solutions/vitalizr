package software.visionary.vitalizr.bodyWater;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyWaterPercentageStorageIntegrationTest {
    @Test
    void canStoreBodyWater() {
        // Given: A person to store Body water for
        final Person mom = Fixtures.person();
        // And: A Body Water Percentage at a particular point in time and quantity to be stored
        final BodyWaterPercentage toStore = Fixtures.bodyWaterPercentageAt(50.2, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeBodyWaterPercentage(toStore);
        // Then: the weight is stored
        final Collection<BodyWaterPercentage> stored = Vitalizr.getBodyWaterPercentagesFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
