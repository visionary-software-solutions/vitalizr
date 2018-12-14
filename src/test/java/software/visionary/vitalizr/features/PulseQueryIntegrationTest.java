package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;
import software.visionary.vitalizr.pulse.PersonPulse;
import software.visionary.vitalizr.pulse.Pulse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PulseQueryIntegrationTest {
    @Test
    void canQueryPulseForTimeRange() {
        // Given: A person to retrieve pulse for
        final Person mom = Fixtures.person();
        // And: that person measured their pulse 2 weeks ago
        final Instant firstObservation = Fixtures.observationAtMidnightNDaysAgo(14);
        final Pulse first = Fixtures.pulseAt(90, firstObservation);
        Vitalizr.storePulseFor(mom, first);
        // And: that person measured their pulse 3 days ago
        final Instant secondObservation = Fixtures.observationAtMidnightNDaysAgo(3);
        final Pulse second = Fixtures.pulseAt(72, secondObservation);
        Vitalizr.storePulseFor(mom, second);
        // And: that person measured their pulse 2 days ago
        final Instant thirdObservation = Fixtures.observationAtMidnightNDaysAgo(2);
        final Pulse third = Fixtures.pulseAt(81, thirdObservation);
        Vitalizr.storePulseFor(mom, third);
        // And: that person measured their pulse 1 days ago
        final Instant fourthObservation = Fixtures.observationAtMidnightNDaysAgo(1);
        final Pulse fourth = Fixtures.pulseAt(62, fourthObservation);
        Vitalizr.storePulseFor(mom, fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Interval.of(Fixtures.observationAtMidnightNDaysAgo(7), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
        // When: I fetch the pulses
        final Collection<PersonPulse> result = Vitalizr.getPulsesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth pulse is stored
        assertTrue(result.contains(new PersonPulse(mom, fourth)));
        // And: the third pulse is stored
        assertTrue(result.contains(new PersonPulse(mom, third)));
        // And: the second pulse is stored
        assertTrue(result.contains(new PersonPulse(mom, second)));
        // And: the first pulse is not stored
        assertFalse(result.contains(new PersonPulse(mom, first)));
    }
}
