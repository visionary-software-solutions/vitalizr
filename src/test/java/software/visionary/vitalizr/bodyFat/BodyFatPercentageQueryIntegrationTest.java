package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyFatPercentageQueryIntegrationTest {
    @Test
    void canQueryBodyFatPercentageForTimeRange() {
        // Given: A person to retrieve body Fat percentage  for
        final Person mom = Fixtures.person();
        // And: that person measured their body Fat percentage 2 weeks ago
        final BodyFatPercentage first = Fixtures.bodyFatPercentageAt(50.5, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeBodyFatPercentage(first);
        // And: that person measured their body Fat percentage  3 days ago
        final BodyFatPercentage second = Fixtures.bodyFatPercentageAt(51.2, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeBodyFatPercentage(second);
        // And: that person measured their body Fat percentage  2 days ago
        final BodyFatPercentage third = Fixtures.bodyFatPercentageAt(50.8, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeBodyFatPercentage(third);
        // And: that person measured their body Fat percentage  1 days ago
        final BodyFatPercentage fourth = Fixtures.bodyFatPercentageAt(49.2, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeBodyFatPercentage(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the body Fat percentage
        final Collection<BodyFatPercentage> result = Vitalizr.getBodyFatPercentagesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth body Fat percentage is stored
        assertTrue(result.contains(fourth));
        // And: the third body Fat percentage is stored
        assertTrue(result.contains(third));
        // And: the second body Fat percentage is stored
        assertTrue(result.contains(second));
        // And: the first body Fat percentage is not stored
        assertFalse(result.contains(first));
    }
}
