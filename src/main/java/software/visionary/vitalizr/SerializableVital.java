package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.Serializable;
import java.time.Instant;

public abstract class SerializableVital extends AbstractVital {
    protected SerializableVital(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public abstract SerializationProxy asSerializationProxy();

    protected abstract static class SerializationProxy implements Serializable {

        private final Instant observationTimestamp;
        private final String observedUnit;
        private final String person;

        private SerializationProxy(final Instant time, final String unit, final String life) {
            observationTimestamp = time;
            observedUnit = unit;
            person = life;
        }

        protected String getObservedUnit() {
            return observedUnit;
        }

        protected abstract SerializableVital toVital();

        protected abstract String getFieldDelimiter();

        protected abstract String getRecordDelimiter();

        protected Instant getObservationTimestamp() {
            return observationTimestamp;
        }

        protected String getPerson() {
            return person;
        }
    }

    protected abstract static class DecimalVital extends SerializationProxy {

        private final double observedValue;

        protected DecimalVital(final Instant time, final double value, final String unit, final String life) {
            super(time, unit, life);
            observedValue = value;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%f%s%s%s%s%s",
                    getRecordDelimiter(),
                    getObservationTimestamp(),
                    getFieldDelimiter(),
                    getObservedValue(),
                    getFieldDelimiter(),
                    getObservedUnit(),
                    getFieldDelimiter(),
                    getPerson(),
                    getRecordDelimiter());
        }

        protected double getObservedValue() {
            return observedValue;
        }

    }

    protected abstract static class IntegralVital extends SerializationProxy {

        private IntegralVital(final Instant time, final String unit, final String life) {
            super(time, unit, life);
        }
    }
}
