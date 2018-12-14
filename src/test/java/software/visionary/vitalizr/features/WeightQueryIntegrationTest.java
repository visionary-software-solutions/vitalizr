package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightQueryIntegrationTest {
    @Test
    void canQueryWeightForTimeRange() {
        // Given: A person to retrieve weight for
        final Person mom = Fixtures.person();
        // And: that person measured their weight 2 weeks ago
        final Weight first = Weight.inKilograms(235, Fixtures.observationAtMidnightNDaysAgo(14));
        Vitalizr.storeWeightFor(mom, first);
        // And: that person measured their weight 3 days ago
        final Weight second = Weight.inKilograms(231, Fixtures.observationAtMidnightNDaysAgo(3));
        Vitalizr.storeWeightFor(mom, second);
        // And: that person measured their weight 2 days ago
        final Weight third = Weight.inKilograms(229, Fixtures.observationAtMidnightNDaysAgo(2));
        Vitalizr.storeWeightFor(mom, third);
        // And: that person measured their weight 1 days ago
        final Weight fourth = Weight.inKilograms(230, Fixtures.observationAtMidnightNDaysAgo(1));
        Vitalizr.storeWeightFor(mom, fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
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
