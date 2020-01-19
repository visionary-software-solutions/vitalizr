package software.visionary.vitalizr.oxygen;

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

public final class PeripheralOxygenSaturation extends SerializableVital implements BloodOxygen {
    public PeripheralOxygenSaturation(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<PeripheralOxygenSaturation> fromSerialized(final Stream<String> toConvert) {
        return toConvert.map(PeripheralOxygenSaturationSerializationProxy::parse)
                .flatMap(List::stream)
                .map(PeripheralOxygenSaturationSerializationProxy::toVital);
    }

    public PeripheralOxygenSaturationSerializationProxy asSerializationProxy() {
        return new PeripheralOxygenSaturationSerializationProxy(observedAt(),
                getQuantity().intValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Unit getUnit() {
        return OxygenSaturation.INSTANCE;
    }

    private static final class PeripheralOxygenSaturationSerializationProxy extends IntegralVital  {
        private static final String FIELD_DELIMITER = "\uD83E\uDD71";
        private static final String RECORD_DELIMITER = "\uD83E\uDDA8";

        private PeripheralOxygenSaturationSerializationProxy(final Instant time, final int value, final String unit, final String life) {
            super(time, value, unit, life);
        }

        private static List<PeripheralOxygenSaturationSerializationProxy> parse(final String entry) {
            final List<PeripheralOxygenSaturationSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final int value = Integer.parseInt(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final PeripheralOxygenSaturationSerializationProxy toAdd = new PeripheralOxygenSaturationSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
        }

        @Override
        public PeripheralOxygenSaturation toVital() {
            return new PeripheralOxygenSaturation(getObservationTimestamp(), getObservedValue(), Human.createPerson(getPerson()));
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
