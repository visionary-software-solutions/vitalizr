package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBodyFatPercentagesTest {
    @Test
    void canRetrieveVital() {
        final Double fatPercentage = 28.2;
        final Person p = Fixtures.createRandomPerson();
        final BodyFatPercentage saved = new BioelectricalImpedance(Instant.now(), fatPercentage, p);
        Vitalizr.storeBodyFatPercentage(saved);
        final ListBodyFatPercentages action = new ListBodyFatPercentages();
        final Collection<BodyFatPercentage> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
