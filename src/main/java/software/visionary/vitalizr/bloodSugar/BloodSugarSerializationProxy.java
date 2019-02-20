package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class BloodSugarSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83C\uDF70";
    private static final String RECORD_DELIMITER = "\uD83E\uDD22";
    private final Instant observationTimestamp;
    private final int observedValue;
    private final String observedUnit;
    private final String person;

    private BloodSugarSerializationProxy(final Instant time, final int value, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = value;
        observedUnit = unit;
        person = life;
    }

    public static BloodSugarSerializationProxy fromBloodSugar(final BloodSugar toSerialize) {
        return new BloodSugarSerializationProxy(toSerialize.observedAt(),
                toSerialize.getQuantity().intValue(),
                toSerialize.getUnit().getSymbol(),
                new LifeformSerializationProxy(toSerialize.belongsTo()).toString());
    }

    public static Stream<BloodSugar> stream(final List<String> entries) {
        final List<BloodSugar> result = new ArrayList<>(entries.size());
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern sought = Pattern.compile(template);
        for (final String entry : entries) {
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final int value = Integer.valueOf(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final BloodSugarSerializationProxy toAdd = new BloodSugarSerializationProxy(time, value, unit, person);
                result.add(toAdd.toBloodSugar());
            }
        }
        return result.stream();
    }

    private BloodSugar toBloodSugar() {
        return new WholeBloodGlucose(observationTimestamp, observedValue, Human.createPerson(person));
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
                System.out.println("It was" + toSerialize + " " + toSerialize.getClass());
                throw new UnsupportedOperationException("We only know how to process Humans!");
            }
        }

        @Override
        public String toString() {
            return asAString;
        }
    }
}
