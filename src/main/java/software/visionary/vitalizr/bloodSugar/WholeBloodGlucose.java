package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.AbstractVital;
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

public final class WholeBloodGlucose extends AbstractVital implements BloodSugar {
    public WholeBloodGlucose(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<WholeBloodGlucose> deserialize(final Stream<String> toConvert) {
        return toConvert.map(WholeBloodGlucoseSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> new WholeBloodGlucose(toConvert1.getObservationTimestamp(), toConvert1.getObservedValue(), Human.createPerson(toConvert1.getPerson())));
    }

    public WholeBloodGlucoseSerializationProxy asSerializationProxy() {
        return new WholeBloodGlucoseSerializationProxy(observedAt(),
                getQuantity().intValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Unit getUnit() {
        return Millimolar.INSTANCE;
    }

    private static final class WholeBloodGlucoseSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83C\uDF70";
        private static final String RECORD_DELIMITER = "\uD83E\uDD22";
        private final Instant observationTimestamp;
        private final int observedValue;
        private final String observedUnit;
        private final String person;

        private WholeBloodGlucoseSerializationProxy(final Instant time, final int value, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = value;
            observedUnit = unit;
            person = life;
        }

        private static List<WholeBloodGlucoseSerializationProxy> parse(final String entry) {
            final List<WholeBloodGlucoseSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final int value = Integer.parseInt(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final WholeBloodGlucoseSerializationProxy toAdd = new WholeBloodGlucoseSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
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
    }
}
