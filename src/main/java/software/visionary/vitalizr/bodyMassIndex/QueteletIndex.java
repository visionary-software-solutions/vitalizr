package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class QueteletIndex extends SerializableVital implements BodyMassIndex {
    public QueteletIndex(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public QuetletIndexSerializationProxy asSerializationProxy() {
        return new QuetletIndexSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return KilogramsPerMetersSquared.INSTANCE;
    }

    private static final class QuetletIndexSerializationProxy extends DecimalVital<QueteletIndex> {
        private static final String FIELD_DELIMITER = "\uD83E\uDDE0";
        private static final String RECORD_DELIMITER = "\uD83E\uDD2E";

        public QuetletIndexSerializationProxy(final QueteletIndex queteletIndex) {
            super(queteletIndex);
        }

        public QuetletIndexSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public QueteletIndex toVital() {
            return new QueteletIndex(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<QueteletIndex, QuetletIndexSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return QuetletIndexSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return QuetletIndexSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public QuetletIndexSerializationProxy fromMatcher(final Matcher matcher) {
            return new QuetletIndexSerializationProxy(matcher);
        }
    }
}
