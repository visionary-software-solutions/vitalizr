package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.Fraction;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class CombinedBloodPressureSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83C\uDFAF";
    private static final String RECORD_DELIMITER = "\uD83E\uDD28";
    private final Instant observationTimestamp;
    private final Fraction observedValue;
    private final String observedUnit;
    private final String person;

    private CombinedBloodPressureSerializationProxy(final Instant time, final Fraction quantity, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = quantity;
        observedUnit = unit;
        person = life;
    }

    public static CombinedBloodPressureSerializationProxy fromBloodPressure(final Combined toSerialize) {
        return new CombinedBloodPressureSerializationProxy(toSerialize.observedAt(),
                (Fraction) toSerialize.getQuantity(),
                toSerialize.getUnit().getSymbol(),
                new LifeformSerializationProxy(toSerialize.belongsTo()).toString());
    }

    public static Stream<BloodPressure> stream(final List<String> entries) {
        final List<BloodPressure> result = new ArrayList<>(entries.size());
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9./]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern sought = Pattern.compile(template);
        for (final String entry : entries) {
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final Fraction theFraction = Fraction.valueOf(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final CombinedBloodPressureSerializationProxy toAdd = new CombinedBloodPressureSerializationProxy(time, theFraction, unit, person);
                result.add(toAdd.toBloodPressure());
            }
        }
        return result.stream();
    }

    private Combined toBloodPressure() {
        return Combined.systolicAndDiastolicBloodPressure(observationTimestamp, observedValue.getNumerator().intValue(),
                observedValue.getDenominator().intValue(), Human.createPerson(person));
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s%s%s%s%s",
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
