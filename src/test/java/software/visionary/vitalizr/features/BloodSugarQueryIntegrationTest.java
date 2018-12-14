package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.PersonBloodSugar;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodSugarQueryIntegrationTest {
    @Test
    void canQueryBloodOxygenForTimeRange() {
        // Given: A person to retrieve blood sugar for
        final Person mom = Fixtures.person();
        // And: that person measured their blood sugar 2 weeks ago
        final BloodSugar first = Fixtures.bloodSugarAt(92, Fixtures.observationAtMidnightNDaysAgo(14));
        Vitalizr.storeBloodSugarFor(mom, first);
        // And: that person measured their blood sugar 3 days ago
        final BloodSugar second = Fixtures.bloodSugarAt(94, Fixtures.observationAtMidnightNDaysAgo(3));
        Vitalizr.storeBloodSugarFor(mom, second);
        // And: that person measured their blood sugar 2 days ago
        final BloodSugar third = Fixtures.bloodSugarAt(96, Fixtures.observationAtMidnightNDaysAgo(2));
        Vitalizr.storeBloodSugarFor(mom, third);
        // And: that person measured their blood sugar 1 days ago
        final BloodSugar fourth = Fixtures.bloodSugarAt(97, Fixtures.observationAtMidnightNDaysAgo(1));
        Vitalizr.storeBloodSugarFor(mom, fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the blood sugars
        final Collection<PersonBloodSugar> result = Vitalizr.getBloodSugarsInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth blood sugar is stored
        assertTrue(result.contains(new PersonBloodSugar(mom, fourth)));
        // And: the third blood sugar is stored
        assertTrue(result.contains(new PersonBloodSugar(mom, third)));
        // And: the second blood sugar is stored
        assertTrue(result.contains(new PersonBloodSugar(mom, second)));
        // And: the first blood sugar is not stored
        assertFalse(result.contains(new PersonBloodSugar(mom, first)));
    }
}
