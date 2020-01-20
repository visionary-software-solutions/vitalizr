package software.visionary.vitalizr.bodyMassIndex;

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

public final class QueteletIndex extends SerializableVital implements BodyMassIndex {
    public QueteletIndex(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<QueteletIndex> deserialize(final Stream<String> toConvert) {
        return toConvert.map(QuetletIndexSerializationProxy::parse)
                .flatMap(List::stream)
                .map(QuetletIndexSerializationProxy::toVital);
    }

    public QuetletIndexSerializationProxy asSerializationProxy() {
        return new QuetletIndexSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return KilogramsPerMetersSquared.INSTANCE;
    }

    private static final class QuetletIndexSerializationProxy extends DecimalVital {
        private static final String FIELD_DELIMITER = "\uD83E\uDDE0";
        private static final String RECORD_DELIMITER = "\uD83E\uDD2E";

        public QuetletIndexSerializationProxy(final QueteletIndex queteletIndex) {
            super(queteletIndex);
        }

        public QuetletIndexSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        private static List<QuetletIndexSerializationProxy> parse(final String entry) {
            final List<QuetletIndexSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                discovered.add(new QuetletIndexSerializationProxy(matcher));
            }
            return discovered;
        }

        @Override
        public QueteletIndex toVital() {
            return new QueteletIndex(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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
