package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.AbstractVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class MetricWeight extends AbstractVital implements Weight {
    public MetricWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return Gram.INSTANCE;
    }


    public static MetricWeight inKilograms(final Number kilos, final Instant observedAt, final Lifeform lifeform) {
        return new MetricWeight(observedAt, new NaturalNumber(kilos.intValue() * 1000), lifeform);
    }

    public static Stream<MetricWeight> deserialize(final Stream<String> toConvert) {
        return toConvert.map(MetricWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> inKilograms(toConvert1.getObservedValue(), toConvert1.getObservationTimestamp(), Human.createPerson(toConvert1.getPerson())));
    }

    public MetricWeightSerializationProxy asSerializationProxy() {
        return new MetricWeightSerializationProxy(observedAt(),
                (byte) (getQuantity().doubleValue() / 1000),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    private static final class MetricWeightSerializationProxy {
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

        private static List<MetricWeightSerializationProxy> parse(final String entry) {
            final List<MetricWeightSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final byte value = Byte.parseByte(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final MetricWeightSerializationProxy toAdd = new MetricWeightSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%d%s%s%s%s%s",
                    RECORD_DELIMITER,
                    getObservationTimestamp(),
                    FIELD_DELIMITER,
                    getObservedValue(),
                    FIELD_DELIMITER,
                    observedUnit,
                    FIELD_DELIMITER,
                    getPerson(),
                    RECORD_DELIMITER);
        }

        private Instant getObservationTimestamp() {
            return observationTimestamp;
        }

        private byte getObservedValue() {
            return observedValue;
        }

        private String getPerson() {
            return person;
        }
    }
}
