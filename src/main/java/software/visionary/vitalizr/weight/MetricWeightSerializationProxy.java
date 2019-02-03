package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class MetricWeightSerializationProxy {
    private static final String FIELD_DELIMITER = "💩";
    private static final String RECORD_DELIMITER = "\uD83E\uDD2F";
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

    public static Stream<MetricWeight> getMetricWeightStream(final List<String> entries) {
        return entries.stream()
                .map(e -> e.split(RECORD_DELIMITER))
                .flatMap(Arrays::stream)
                .map(record -> fromString(record).toMetricWeight());
    }

    public static MetricWeightSerializationProxy fromString(final String string) {
        final String[] pieces = string.split(FIELD_DELIMITER);
        final Instant time = Instant.parse(pieces[0]);
        final byte value = Byte.valueOf(pieces[1]);
        final String unit = pieces[2];
        final String person = pieces[3];
        return new MetricWeightSerializationProxy(time, value, unit, person);
    }

    public MetricWeight toMetricWeight() {
        return MetricWeight.inKilograms(observedValue, observationTimestamp, Human.createPerson(person));
    }

    @Override
    public String toString() {
        return String.format("%s%s%d%s%s%s%s%s",
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