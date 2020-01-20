package software.visionary.vitalizr.bloodSugar;

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

public final class WholeBloodGlucose extends SerializableVital implements BloodSugar {
    public WholeBloodGlucose(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<WholeBloodGlucose> deserialize(final Stream<String> toConvert) {
        return toConvert.map(WholeBloodGlucoseSerializationProxy::parse)
                .flatMap(List::stream)
                .map(WholeBloodGlucoseSerializationProxy::toVital);
    }

    public WholeBloodGlucoseSerializationProxy asSerializationProxy() {
        return new WholeBloodGlucoseSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Millimolar.INSTANCE;
    }

    private static final class WholeBloodGlucoseSerializationProxy extends IntegralVital {
        private static final String FIELD_DELIMITER = "\uD83C\uDF70";
        private static final String RECORD_DELIMITER = "\uD83E\uDD22";

        public WholeBloodGlucoseSerializationProxy(final WholeBloodGlucose wholeBloodGlucose) {
            super(wholeBloodGlucose);
        }

        public WholeBloodGlucoseSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        private static List<WholeBloodGlucoseSerializationProxy> parse(final String entry) {
            final List<WholeBloodGlucoseSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                discovered.add(new WholeBloodGlucoseSerializationProxy(matcher));
            }
            return discovered;
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
}
