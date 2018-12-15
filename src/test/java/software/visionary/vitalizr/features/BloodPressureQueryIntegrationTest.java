package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodPressureQueryIntegrationTest {
    @Test
    void canQueryBloodPressureForTimeRange() {
        // Given: A person to retrieve BP for
        final Person mom = Fixtures.person();
        // And: that person measured their BP 2 weeks ago
        final BloodPressure first = Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(14), 151, 71, mom);
        Vitalizr.storeBloodPressureFor(first);
        // And: that person measured their BP 3 days ago
        final BloodPressure second = Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(3), 139, 68, mom);
        Vitalizr.storeBloodPressureFor(second);
        // And: that person measured their BP 2 days ago
        final BloodPressure third = Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(2), 145, 71, mom);
        Vitalizr.storeBloodPressureFor(third);
        // And: that person measured their BP 1 days ago
        final BloodPressure fourth = Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(1), 145, 76, mom);
        Vitalizr.storeBloodPressureFor(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the BPs
        final Collection<BloodPressure> result = Vitalizr.getBloodPressuresInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth BP is stored
        assertTrue(result.contains(fourth));
        // And: the third BP is stored
        assertTrue(result.contains(third));
        // And: the second BP is stored
        assertTrue(result.contains(second));
        // And: the first BP is not stored
        assertFalse(result.contains(first));
    }
}
