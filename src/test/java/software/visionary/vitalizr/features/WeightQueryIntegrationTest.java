package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.Gram;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightQueryIntegrationTest {
    @Test
    void canQueryWeightForTimeRange() {
        // Given: A person to retrieve weight for
        final Person mom = Fixtures.person();
        // And: that person measured their weight 2 weeks ago
        final Weight first = new Weight(LocalDate.now().minusDays(14).atStartOfDay().toInstant(ZoneOffset.UTC), 235, new Gram());
        Vitalizr.storeWeightFor(mom, first);
        // And: that person measured their weight 3 days ago
        final Weight second = new Weight(LocalDate.now().minusDays(3).atStartOfDay().toInstant(ZoneOffset.UTC), 231, new Gram());
        Vitalizr.storeWeightFor(mom, second);
        // And: that person measured their weight 2 days ago
        final Weight third = new Weight(LocalDate.now().minusDays(2).atStartOfDay().toInstant(ZoneOffset.UTC), 229, new Gram());
        Vitalizr.storeWeightFor(mom, third);
        // And: that person measured their weight 1 days ago
        final Weight fourth = new Weight(LocalDate.now().minusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC), 230, new Gram());
        Vitalizr.storeWeightFor(mom, fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Interval.of(LocalDate.now().minusDays(7).atStartOfDay().toInstant(ZoneOffset.UTC), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
        // When: I fetch the weights
        final Collection<PersonWeight> result = Vitalizr.getWeightsInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth weight is stored
        assertTrue(result.contains(new PersonWeight(mom, fourth)));
        // And: the third weight is stored
        assertTrue(result.contains(new PersonWeight(mom, third)));
        // And: the second weight is stored
        assertTrue(result.contains(new PersonWeight(mom, second)));
        // And: the first weight is not stored
        assertFalse(result.contains(new PersonWeight(mom, first)));
    }
}
