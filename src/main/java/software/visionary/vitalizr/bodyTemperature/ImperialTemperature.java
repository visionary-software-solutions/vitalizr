package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class ImperialTemperature extends SerializableVital implements BodyTemperature {
    public ImperialTemperature(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public ImperialTemperatureSerializationProxy asSerializationProxy() {
        return new ImperialTemperatureSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Fahrenheit.INSTANCE;
    }

    private static final class ImperialTemperatureSerializationProxy extends DecimalVital<ImperialTemperature> {
        private static final String FIELD_DELIMITER = "\uD83E\uDD75";
        private static final String RECORD_DELIMITER = "\uD83E\uDD76";

        public ImperialTemperatureSerializationProxy(final ImperialTemperature imperialTemperature) {
            super(imperialTemperature);
        }

        public ImperialTemperatureSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public ImperialTemperature toVital() {
            return new ImperialTemperature(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<ImperialTemperature, ImperialTemperatureSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return ImperialTemperatureSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return ImperialTemperatureSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public ImperialTemperatureSerializationProxy fromMatcher(final Matcher matcher) {
            return new ImperialTemperatureSerializationProxy(matcher);
        }
    }
}
