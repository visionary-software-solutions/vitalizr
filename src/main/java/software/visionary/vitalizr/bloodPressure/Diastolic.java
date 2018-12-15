package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.Objects;

public final class Diastolic implements BloodPressure {
    private final NaturalNumber quantity;
    private final Instant observedAt;
    private final Lifeform measured;

    Diastolic(final Instant measuredAt, final NaturalNumber number, final Lifeform measuree) {
        observedAt = Objects.requireNonNull(measuredAt);
        quantity = Objects.requireNonNull(number);
        measured = Objects.requireNonNull(measuree);
    }

    @Override
    public Instant observedAt() {
        return observedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Diastolic systolic = (Diastolic) o;
        return Objects.equals(getQuantity(), systolic.getQuantity()) &&
                Objects.equals(observedAt, systolic.observedAt) &&
                Objects.equals(belongsTo(), systolic.belongsTo());
    }

    @Override
    public Lifeform belongsTo() {
        return measured;
    }

    @Override
    public Number getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), observedAt, belongsTo());
    }
}
