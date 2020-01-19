package software.visionary.vitalizr.features;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyTemperature.MetricTemperature;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyTemperatureStorageIntegrationTest {

    private static final Person PERSON = Fixtures.person();

    @ParameterizedTest
    @MethodSource
    void canStoreBodyTemperature(final BodyTemperature toStore) {
        // Given: A person to store temperature for
        // And: A blood oxygen at a particular point in time and quantity to be stored
        // When: I call store
        Vitalizr.storeTemperature(toStore);
        // Then: the weight is stored
        final Collection<BodyTemperature> stored = Vitalizr.getBodyTemperaturesFor(PERSON);
        assertTrue(stored.contains(toStore));
    }

    private static Stream<BodyTemperature> canStoreBodyTemperature() {
        return Stream.of(
                Fixtures.temperatureAt(97.9, Instant.now(), PERSON),
                new MetricTemperature(PERSON, 36.61, Instant.now())
                );
    }
}
