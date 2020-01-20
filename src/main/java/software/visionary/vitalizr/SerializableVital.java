package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.bodyWater.BioelectricalImpedance;

import java.io.Serializable;
import java.time.Instant;
import java.util.regex.Matcher;

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

        public SerializationProxy(final Matcher matcher) {
            this.observationTimestamp = Instant.parse(matcher.group("time"));
            this.observedUnit = matcher.group("unit");
            this.person = matcher.group("person");
        }

        public SerializationProxy(final SerializableVital vital) {
            this.observationTimestamp = vital.observedAt();
            this.observedUnit = vital.getUnit().getSymbol();
            this.person = new LifeformSerializationProxy(vital.belongsTo()).toString();
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

        public DecimalVital(final Matcher matcher) {
            super(matcher);
            this.observedValue = Double.parseDouble(matcher.group("number"));
        }

        public DecimalVital(final SerializableVital vital) {
            super(vital);
            this.observedValue = vital.getQuantity().doubleValue();
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
        private final int observedValue;

        protected IntegralVital(final Instant time, final int value, final String unit, final String life) {
            super(time, unit, life);
            observedValue = value;
        }

        public IntegralVital(final SerializableVital vital) {
            super(vital);
            this.observedValue = vital.getQuantity().intValue();
        }

        public IntegralVital(final Matcher matcher) {
            super(matcher);
            this.observedValue = Integer.parseInt(matcher.group("number"));
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%d%s%s%s%s%s",
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

        protected int getObservedValue() {
            return observedValue;
        }
    }
}
