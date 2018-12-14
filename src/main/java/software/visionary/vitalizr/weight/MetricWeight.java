package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.NaturalNumber;

import java.time.Instant;
import java.util.Objects;

public final class MetricWeight implements Weight {
    private final Instant observed;
    private final NaturalNumber number;

    private MetricWeight(final Instant observed, final NaturalNumber number) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
    }

    public static Weight inKilograms(final Number kilos, final Instant observedAt) {
        return new MetricWeight(observedAt, new NaturalNumber(kilos.intValue() * 1000));
    }

    @Override
    public Number getQuantity() {
        return number;
    }

    @Override
    public Instant observedAt() {
        return observed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MetricWeight weight = (MetricWeight) o;
        return Objects.equals(observed, weight.observed) &&
                Objects.equals(number, weight.number) &&
                Objects.equals(getUnit(), weight.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(observed, number, getUnit());
    }
}
