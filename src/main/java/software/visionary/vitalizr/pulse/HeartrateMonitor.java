package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class HeartrateMonitor extends SerializableVital implements Pulse {
    public HeartrateMonitor(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public HeartrateMonitorSerializationProxy asSerializationProxy() {
        return new HeartrateMonitorSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return HeartbeatsPerMinute.INSTANCE;
    }

    private static final class HeartrateMonitorSerializationProxy extends IntegralVital<HeartrateMonitor> {
        private static final String FIELD_DELIMITER = "\uD83D\uDC93";
        private static final String RECORD_DELIMITER = "\uD83D\uDC97";

        public HeartrateMonitorSerializationProxy(final HeartrateMonitor heartrateMonitor) {
            super(heartrateMonitor);
        }

        public HeartrateMonitorSerializationProxy(final Matcher matcher) {
            super(matcher);
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

    public enum Factory implements AbstractFactory<HeartrateMonitor, HeartrateMonitorSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return HeartrateMonitorSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return HeartrateMonitorSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public HeartrateMonitorSerializationProxy fromMatcher(final Matcher matcher) {
            return new HeartrateMonitorSerializationProxy(matcher);
        }
    }
}
