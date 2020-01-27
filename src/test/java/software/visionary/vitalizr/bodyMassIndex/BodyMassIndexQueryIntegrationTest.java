package software.visionary.vitalizr.bodyMassIndex;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyMassIndexQueryIntegrationTest {
    @Test
    void canQueryBodyMassIndexForTimeRange() {
        // Given: A person to retrieve BMI  for
        final Person mom = Fixtures.person();
        // And: that person measured their BMI 2 weeks ago
        final BodyMassIndex first = Fixtures.bmiAt(33.9, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeBodyMassIndex(first);
        // And: that person measured their BMI  3 days ago
        final BodyMassIndex second = Fixtures.bmiAt(33.4, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeBodyMassIndex(second);
        // And: that person measured their BMI  2 days ago
        final BodyMassIndex third = Fixtures.bmiAt(33.1, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeBodyMassIndex(third);
        // And: that person measured their BMI  1 days ago
        final BodyMassIndex fourth = Fixtures.bmiAt(33.0, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeBodyMassIndex(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the O2s
        final Collection<BodyMassIndex> result = Vitalizr.getBodyMassIndicesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth O2 is stored
        assertTrue(result.contains(fourth));
        // And: the third O2 is stored
        assertTrue(result.contains(third));
        // And: the second O2 is stored
        assertTrue(result.contains(second));
        // And: the first O2 is not stored
        assertFalse(result.contains(first));
    }
}
