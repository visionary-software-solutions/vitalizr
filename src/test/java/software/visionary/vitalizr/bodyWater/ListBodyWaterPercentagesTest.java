package software.visionary.vitalizr.bodyWater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBodyWaterPercentagesTest {
    @Test
    void canRetrieveVital() {
        final Double fatPercentage = 28.2;
        final Person p = Fixtures.createRandomPerson();
        final BodyWaterPercentage saved = new BioelectricalImpedance(Instant.now(), fatPercentage, p);
        Vitalizr.storeBodyWaterPercentage(saved);
        final ListBodyWaterPercentages action = new ListBodyWaterPercentages();
        final Collection<BodyWaterPercentage> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
