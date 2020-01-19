package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricWeightQueryIntegrationTest {
    @Test
    void canQueryWeightForTimeRange() {
        // Given: A person to retrieve weight for
        final Person mom = Fixtures.person();
        // And: that person measured their weight 2 weeks ago
        final Weight first = MetricWeight.inKilograms(107, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeWeightFor(first);
        // And: that person measured their weight 3 days ago
        final Weight second = MetricWeight.inKilograms(105, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeWeightFor(second);
        // And: that person measured their weight 2 days ago
        final Weight third = MetricWeight.inKilograms(103, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeWeightFor(third);
        // And: that person measured their weight 1 days ago
        final Weight fourth = MetricWeight.inKilograms(104, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeWeightFor(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the weights
        final Collection<Weight> result = Vitalizr.getWeightsInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth weight is stored
        assertTrue(result.contains(fourth));
        // And: the third weight is stored
        assertTrue(result.contains(third));
        // And: the second weight is stored
        assertTrue(result.contains(second));
        // And: the first weight is not stored
        assertFalse(result.contains(first));
    }
}
