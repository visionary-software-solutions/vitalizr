package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloodOxygenQueryIntegrationTest {
    @Test
    void canQueryBloodOxygenForTimeRange() {
        // Given: A person to retrieve O2 for
        final Person mom = Fixtures.person();
        // And: that person measured their O2 2 weeks ago
        final BloodOxygen first = Fixtures.oxygenAt(92, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeBloodOxygen(first);
        // And: that person measured their O2 3 days ago
        final BloodOxygen second = Fixtures.oxygenAt(94, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeBloodOxygen(second);
        // And: that person measured their O2 2 days ago
        final BloodOxygen third = Fixtures.oxygenAt(96, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeBloodOxygen(third);
        // And: that person measured their O2 1 days ago
        final BloodOxygen fourth = Fixtures.oxygenAt(97, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeBloodOxygen(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the O2s
        final Collection<BloodOxygen> result = Vitalizr.getBloodOxygensInInterval(mom, oneWeekAgoToNow);
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
