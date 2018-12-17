package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;
import java.util.Objects;

public final class ImperialTemperature implements BodyTemperature {
    private final Lifeform lifeform;
    private final Number quantity;
    private final Instant observedAt;

    public ImperialTemperature(final Lifeform lifeform, final Number quantity, final Instant observedAt) {
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
        return Fahrenheit.INSTANCE;
    }
}
