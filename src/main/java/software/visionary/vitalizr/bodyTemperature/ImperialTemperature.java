package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.SerializableVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class ImperialTemperature extends SerializableVital implements BodyTemperature {
    public ImperialTemperature(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<ImperialTemperature> deserialize(final Stream<String> toConvert) {
        return toConvert.map(ImperialTemperatureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(ImperialTemperatureSerializationProxy::toVital);
    }

    public ImperialTemperatureSerializationProxy asSerializationProxy() {
        return new ImperialTemperatureSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Fahrenheit.INSTANCE;
    }

    private static final class ImperialTemperatureSerializationProxy extends DecimalVital {
        private static final String FIELD_DELIMITER = "\uD83E\uDD75";
        private static final String RECORD_DELIMITER = "\uD83E\uDD76";

        public ImperialTemperatureSerializationProxy(final ImperialTemperature imperialTemperature) {
            super(imperialTemperature);
        }

        public ImperialTemperatureSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        private static List<ImperialTemperatureSerializationProxy> parse(final String entry) {
            final List<ImperialTemperatureSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                discovered.add(new ImperialTemperatureSerializationProxy(matcher));
            }
            return discovered;
        }

        @Override
        public ImperialTemperature toVital() {
            return new ImperialTemperature(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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
