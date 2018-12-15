package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.pulse.Pulse;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PulseQueryIntegrationTest {
    @Test
    void canQueryPulseForTimeRange() {
        // Given: A person to retrieve pulse for
        final Person mom = Fixtures.person();
        // And: that person measured their pulse 2 weeks ago
        final Pulse first = Fixtures.pulseAt(90, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storePulseFor(first);
        // And: that person measured their pulse 3 days ago
        final Pulse second = Fixtures.pulseAt(72, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storePulseFor(second);
        // And: that person measured their pulse 2 days ago
        final Pulse third = Fixtures.pulseAt(81, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storePulseFor(third);
        // And: that person measured their pulse 1 days ago
        final Pulse fourth = Fixtures.pulseAt(62, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storePulseFor(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the pulses
        final Collection<Pulse> result = Vitalizr.getPulsesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth pulse is stored
        assertTrue(result.contains(fourth));
        // And: the third pulse is stored
        assertTrue(result.contains(third));
        // And: the second pulse is stored
        assertTrue(result.contains(second));
        // And: the first pulse is not stored
        assertFalse(result.contains(first));
    }
}
