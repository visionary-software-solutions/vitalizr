package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class ImperialWeight extends SerializableVital implements Weight {

    public ImperialWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return Pound.INSTANCE;
    }

    public static Stream<ImperialWeight> deserialize(final Stream<String> toConvert) {
        return toConvert.map(ImperialWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(ImperialWeightSerializationProxy::toVital);
    }

    @Override
    public ImperialWeightSerializationProxy asSerializationProxy() {
        return new ImperialWeightSerializationProxy(observedAt(),
                getQuantity().doubleValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    private static final class ImperialWeightSerializationProxy extends DecimalVital {
        private static final String FIELD_DELIMITER = "\uD83D\uDC51";
        private static final String RECORD_DELIMITER = "\uD83E\uDE00";

        private ImperialWeightSerializationProxy(final Instant time, final double value, final String unit, final String life) {
            super(time, value, unit, life);
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

        private static List<ImperialWeightSerializationProxy> parse(final String entry) {
            final List<ImperialWeightSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final double value = Double.parseDouble(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final ImperialWeightSerializationProxy toAdd = new ImperialWeightSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
        }

    }
}
