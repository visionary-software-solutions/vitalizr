package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class PeripheralOxygenSaturation extends SerializableVital implements BloodOxygen {
    public PeripheralOxygenSaturation(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public PeripheralOxygenSaturationSerializationProxy asSerializationProxy() {
        return new PeripheralOxygenSaturationSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return OxygenSaturation.INSTANCE;
    }

    private static final class PeripheralOxygenSaturationSerializationProxy extends IntegralVital<PeripheralOxygenSaturation>  {
        private static final String FIELD_DELIMITER = "\uD83E\uDD71";
        private static final String RECORD_DELIMITER = "\uD83E\uDDA8";

        public PeripheralOxygenSaturationSerializationProxy(final PeripheralOxygenSaturation peripheralOxygenSaturation) {
            super(peripheralOxygenSaturation);
        }

        public PeripheralOxygenSaturationSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public PeripheralOxygenSaturation toVital() {
            return new PeripheralOxygenSaturation(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<PeripheralOxygenSaturation, PeripheralOxygenSaturationSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return PeripheralOxygenSaturationSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return PeripheralOxygenSaturationSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public PeripheralOxygenSaturationSerializationProxy fromMatcher(final Matcher matcher) {
            return new PeripheralOxygenSaturationSerializationProxy(matcher);
        }
    }
}
