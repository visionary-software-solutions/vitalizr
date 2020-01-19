package software.visionary.vitalizr.bodyTemperature;

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

public final class MetricTemperature implements BodyTemperature {
    private final Lifeform lifeform;
    private final Number quantity;
    private final Instant observedAt;

    public MetricTemperature(final Lifeform lifeform, final Number quantity, final Instant observedAt) {
        this.lifeform = Objects.requireNonNull(lifeform);
        this.quantity = Objects.requireNonNull(quantity);
        this.observedAt = Objects.requireNonNull(observedAt);
    }

    public static Stream<MetricTemperature> deserialize(final Stream<String> toConvert) {
        return toConvert.map(MetricTemperatureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> new MetricTemperature(Human.createPerson(toConvert1.getPerson()), toConvert1.getObservedValue(), toConvert1.getObservationTimestamp()));
    }

    public MetricTemperatureSerializationProxy asSerializationProxy() {
        return new MetricTemperatureSerializationProxy(observedAt(),
                getQuantity().doubleValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Lifeform belongsTo() {
        return lifeform;
    }

    @Override
    public Number getQuantity() {
        return quantity;
    }

    @Override
    public Instant observedAt() {
        return observedAt;
    }

    @Override
    public Unit getUnit() {
        return Celsius.INSTANCE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MetricTemperature that = (MetricTemperature) o;
        return lifeform.equals(that.lifeform) &&
                getQuantity().equals(that.getQuantity()) &&
                observedAt.equals(that.observedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifeform, getQuantity(), observedAt);
    }

    private static final class MetricTemperatureSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83C\uDF21";
        private static final String RECORD_DELIMITER = "\uD83C\uDF27";
        private final Instant observationTimestamp;
        private final double observedValue;
        private final String observedUnit;
        private final String person;

        private MetricTemperatureSerializationProxy(final Instant time, final double value, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = value;
            observedUnit = unit;
            person = life;
        }

        private static List<MetricTemperatureSerializationProxy> parse(final String entry) {
            final List<MetricTemperatureSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final double value = Double.parseDouble(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final MetricTemperatureSerializationProxy toAdd = new MetricTemperatureSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
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

        @Override
        public String toString() {
            return String.format("%s%s%s%f%s%s%s%s%s",
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
    }
}
