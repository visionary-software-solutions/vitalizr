package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class MetricWeight implements Weight {
    private final Instant observed;
    private final NaturalNumber number;
    private final Lifeform owner;

    private MetricWeight(final Instant observed, final NaturalNumber number, final Lifeform lifeform) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.owner = Objects.requireNonNull(lifeform);
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

    @Override
    public Number getQuantity() {
        return number;
    }

    @Override
    public Instant observedAt() {
        return observed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MetricWeight weight = (MetricWeight) o;
        return Objects.equals(observed, weight.observed) &&
                Objects.equals(number, weight.number) &&
                Objects.equals(getUnit(), weight.getUnit()) &&
                Objects.equals(belongsTo(), weight.belongsTo());
    }
    
    @Override
    public Lifeform belongsTo() {
        return owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observed, number, getUnit(), belongsTo());
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
