package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyTemperatureQueryIntegrationTest {
    @Test
    void canQueryBodyTemperatureForTimeRange() {
        // Given: A person to retrieve temperature for
        final Person mom = Fixtures.person();
        // And: that person measured their temperature 2 weeks ago
        final BodyTemperature first = Fixtures.temperatureAt(98, Fixtures.observationAtMidnightNDaysAgo(14), mom);
        Vitalizr.storeTemperature(first);
        // And: that person measured their temperature 3 days ago
        final BodyTemperature second = Fixtures.temperatureAt(101.3, Fixtures.observationAtMidnightNDaysAgo(3), mom);
        Vitalizr.storeTemperature(second);
        // And: that person measured their temperature 2 days ago
        final BodyTemperature third = Fixtures.temperatureAt(99.2, Fixtures.observationAtMidnightNDaysAgo(2), mom);
        Vitalizr.storeTemperature(third);
        // And: that person measured their temperature 1 days ago
        final BodyTemperature fourth = Fixtures.temperatureAt(97.9, Fixtures.observationAtMidnightNDaysAgo(1), mom);
        Vitalizr.storeTemperature(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the temperatures
        final Collection<BodyTemperature> result = Vitalizr.getBodyTemperaturesInInterval(mom, oneWeekAgoToNow);
        // Then: the fourth temperature is stored
        assertTrue(result.contains(fourth));
        // And: the third temperature is stored
        assertTrue(result.contains(third));
        // And: the second temperature is stored
        assertTrue(result.contains(second));
        // And: the first temperature is not stored
        assertFalse(result.contains(first));
    }
}
