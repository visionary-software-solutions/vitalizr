package software.visionary.vitalizr.bloodPressure;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

public final class Combined implements BloodPressure {
    private final Systolic systolic;
    private final Diastolic diastolic;

    public Combined(final Systolic top, final Diastolic bottom) {
        systolic = Objects.requireNonNull(top);
        diastolic = Objects.requireNonNull(bottom);
    }

    @Override
    public Number getQuantity() {
        return systolic.getQuantity().doubleValue() / diastolic.getQuantity().doubleValue();
    }

    @Override
    public Instant observedAt() {
        return Stream.of(systolic.observedAt(), diastolic.observedAt()).max(Instant::compareTo).get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combined combined = (Combined) o;
        return Objects.equals(systolic, combined.systolic) &&
                Objects.equals(diastolic, combined.diastolic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systolic, diastolic);
    }
}
