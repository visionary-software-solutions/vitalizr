package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.ImperialWeight;
import software.visionary.vitalizr.weight.Weight;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ImperialWeightStorageIntegrationTest {
    @Test
    void canStoreWeight() {
        // Given: A person to store weight for
        final Person mom = Fixtures.person();
        // Given: A weight at a particular point in time and quantity to be stored
        final Weight toStore = new ImperialWeight(Instant.now(), 203.8, mom);
        // When: I call store
        Vitalizr.storeWeightFor(toStore);
        // Then: the weight is stored
        final Collection<Weight> stored = Vitalizr.getWeightsFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
