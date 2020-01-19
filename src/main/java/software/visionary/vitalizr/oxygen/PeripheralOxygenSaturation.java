package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class PeripheralOxygenSaturation implements BloodOxygen {
    private final Instant observed;
    private final Number number;
    private final Lifeform owner;

    public PeripheralOxygenSaturation(final Instant observed, final Number number, final Lifeform owner) {
        this.observed = observed;
        this.number = number;
        this.owner = owner;
    }

    public static Stream<PeripheralOxygenSaturation> fromSerialized(final Stream<String> toConvert) {
        return toConvert.map(PeripheralOxygenSaturationSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> new PeripheralOxygenSaturation(toConvert1.getObservationTimestamp(), toConvert1.getObservedValue(), Human.createPerson(toConvert1.getPerson())));
    }

    public PeripheralOxygenSaturationSerializationProxy asSerializationProxy() {
        return new PeripheralOxygenSaturationSerializationProxy(observedAt(),
                getQuantity().intValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Lifeform belongsTo() {
        return owner;
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
        final PeripheralOxygenSaturation that = (PeripheralOxygenSaturation) o;
        return Objects.equals(observed, that.observed) &&
                Objects.equals(number, that.number) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observed, number, owner);
    }

    private static final class PeripheralOxygenSaturationSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83E\uDD71";
        private static final String RECORD_DELIMITER = "\uD83E\uDDA8";
        private final Instant observationTimestamp;
        private final int observedValue;
        private final String observedUnit;
        private final String person;

        private PeripheralOxygenSaturationSerializationProxy(final Instant time, final int value, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = value;
            observedUnit = unit;
            person = life;
        }

        private static List<PeripheralOxygenSaturationSerializationProxy> parse(final String entry) {
            final List<PeripheralOxygenSaturationSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final int value = Integer.parseInt(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final PeripheralOxygenSaturationSerializationProxy toAdd = new PeripheralOxygenSaturationSerializationProxy(time, value, unit, person);
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

        private int getObservedValue() {
            return observedValue;
        }

        private String getPerson() {
            return person;
        }
    }
}
