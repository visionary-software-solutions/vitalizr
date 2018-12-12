package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.NaturalNumber;

import java.time.Instant;
import java.util.Objects;

public final class Systolic implements BloodPressure {
    private final NaturalNumber quantity;
    private final Instant observedAt;

    public Systolic(final Instant measuredAt, final NaturalNumber number) {
        observedAt = Objects.requireNonNull(measuredAt);
        quantity = Objects.requireNonNull(number);
    }

    @Override
    public Number getQuantity() {
        return quantity;
    }

    @Override
    public Instant observedAt() {
        return observedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Systolic systolic = (Systolic) o;
        return Objects.equals(getQuantity(), systolic.getQuantity()) &&
                Objects.equals(observedAt, systolic.observedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), observedAt);
    }
}
