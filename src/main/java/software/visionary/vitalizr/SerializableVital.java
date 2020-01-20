package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class SerializableVital extends AbstractVital {
    public interface AbstractFactory<T extends SerializableVital, U extends SerializationProxy<T>> {
        default Stream<T> create(Stream<String> strings) {
            return strings.map(this::doParse)
                    .flatMap(List::stream)
                    .map(SerializationProxy::toVital);
        }

        default List<U> doParse(String string) {
            final List<U> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", getRecordDelimiter(), getFieldDelimiter(), getFieldDelimiter(), getFieldDelimiter(), getRecordDelimiter());
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(string);
            while (matcher.find()) {
                discovered.add(fromMatcher(matcher));
            }
            return discovered;
        }

        String getFieldDelimiter();
        String getRecordDelimiter();
        U fromMatcher(final Matcher m);
    }
    protected SerializableVital(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public abstract SerializationProxy<?> asSerializationProxy();

    protected abstract static class SerializationProxy<T extends SerializableVital> implements Serializable {

        private final Instant observationTimestamp;
        private final String observedUnit;
        private final String person;

        public SerializationProxy(final Matcher matcher) {
            this.observationTimestamp = Instant.parse(matcher.group("time"));
            this.observedUnit = matcher.group("unit");
            this.person = matcher.group("person");
        }

        public SerializationProxy(final T vital) {
            this.observationTimestamp = vital.observedAt();
            this.observedUnit = vital.getUnit().getSymbol();
            this.person = new LifeformSerializationProxy(vital.belongsTo()).toString();
        }

        protected String getObservedUnit() {
            return observedUnit;
        }

        protected abstract T toVital();

        protected abstract String getFieldDelimiter();

        protected abstract String getRecordDelimiter();

        protected Instant getObservationTimestamp() {
            return observationTimestamp;
        }

        protected String getPerson() {
            return person;
        }
    }

    protected abstract static class DecimalVital<T extends SerializableVital> extends SerializationProxy<T> {

        private final double observedValue;

        public DecimalVital(final Matcher matcher) {
            super(matcher);
            this.observedValue = Double.parseDouble(matcher.group("number"));
        }

        public DecimalVital(final T vital) {
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

    protected abstract static class IntegralVital<T extends SerializableVital> extends SerializationProxy<T> {
        private final int observedValue;

        public IntegralVital(final T vital) {
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
