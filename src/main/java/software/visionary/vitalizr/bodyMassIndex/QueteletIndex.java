package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.Objects;

public final class QueteletIndex implements BodyMassIndex {
    private final Instant observed;
    private final Number number;
    private final Lifeform owner;

    public QueteletIndex(final Instant observed, final Number number, final Lifeform owner) {
        this.observed = observed;
        this.number = number;
        this.owner = owner;
    }

    @Override
    public Lifeform belongsTo() {
        return owner;
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
        final QueteletIndex that = (QueteletIndex) o;
        return Objects.equals(observed, that.observed) &&
                Objects.equals(number, that.number) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(observed, number, owner);
    }
}
