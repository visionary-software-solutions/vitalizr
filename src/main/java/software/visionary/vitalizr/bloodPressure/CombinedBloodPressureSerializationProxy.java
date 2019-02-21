package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.Fraction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CombinedBloodPressureSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83C\uDFAF";
    private static final String RECORD_DELIMITER = "\uD83E\uDD28";
    private final Instant observationTimestamp;
    private final Fraction observedValue;
    private final String observedUnit;
    private final String person;

    CombinedBloodPressureSerializationProxy(final Instant time, final Fraction quantity, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = quantity;
        observedUnit = unit;
        person = life;
    }

    static List<CombinedBloodPressureSerializationProxy> parse(final String entry) {
        final List<CombinedBloodPressureSerializationProxy> discovered = new ArrayList<>();
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9./]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern pattern = Pattern.compile(template);
        final Matcher matcher = pattern.matcher(entry);
        while (matcher.find()) {
            final Instant time = Instant.parse(matcher.group("time"));
            final Fraction theFraction = Fraction.valueOf(matcher.group("number"));
            final String unit = matcher.group("unit");
            final String person = matcher.group("person");
            discovered.add(new CombinedBloodPressureSerializationProxy(time, theFraction, unit, person));
        }
        return discovered;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s%s%s%s%s",
                RECORD_DELIMITER,
                getObservationTimestamp(),
                FIELD_DELIMITER,
                getObservedValue(),
                FIELD_DELIMITER,
                getObservedUnit(),
                FIELD_DELIMITER,
                person,
                RECORD_DELIMITER);
    }

    Instant getObservationTimestamp() {
        return observationTimestamp;
    }

    Fraction getObservedValue() {
        return observedValue;
    }

    private String getObservedUnit() {
        return observedUnit;
    }

    String getPerson() {
        return person;
    }
}
