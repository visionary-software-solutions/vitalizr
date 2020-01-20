package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class BioelectricalImpedance extends SerializableVital implements BodyFatPercentage {
    public BioelectricalImpedance(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public BioelectricalImpedanceSerializationProxy asSerializationProxy() {
        return new BioelectricalImpedanceSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Unit.NONE.INSTANCE;
    }

    private static final class BioelectricalImpedanceSerializationProxy extends DecimalVital<BioelectricalImpedance> {
        private static final String FIELD_DELIMITER = "\uD83E\uDD6E";
        private static final String RECORD_DELIMITER = "\uD83E\uDD5E";


        public BioelectricalImpedanceSerializationProxy(final BioelectricalImpedance bioelectricalImpedance) {
            super(bioelectricalImpedance);
        }

        public BioelectricalImpedanceSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public BioelectricalImpedance toVital() {
            return new BioelectricalImpedance(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<BioelectricalImpedance, BioelectricalImpedanceSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return BioelectricalImpedanceSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return BioelectricalImpedanceSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public BioelectricalImpedanceSerializationProxy fromMatcher(final Matcher matcher) {
            return new BioelectricalImpedanceSerializationProxy(matcher);
        }
    }
}
