package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class HeartrateMonitor extends SerializableVital implements Pulse {
    public HeartrateMonitor(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<HeartrateMonitor> deserialize(final Stream<String> toConvert) {
        return toConvert.map(HeartrateMonitorSerializationProxy::parse)
                .flatMap(List::stream)
                .map(HeartrateMonitorSerializationProxy::toVital);
    }

    public HeartrateMonitorSerializationProxy asSerializationProxy() {
        return new HeartrateMonitorSerializationProxy(observedAt(),
                getQuantity().intValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Unit getUnit() {
        return HeartbeatsPerMinute.INSTANCE;
    }

    private static final class HeartrateMonitorSerializationProxy extends IntegralVital {
        private static final String FIELD_DELIMITER = "\uD83D\uDC93";
        private static final String RECORD_DELIMITER = "\uD83D\uDC97";

        private HeartrateMonitorSerializationProxy(final Instant time, final int value, final String unit, final String life) {
            super(time, value, unit, life);
        }

        private static List<HeartrateMonitorSerializationProxy> parse(final String entry) {
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
        public HeartrateMonitor toVital() {
            return new HeartrateMonitor(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
        }

        @Override
        protected String getFieldDelimiter() {
            return FIELD_DELIMITER;
        }

        @Override
        protected String getRecordDelimiter() {
            return RECORD_DELIMITER;
        }
    }
}
