package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public final class MetricTemperature implements BodyTemperature {
    private final Lifeform lifeform;
    private final Number quantity;
    private final Instant observedAt;

    public MetricTemperature(final Lifeform lifeform, final Number quantity, final Instant observedAt) {
        this.lifeform = Objects.requireNonNull(lifeform);
        this.quantity = Objects.requireNonNull(quantity);
        this.observedAt = Objects.requireNonNull(observedAt);
    }

    @Override
    public Lifeform belongsTo() {
        return lifeform;
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
    public Unit getUnit() {
        return Celsius.INSTANCE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MetricTemperature that = (MetricTemperature) o;
        return lifeform.equals(that.lifeform) &&
                getQuantity().equals(that.getQuantity()) &&
                observedAt.equals(that.observedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifeform, getQuantity(), observedAt);
    }
}
