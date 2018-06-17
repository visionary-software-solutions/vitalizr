package visionarysoftwaresolutions.vitalizr.weight;

import visionarysoftwaresolutions.vitalizr.api.Scalable;
import visionarysoftwaresolutions.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public final class Weight implements Scalable {
    private final Instant observed;
    private final Number number;
    private final Unit unit;

    public Weight(final Instant observed, final Number number, final Unit unit) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.unit = Objects.requireNonNull(unit);
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
