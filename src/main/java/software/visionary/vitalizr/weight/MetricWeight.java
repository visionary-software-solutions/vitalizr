package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.Objects;

public final class MetricWeight implements Weight {
    private final Instant observed;
    private final NaturalNumber number;
    private final Lifeform owner;

    private MetricWeight(final Instant observed, final NaturalNumber number, final Lifeform lifeform) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.owner = Objects.requireNonNull(lifeform);
    }

    public static MetricWeight inKilograms(final Number kilos, final Instant observedAt, final Lifeform lifeform) {
        return new MetricWeight(observedAt, new NaturalNumber(kilos.intValue() * 1000), lifeform);
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
                Objects.equals(getUnit(), weight.getUnit()) &&
                Objects.equals(belongsTo(), weight.belongsTo());
    }

    @Override
    public Lifeform belongsTo() {
        return owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observed, number, getUnit(), belongsTo());
    }
}
