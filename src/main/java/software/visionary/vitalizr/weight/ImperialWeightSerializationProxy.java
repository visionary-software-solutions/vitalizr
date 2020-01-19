package software.visionary.vitalizr.weight;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImperialWeightSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83D\uDC51";
    private static final String RECORD_DELIMITER = "\uD83E\uDE00";
    private final Instant observationTimestamp;
    private final double observedValue;
    private final String observedUnit;
    private final String person;

    ImperialWeightSerializationProxy(final Instant time, final double value, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = value;
        observedUnit = unit;
        person = life;
    }

    static List<ImperialWeightSerializationProxy> parse(final String entry) {
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
