package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.regex.Matcher;

public final class MetricWeight extends SerializableVital implements Weight {
    public MetricWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return Gram.INSTANCE;
    }

    public MetricWeightSerializationProxy asSerializationProxy() {
        return new MetricWeightSerializationProxy(this);
    }

    private static final class MetricWeightSerializationProxy extends DecimalVital<MetricWeight> {
        private static final String FIELD_DELIMITER = "💩";
        private static final String RECORD_DELIMITER = "🤯";

        public MetricWeightSerializationProxy(final MetricWeight metricWeight) {
            super(metricWeight);
        }

        public MetricWeightSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        @Override
        protected MetricWeight toVital() {
            return new MetricWeight(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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

    public enum Factory implements AbstractFactory<MetricWeight, MetricWeightSerializationProxy> {
        INSTANCE;

        @Override
        public String getFieldDelimiter() {
            return MetricWeightSerializationProxy.FIELD_DELIMITER;
        }

        @Override
        public String getRecordDelimiter() {
            return MetricWeightSerializationProxy.RECORD_DELIMITER;
        }

        @Override
        public MetricWeightSerializationProxy fromMatcher(final Matcher matcher) {
            return new MetricWeightSerializationProxy(matcher);
        }
    }
}
