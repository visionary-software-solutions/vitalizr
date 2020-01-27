package software.visionary.vitalizr.weight;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.threeten.extra.Interval;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightQueryIntegrationTest {

    public static final Person PERSON = Fixtures.person();

    @ParameterizedTest
    @MethodSource
    void canQueryWeightForTimeRange(final List<Weight> weights) {
        // Given: A person to retrieve weight for
        // And: that person measured their weight 2 weeks ago
        final Weight first = weights.get(0);
        Vitalizr.storeWeight(first);
        // And: that person measured their weight 3 days ago
        final Weight second = weights.get(1);
        Vitalizr.storeWeight(second);
        // And: that person measured their weight 2 days ago
        final Weight third = weights.get(2);
        Vitalizr.storeWeight(third);
        // And: that person measured their weight 1 days ago
        final Weight fourth = weights.get(3);
        Vitalizr.storeWeight(fourth);
        // And: A time range to query for
        final Interval oneWeekAgoToNow = Fixtures.oneWeekAgoToNow();
        // When: I fetch the weights
        final Collection<Weight> result = Vitalizr.getWeightsInInterval(PERSON, oneWeekAgoToNow);
        // Then: the fourth weight is stored
        assertTrue(result.contains(fourth));
        // And: the third weight is stored
        assertTrue(result.contains(third));
        // And: the second weight is stored
        assertTrue(result.contains(second));
        // And: the first weight is not stored
        assertFalse(result.contains(first));
    }

    private static Stream<List<Weight>> canQueryWeightForTimeRange() {
        return Stream.of(
                Arrays.asList(
                        new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(14), 199, PERSON),
                        new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(3), 207, PERSON),
                        new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(2), 205, PERSON),
                        new ImperialWeight(Fixtures.observationAtMidnightNDaysAgo(1), 203.8, PERSON)
                ),
                Arrays.asList(
                        new MetricWeight(Fixtures.observationAtMidnightNDaysAgo(14), 107, PERSON),
                        new MetricWeight(Fixtures.observationAtMidnightNDaysAgo(3), 105, PERSON),
                        new MetricWeight(Fixtures.observationAtMidnightNDaysAgo(2), 103, PERSON),
                        new MetricWeight(Fixtures.observationAtMidnightNDaysAgo(1), 104, PERSON)
                )
        );
    }
}
