package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListWeightsTest {
    @Test
    void canRetrieveVital() {
        final Double weight = 232.6;
        final Person p = Fixtures.createRandomPerson();
        final Weight saved = new ImperialWeight(Instant.now(), weight, p);
        Vitalizr.storeWeight(saved);
        final ListWeights action = new ListWeights();
        final Collection<Weight> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(weight)));
    }
}
