package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.AbstractVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class BioelectricalImpedance extends AbstractVital implements BodyWaterPercentage {
    public BioelectricalImpedance(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    public static Stream<BioelectricalImpedance> deserialize(final Stream<String> toConvert) {
        return toConvert.map(BioelectricalImpedanceSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> new BioelectricalImpedance(toConvert1.getObservationTimestamp(), toConvert1.getObservedValue(), Human.createPerson(toConvert1.getPerson())));
    }

    public BioelectricalImpedanceSerializationProxy asSerializationProxy() {
        return new BioelectricalImpedanceSerializationProxy(observedAt(),
                getQuantity().doubleValue(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Unit getUnit() {
        return Unit.NONE.INSTANCE;
    }

    private static final class BioelectricalImpedanceSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83E\uDD3D";
        private static final String RECORD_DELIMITER = "\uD83E\uDD3F";
        private final Instant observationTimestamp;
        private final double observedValue;
        private final String observedUnit;
        private final String person;

        private BioelectricalImpedanceSerializationProxy(final Instant time, final double value, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = value;
            observedUnit = unit;
            person = life;
        }

        private static List<BioelectricalImpedanceSerializationProxy> parse(final String entry) {
            final List<BioelectricalImpedanceSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9.]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern sought = Pattern.compile(template);
            final Matcher matcher = sought.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final double value = Double.parseDouble(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                final BioelectricalImpedanceSerializationProxy toAdd = new BioelectricalImpedanceSerializationProxy(time, value, unit, person);
                discovered.add(toAdd);
            }
            return discovered;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%f%s%s%s%s%s",
                    RECORD_DELIMITER,
                    getObservationTimestamp(),
                    FIELD_DELIMITER,
                    getObservedValue(),
                    FIELD_DELIMITER,
                    observedUnit,
                    FIELD_DELIMITER,
                    getPerson(),
                    RECORD_DELIMITER);
        }

        private Instant getObservationTimestamp() {
            return observationTimestamp;
        }

        private double getObservedValue() {
            return observedValue;
        }

        private String getPerson() {
            return person;
        }
    }
}
