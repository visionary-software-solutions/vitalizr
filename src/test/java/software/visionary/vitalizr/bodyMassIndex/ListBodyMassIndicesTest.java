package software.visionary.vitalizr.bodyMassIndex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBodyMassIndicesTest {
    @Test
    void canRetrieveVital() {
        final Double bmi = 33.1;
        final Person p = Fixtures.createRandomPerson();
        final BodyMassIndex saved = new QueteletIndex(Instant.now(), bmi, p);
        Vitalizr.storeBodyMassIndex(saved);
        final ListBodyMassIndices action = new ListBodyMassIndices();
        final Collection<BodyMassIndex> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(bmi)));
    }
}
