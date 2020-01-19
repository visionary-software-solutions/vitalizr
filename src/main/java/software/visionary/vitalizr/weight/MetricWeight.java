package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.*;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class MetricWeight extends SerializableVital implements Weight {
    public MetricWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return Gram.INSTANCE;
    }

    public static Stream<MetricWeight> deserialize(final Stream<String> toConvert) {
        return toConvert.map(MetricWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(MetricWeightSerializationProxy::toVital);
    }

    public MetricWeightSerializationProxy asSerializationProxy() {
        return new MetricWeightSerializationProxy(observedAt(),
                getQuantity().doubleValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    private static final class MetricWeightSerializationProxy extends DecimalVital {
        private static final String FIELD_DELIMITER = "💩";
        private static final String RECORD_DELIMITER = "🤯";

        protected MetricWeightSerializationProxy(final Instant time, final double value, final String unit, final String life) {
            super(time, value, unit, life);
        }

        private static List<MetricWeightSerializationProxy> parse(final String entry) {
            final List<MetricWeightSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final double value = Double.parseDouble(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final MetricWeightSerializationProxy toAdd = new MetricWeightSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
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
}
