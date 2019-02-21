package software.visionary.vitalizr.bodyMassIndex;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QuetletIndexSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83E\uDDE0";
    private static final String RECORD_DELIMITER = "\uD83E\uDD2E";
    private final Instant observationTimestamp;
    private final double observedValue;
    private final String observedUnit;
    private final String person;

    QuetletIndexSerializationProxy(final Instant time, final double value, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = value;
        observedUnit = unit;
        person = life;
    }

    static List<QuetletIndexSerializationProxy> parse(final String entry) {
        final List<QuetletIndexSerializationProxy> discovered = new ArrayList<>();
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern sought = Pattern.compile(template);
        final Matcher matcher = sought.matcher(entry);
        while (matcher.find()) {
            final Instant time = Instant.parse(matcher.group("time"));
            final double value = Double.valueOf(matcher.group("number"));
            final String unit = matcher.group("unit");
            final String person = matcher.group("person");
            final QuetletIndexSerializationProxy toAdd = new QuetletIndexSerializationProxy(time, value, unit, person);
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

    Instant getObservationTimestamp() {
        return observationTimestamp;
    }

    double getObservedValue() {
        return observedValue;
    }

    String getPerson() {
        return person;
    }
}
