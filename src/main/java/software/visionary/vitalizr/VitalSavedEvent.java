package software.visionary.vitalizr;

import software.visionary.eventr.Event;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

final class VitalSavedEvent implements Event {
    private final Instant occurredAt;
    private final Vital saved;

    VitalSavedEvent(final Vital saved) {
        this.saved = Objects.requireNonNull(saved);
        occurredAt = Instant.now();
    }

    Instant getOccurredAt() {
        return occurredAt;
    }

    Vital getVital() {
        return saved;
    }
}
