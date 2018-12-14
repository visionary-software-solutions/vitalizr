package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodPressureQueryIntegrationTest {
    @Test
    void canQueryBloodPressureForTimeRange() {
        // Given: A person to retrieve BP for
        final Person mom = Fixtures.person();
        // And: that person measured their weight 2 weeks ago
        final Instant firstObservation = Fixtures.observationAtMidnightNDaysAgo(14);
        final BloodPressure first = Combined.systolicAndDiastolicBloodPressure(firstObservation, 151, 71);
        Vitalizr.storeBloodPressureFor(mom, first);
        // And: that person measured their weight 3 days ago
        final Instant secondObservation = Fixtures.observationAtMidnightNDaysAgo(3);
        final BloodPressure second = Combined.systolicAndDiastolicBloodPressure(secondObservation, 139, 68);
        Vitalizr.storeBloodPressureFor(mom, second);
        // And: that person measured their weight 2 days ago
        final BloodPressure third = Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(2), 145, 71);
        Vitalizr.storeBloodPressureFor(mom, third);
        // And: that person measured their weight 1 days ago
        final BloodPressure fourth = Combined.systolicAndDiastolicBloodPressure(secondObservation, 145, 76);
        Vitalizr.storeBloodPressureFor(mom, fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Interval.of(Fixtures.observationAtMidnightNDaysAgo(7), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
        // When: I fetch the weights
        final Collection<PersonBloodPressure> result = Vitalizr.getBloodPressuresInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth weight is stored
        assertTrue(result.contains(new PersonBloodPressure(mom, fourth)));
        // And: the third weight is stored
        assertTrue(result.contains(new PersonBloodPressure(mom, third)));
        // And: the second weight is stored
        assertTrue(result.contains(new PersonBloodPressure(mom, second)));
        // And: the first weight is not stored
        assertFalse(result.contains(new PersonBloodPressure(mom, first)));
    }

}
