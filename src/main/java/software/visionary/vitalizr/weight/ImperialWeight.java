package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class ImperialWeight implements Weight {
    private final Instant observed;
    private final Number number;
    private final Lifeform owner;

    public ImperialWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.owner = Objects.requireNonNull(lifeform);
    }

    public static Stream<ImperialWeight> deserialize(final Stream<String> toConvert) {
        return toConvert.map(ImperialWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> new ImperialWeight(toConvert1.getObservationTimestamp(), toConvert1.getObservedValue(), Human.createPerson(toConvert1.getPerson())));
    }

    public ImperialWeightSerializationProxy asSerializationProxy() {
        return new ImperialWeightSerializationProxy(observedAt(),
                getQuantity().doubleValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Unit getUnit() {
        return Pound.INSTANCE;
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
        final ImperialWeight weight = (ImperialWeight) o;
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

    private static final class ImperialWeightSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83D\uDC51";
        private static final String RECORD_DELIMITER = "\uD83E\uDE00";
        private final Instant observationTimestamp;
        private final double observedValue;
        private final String observedUnit;
        private final String person;

        private ImperialWeightSerializationProxy(final Instant time, final double value, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = value;
            observedUnit = unit;
            person = life;
        }

        private static List<ImperialWeightSerializationProxy> parse(final String entry) {
            final List<ImperialWeightSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final double value = Double.parseDouble(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final ImperialWeightSerializationProxy toAdd = new ImperialWeightSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%f%s%s%s%s%s",
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

        private double getObservedValue() {
            return observedValue;
        }

        private String getPerson() {
            return person;
        }
    }
}
