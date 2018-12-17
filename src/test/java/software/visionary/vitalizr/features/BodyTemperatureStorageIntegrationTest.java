package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyTemperatureStorageIntegrationTest {
    @Test
    void canStoreBodyTemperature() {
        // Given: A person to store temperature for
        final Person mom = Fixtures.person();
        // And: A blood oxygen at a particular point in time and quantity to be stored
        final BodyTemperature toStore = Fixtures.temperatureAt(97.9, Instant.now(), mom);
        // When: I call store
        Vitalizr.storeTemperature(toStore);
        // Then: the weight is stored
        final Collection<BodyTemperature> stored = Vitalizr.getBodyTemperaturesFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
