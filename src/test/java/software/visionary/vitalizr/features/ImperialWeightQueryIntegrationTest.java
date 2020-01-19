package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.ImperialWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImperialWeightQueryIntegrationTest {
    @Test
    void canQueryWeightForTimeRange() {
        // Given: A person to retrieve weight for
        final Person mom = Fixtures.person();
        // And: that person measured their weight 2 weeks ago
        final Weight first = new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(14), 199, mom);
        Vitalizr.storeWeightFor(first);
        // And: that person measured their weight 3 days ago
        final Weight second = new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(3), 207, mom);
        Vitalizr.storeWeightFor(second);
        // And: that person measured their weight 2 days ago
        final Weight third = new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(2), 205, mom);
        Vitalizr.storeWeightFor(third);
        // And: that person measured their weight 1 days ago
        final Weight fourth = new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(1), 203.8, mom);
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
