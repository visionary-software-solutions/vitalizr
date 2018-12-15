package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.BloodSugar;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodSugarQueryIntegrationTest {
    @Test
    void canQueryBloodOxygenForTimeRange() {
        // Given: A person to retrieve blood sugar for
        final Person mom = Fixtures.person();
        // And: that person measured their blood sugar 2 weeks ago
        final BloodSugar first = Fixtures.bloodSugarAt(92, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeBloodSugarFor(first);
        // And: that person measured their blood sugar 3 days ago
        final BloodSugar second = Fixtures.bloodSugarAt(94, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeBloodSugarFor(second);
        // And: that person measured their blood sugar 2 days ago
        final BloodSugar third = Fixtures.bloodSugarAt(96, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeBloodSugarFor(third);
        // And: that person measured their blood sugar 1 days ago
        final BloodSugar fourth = Fixtures.bloodSugarAt(97, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeBloodSugarFor(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the blood sugars
        final Collection<BloodSugar> result = Vitalizr.getBloodSugarsInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth blood sugar is stored
        assertTrue(result.contains(fourth));
        // And: the third blood sugar is stored
        assertTrue(result.contains(third));
        // And: the second blood sugar is stored
        assertTrue(result.contains(second));
        // And: the first blood sugar is not stored
        assertFalse(result.contains(first));
    }
}
