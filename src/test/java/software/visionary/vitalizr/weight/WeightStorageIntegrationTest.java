package software.visionary.vitalizr.weight;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightStorageIntegrationTest {

    public static final Person PERSON = Fixtures.person();

    @ParameterizedTest
    @MethodSource
    void canStoreWeight(final Weight weight) {
        // Given: A person to store weight for
        // Given: A weight at a particular point in time and quantity to be stored
        // When: I call store
        Vitalizr.storeWeight(weight);
        // Then: the weight is stored
        final Collection<Weight> stored = Vitalizr.getWeightsFor(PERSON);
        assertTrue(stored.contains(weight));
    }

    private static Stream<Weight> canStoreWeight() {
        return Stream.of(
                new ImperialWeight(Instant.now(), 203.8, PERSON),
                new MetricWeight(Instant.now(), 100.1, PERSON)
        );
    }
}
