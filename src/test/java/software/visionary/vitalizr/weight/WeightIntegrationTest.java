package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightIntegrationTest {
    @Test
    void canStoreWeight() {
        // Given: A weight at a particular point in time and quantity to be stored
        final Weight toStore = Fixtures.weight();
        // And: A configured Application
        final Person mom = Fixtures.person();
        // When: I call store
        Vitalizr.storeWeightFor(mom, toStore);
        // Then: the weight is stored
        Collection<PersonWeight> stored = Vitalizr.getWeightsFor(mom);
        assertTrue(stored.contains(new PersonWeight(mom, toStore)));
    }
}
