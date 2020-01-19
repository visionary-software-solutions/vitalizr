package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public abstract class AbstractVital {
    private final Instant observed;
    private final Number number;
    private final Lifeform owner;

    public AbstractVital(final Instant observed, final Number number, final Lifeform lifeform) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.owner = Objects.requireNonNull(lifeform);
    }

    public abstract Unit getUnit();

    public Number getQuantity() {
        return number;
    }

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
        final AbstractVital weight = (AbstractVital) o;
        return Objects.equals(observed, weight.observed) &&
                Objects.equals(number, weight.number) &&
                Objects.equals(getUnit(), weight.getUnit()) &&
                Objects.equals(belongsTo(), weight.belongsTo());
    }

    public Lifeform belongsTo() {
        return owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observed, number, getUnit(), belongsTo());
    }
}
