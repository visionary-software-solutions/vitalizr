package software.visionary.vitalizr.pulse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HeartrateMonitorSerializationProxy {
    private static final String FIELD_DELIMITER = "\uD83D\uDC93";
    private static final String RECORD_DELIMITER = "\uD83D\uDC97";
    private final Instant observationTimestamp;
    private final int observedValue;
    private final String observedUnit;
    private final String person;

    HeartrateMonitorSerializationProxy(final Instant time, final int value, final String unit, final String life) {
        observationTimestamp = time;
        observedValue = value;
        observedUnit = unit;
        person = life;
    }

    static List<HeartrateMonitorSerializationProxy> parse(final String entry) {
        final List<HeartrateMonitorSerializationProxy> discovered = new ArrayList<>();
        final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
        final Pattern sought = Pattern.compile(template);
        final Matcher matcher = sought.matcher(entry);
        while (matcher.find()) {
            final Instant time = Instant.parse(matcher.group("time"));
            final int value = Integer.parseInt(matcher.group("number"));
            final String unit = matcher.group("unit");
            final String person = matcher.group("person");
            final HeartrateMonitorSerializationProxy toAdd = new HeartrateMonitorSerializationProxy(time, value, unit, person);
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

    Instant getObservationTimestamp() {
        return observationTimestamp;
    }

    int getObservedValue() {
        return observedValue;
    }

    String getPerson() {
        return person;
    }
}
