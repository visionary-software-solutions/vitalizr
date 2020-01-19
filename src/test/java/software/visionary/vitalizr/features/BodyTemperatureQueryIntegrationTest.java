package software.visionary.vitalizr.features;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyTemperature.MetricTemperature;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BodyTemperatureQueryIntegrationTest {

    private static final Person PERSON = Fixtures.person();

    @ParameterizedTest
    @MethodSource
    void canQueryBodyTemperatureForTimeRange(final List<BodyTemperature> temperatures) {
        // Given: A person to retrieve temperature for
        // And: that person measured their temperature 2 weeks ago
        final BodyTemperature first = temperatures.get(0);
        Vitalizr.storeTemperature(first);
        // And: that person measured their temperature 3 days ago
        final BodyTemperature second = temperatures.get(1);
        Vitalizr.storeTemperature(second);
        // And: that person measured their temperature 2 days ago
        final BodyTemperature third = temperatures.get(2);
        Vitalizr.storeTemperature(third);
        // And: that person measured their temperature 1 days ago
        final BodyTemperature fourth = temperatures.get(3);
        Vitalizr.storeTemperature(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the temperatures
        final Collection<BodyTemperature> result = Vitalizr.getBodyTemperaturesInInterval(PERSON, oneWeekAgoToNow);
        // Then: the fourth temperature is stored
        assertTrue(result.contains(fourth));
        // And: the third temperature is stored
        assertTrue(result.contains(third));
        // And: the second temperature is stored
        assertTrue(result.contains(second));
        // And: the first temperature is not stored
        assertFalse(result.contains(first));
    }

    private static Stream<List<BodyTemperature>> canQueryBodyTemperatureForTimeRange() {
        return Stream.of(
                Arrays.asList(
                        Fixtures.temperatureAt(98, Fixtures.observationAtMidnightNDaysAgo(14), PERSON),
                        Fixtures.temperatureAt(101.3, Fixtures.observationAtMidnightNDaysAgo(3), PERSON),
                        Fixtures.temperatureAt(99.2, Fixtures.observationAtMidnightNDaysAgo(2), PERSON),
                        Fixtures.temperatureAt(97.9, Fixtures.observationAtMidnightNDaysAgo(1), PERSON)
                ),
                Arrays.asList(
                        new MetricTemperature(PERSON, 98, Fixtures.observationAtMidnightNDaysAgo(14)),
                        new MetricTemperature(PERSON, 101.3, Fixtures.observationAtMidnightNDaysAgo(3)),
                        new MetricTemperature(PERSON, 99.2, Fixtures.observationAtMidnightNDaysAgo(2)),
                        new MetricTemperature(PERSON, 97.9, Fixtures.observationAtMidnightNDaysAgo(1))
                )
        );
    }
}
