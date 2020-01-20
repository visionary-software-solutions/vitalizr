package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class ImperialWeight extends SerializableVital implements Weight {

    public ImperialWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return Pound.INSTANCE;
    }

    @Override
    public ImperialWeightSerializationProxy asSerializationProxy() {
        return new ImperialWeightSerializationProxy(this);
    }

    private static final class ImperialWeightSerializationProxy extends DecimalVital<ImperialWeight> {
        private static final String FIELD_DELIMITER = "\uD83D\uDC51";
        private static final String RECORD_DELIMITER = "\uD83E\uDE00";

        public ImperialWeightSerializationProxy(final ImperialWeight imperialWeight) {
            super(imperialWeight);
        }

        public ImperialWeightSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        protected ImperialWeight toVital() {
            return new ImperialWeight(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<ImperialWeight, ImperialWeightSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return ImperialWeightSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return ImperialWeightSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public ImperialWeight.ImperialWeightSerializationProxy fromMatcher(final Matcher matcher) {
            return new ImperialWeightSerializationProxy(matcher);
        }
    }
}
