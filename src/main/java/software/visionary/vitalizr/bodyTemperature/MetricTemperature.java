package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class MetricTemperature extends SerializableVital implements BodyTemperature {
    public MetricTemperature(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public MetricTemperatureSerializationProxy asSerializationProxy() {
        return new MetricTemperatureSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Celsius.INSTANCE;
    }

    private static final class MetricTemperatureSerializationProxy extends DecimalVital<MetricTemperature> {
        private static final String FIELD_DELIMITER = "\uD83C\uDF21";
        private static final String RECORD_DELIMITER = "\uD83C\uDF27";

        public MetricTemperatureSerializationProxy(final MetricTemperature metricTemperature) {
            super(metricTemperature);
        }

        public MetricTemperatureSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public MetricTemperature toVital() {
            return new MetricTemperature(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<MetricTemperature, MetricTemperatureSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return MetricTemperatureSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return MetricTemperatureSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public MetricTemperatureSerializationProxy fromMatcher(final Matcher matcher) {
            return new MetricTemperatureSerializationProxy(matcher);
        }
    }
}
