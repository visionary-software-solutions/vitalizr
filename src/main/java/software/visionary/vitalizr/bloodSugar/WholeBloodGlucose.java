package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class WholeBloodGlucose extends SerializableVital implements BloodSugar {
    public WholeBloodGlucose(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public WholeBloodGlucoseSerializationProxy asSerializationProxy() {
        return new WholeBloodGlucoseSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Millimolar.INSTANCE;
    }

    private static final class WholeBloodGlucoseSerializationProxy extends IntegralVital<WholeBloodGlucose> {
        private static final String FIELD_DELIMITER = "\uD83C\uDF70";
        private static final String RECORD_DELIMITER = "\uD83E\uDD22";

        public WholeBloodGlucoseSerializationProxy(final WholeBloodGlucose wholeBloodGlucose) {
            super(wholeBloodGlucose);
        }

        public WholeBloodGlucoseSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        public WholeBloodGlucose toVital() {
            return new WholeBloodGlucose(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum  Factory implements AbstractFactory<WholeBloodGlucose, WholeBloodGlucoseSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return WholeBloodGlucoseSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return WholeBloodGlucoseSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public WholeBloodGlucoseSerializationProxy fromMatcher(final Matcher matcher) {
            return new WholeBloodGlucoseSerializationProxy(matcher);
        }
    }
}
