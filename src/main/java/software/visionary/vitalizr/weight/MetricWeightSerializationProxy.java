package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class MetricWeightSerializationProxy {
    private static final String FIELD_DELIMITER = "💩";
    private static final String RECORD_DELIMITER = "🤯";
    private final Instant observationTimestamp;
    private final byte observedValue;
    private final String observedUnit;
    private final String person;

    private MetricWeightSerializationProxy(final Instant time, final byte value, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = value;
        observedUnit = unit;
        person = life;
    }

    public static MetricWeightSerializationProxy fromMetricWeight(final MetricWeight toSerialize) {
        return new MetricWeightSerializationProxy(toSerialize.observedAt(),
                (byte) (toSerialize.getQuantity().doubleValue() / 1000),
                toSerialize.getUnit().getSymbol(),
                new LifeformSerializationProxy(toSerialize.belongsTo()).toString());
    }

    public static Stream<MetricWeight> stream(final List<String> entries) {
        final List<MetricWeight> result = new ArrayList<>(entries.size());
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern sought = Pattern.compile(template);
        for (final String entry : entries) {
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final byte value = Byte.valueOf(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final MetricWeightSerializationProxy toAdd = new MetricWeightSerializationProxy(time, value, unit, person);
                result.add(toAdd.toMetricWeight());
            }
        }
        return result.stream();
    }

    private MetricWeight toMetricWeight() {
        return MetricWeight.inKilograms(observedValue, observationTimestamp, Human.createPerson(person));
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%d%s%s%s%s%s",
                RECORD_DELIMITER,
                observationTimestamp,
                FIELD_DELIMITER,
                observedValue,
                FIELD_DELIMITER,
                observedUnit,
                FIELD_DELIMITER,
                person,
                RECORD_DELIMITER);
    }

    private static class LifeformSerializationProxy {
        private final String asAString;

        private LifeformSerializationProxy(final Lifeform toSerialize) {
            if (toSerialize instanceof Human) {
                asAString = toSerialize.toString();
            } else {
                throw new UnsupportedOperationException("We only know how to process Humans!");
            }
        }

        @Override
        public String toString() {
            return asAString;
        }
    }
}
