package software.visionary.vitalizr.bloodPressure;

import software.visionary.numbers.Fraction;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.numbers.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Combined implements BloodPressure {
    private final Systolic systolic;
    private final Diastolic diastolic;

    public Combined(final Systolic top, final Diastolic bottom) {
        systolic = Objects.requireNonNull(top);
        diastolic = Objects.requireNonNull(bottom);
        if (!systolic.belongsTo().equals(diastolic.belongsTo())) {
            throw new IllegalArgumentException("Cannot combine blood pressures from two different lifeforms");
        }
    }

    public static Combined systolicAndDiastolicBloodPressure(final Instant observedAt, final int systolic, final int diastolic, final Lifeform measured) {
        return new Combined(new Systolic(observedAt, new NaturalNumber(systolic), measured), new Diastolic(observedAt, new NaturalNumber(diastolic), measured));
    }

    public static Stream<Combined> deserialize(final Stream<String> toConvert) {
        return toConvert.map(CombinedBloodPressureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(toConvert1 -> systolicAndDiastolicBloodPressure(toConvert1.getObservationTimestamp(),
                        toConvert1.getObservedValue().getNumerator().intValue(),
                        toConvert1.getObservedValue().getDenominator().intValue(),
                        Human.createPerson(toConvert1.getPerson())));
    }

    public CombinedBloodPressureSerializationProxy toSerializationProxy() {
        return new CombinedBloodPressureSerializationProxy(observedAt(),
                (Fraction) getQuantity(),
                getUnit().getSymbol(),
                new LifeformSerializationProxy(belongsTo()).toString());
    }

    @Override
    public Number getQuantity() {
        return new Fraction(systolic.getQuantity(), diastolic.getQuantity());
    }

    @Override
    public Instant observedAt() {
        return Stream.of(systolic.observedAt(), diastolic.observedAt()).max(Instant::compareTo).get();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Combined combined = (Combined) o;
        return Objects.equals(systolic, combined.systolic) &&
                Objects.equals(diastolic, combined.diastolic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systolic, diastolic);
    }

    @Override
    public Lifeform belongsTo() {
        return systolic.belongsTo();
    }

    @Override
    public Unit getUnit() {
        return MillimetersOfMercury.INSTANCE;
    }

    private static final class CombinedBloodPressureSerializationProxy {
        private static final String FIELD_DELIMITER = "\uD83C\uDFAF";
        private static final String RECORD_DELIMITER = "\uD83E\uDD28";
        private final Instant observationTimestamp;
        private final Fraction observedValue;
        private final String observedUnit;
        private final String person;

        private CombinedBloodPressureSerializationProxy(final Instant time, final Fraction quantity, final String unit, final String life) {
            observationTimestamp = time;
            observedValue = quantity;
            observedUnit = unit;
            person = life;
        }

        private static List<CombinedBloodPressureSerializationProxy> parse(final String entry) {
            final List<CombinedBloodPressureSerializationProxy> discovered = new ArrayList<>();
            final String template = String.format("%s(?<time>.*?)%s(?<number>[0-9./]+)%s(?<unit>.*?)%s(?<person>.*?)%s", RECORD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, FIELD_DELIMITER, RECORD_DELIMITER);
            final Pattern pattern = Pattern.compile(template);
            final Matcher matcher = pattern.matcher(entry);
            while (matcher.find()) {
                final Instant time = Instant.parse(matcher.group("time"));
                final Fraction theFraction = Fraction.valueOf(matcher.group("number"));
                final String unit = matcher.group("unit");
                final String person = matcher.group("person");
                discovered.add(new CombinedBloodPressureSerializationProxy(time, theFraction, unit, person));
            }
            return discovered;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%s%s%s%s%s%s",
                    RECORD_DELIMITER,
                    getObservationTimestamp(),
                    FIELD_DELIMITER,
                    getObservedValue(),
                    FIELD_DELIMITER,
                    getObservedUnit(),
                    FIELD_DELIMITER,
                    person,
                    RECORD_DELIMITER);
        }

        private Instant getObservationTimestamp() {
            return observationTimestamp;
        }

        private Fraction getObservedValue() {
            return observedValue;
        }

        private String getObservedUnit() {
            return observedUnit;
        }

        private String getPerson() {
            return person;
        }
    }
}
