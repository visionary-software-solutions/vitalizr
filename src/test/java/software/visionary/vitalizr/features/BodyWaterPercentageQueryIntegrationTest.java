package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyWaterPercentageQueryIntegrationTest {
    @Test
    void canQueryBodyWaterPercentageForTimeRange() {
        // Given: A person to retrieve body water percentage  for
        final Person mom = Fixtures.person();
        // And: that person measured their body water percentage 2 weeks ago
        final BodyWaterPercentage first = Fixtures.bodyWaterPercentageAt(50.5, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeBodyWaterPercentageFor(first);
        // And: that person measured their body water percentage  3 days ago
        final BodyWaterPercentage second = Fixtures.bodyWaterPercentageAt(51.2, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeBodyWaterPercentageFor(second);
        // And: that person measured their body water percentage  2 days ago
        final BodyWaterPercentage third = Fixtures.bodyWaterPercentageAt(50.8, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeBodyWaterPercentageFor(third);
        // And: that person measured their body water percentage  1 days ago
        final BodyWaterPercentage fourth = Fixtures.bodyWaterPercentageAt(49.2, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeBodyWaterPercentageFor(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the body water percentage
        final Collection<BodyWaterPercentage> result = Vitalizr.getBodyWaterPercentagesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth body water percentage is stored
        assertTrue(result.contains(fourth));
        // And: the third body water percentage is stored
        assertTrue(result.contains(third));
        // And: the second body water percentage is stored
        assertTrue(result.contains(second));
        // And: the first body water percentage is not stored
        assertFalse(result.contains(first));
    }
}
