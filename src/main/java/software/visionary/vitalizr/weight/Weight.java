package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public final class Weight implements Scalable {
    private final Instant observed;
    private final NaturalNumber number;
    private final Unit unit;

    public Weight(final Instant observed, final NaturalNumber number, final Unit unit) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.unit = Objects.requireNonNull(unit);
    }

    public static Weight inKilograms(final int kilos, final Instant observedAt) {
        return new Weight(observedAt, new NaturalNumber(kilos * 1000), new Gram());
    }

    @Override
    public Unit getUnit() {
        return unit;
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
        final Weight weight = (Weight) o;
        return Objects.equals(number, weight.number) &&
                Objects.equals(getUnit(), weight.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, getUnit());
    }
}
