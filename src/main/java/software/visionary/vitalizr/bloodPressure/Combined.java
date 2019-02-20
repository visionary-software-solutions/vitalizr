package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.Fraction;
import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.Objects;
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
}
