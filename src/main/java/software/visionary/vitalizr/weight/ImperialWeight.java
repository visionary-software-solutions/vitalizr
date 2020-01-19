package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public final class ImperialWeight implements Weight {
    private final Instant observed;
    private final Number number;
    private final Lifeform owner;

    public ImperialWeight(final Instant observed, final Number number, final Lifeform lifeform) {
        this.observed = Objects.requireNonNull(observed);
        this.number = Objects.requireNonNull(number);
        this.owner = Objects.requireNonNull(lifeform);
    }

    @Override
    public Unit getUnit() {
        return Pound.INSTANCE;
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
        final ImperialWeight weight = (ImperialWeight) o;
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
