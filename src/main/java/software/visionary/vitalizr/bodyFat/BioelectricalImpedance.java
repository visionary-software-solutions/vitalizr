package software.visionary.vitalizr.bodyFat;

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

public final class BioelectricalImpedance extends SerializableVital implements BodyFatPercentage {
    public BioelectricalImpedance(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<BioelectricalImpedance> deserialize(final Stream<String> toConvert) {
        return toConvert.map(BioelectricalImpedanceSerializationProxy::parse)
                .flatMap(List::stream)
                .map(BioelectricalImpedanceSerializationProxy::toVital);
    }

    public BioelectricalImpedanceSerializationProxy asSerializationProxy() {
        return new BioelectricalImpedanceSerializationProxy(this);
    }

    @Override
    public Unit getUnit() {
        return Unit.NONE.INSTANCE;
    }

    private static final class BioelectricalImpedanceSerializationProxy extends DecimalVital {
        private static final String FIELD_DELIMITER = "\uD83E\uDD6E";
        private static final String RECORD_DELIMITER = "\uD83E\uDD5E";


        public BioelectricalImpedanceSerializationProxy(final BioelectricalImpedance bioelectricalImpedance) {
            super(bioelectricalImpedance);
        }

        public BioelectricalImpedanceSerializationProxy(final Matcher matcher) {
            super(matcher);
        }

        private static List<BioelectricalImpedanceSerializationProxy> parse(final String entry) {
            final List<BioelectricalImpedanceSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                discovered.add(new BioelectricalImpedanceSerializationProxy(matcher));
            }
            return discovered;
        }

        @Override
        public BioelectricalImpedance toVital() {
            return new BioelectricalImpedance(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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
