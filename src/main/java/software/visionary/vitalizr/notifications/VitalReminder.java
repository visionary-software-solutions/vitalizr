package software.visionary.vitalizr.notifications;

import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Objects;

public final class VitalReminder implements Reminder {
    private final Instant scheduled;
    private final Person toRemind;
    private final Class<?> vitalType;

    public VitalReminder(final Instant scheduled, final Person toRemind, final Class<?> vitalType) {
        this.scheduled = scheduled;
        this.toRemind = toRemind;
        this.vitalType = vitalType;
    }

    @Override
    public Instant scheduledFor() {
        return scheduled;
    }

    @Override
    public Person target() {
        return toRemind;
    }

    @Override
    public String toString() {
        return String.format("Remind %s to check %s at %s", toRemind, vitalType.getSimpleName(), scheduled);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VitalReminder that = (VitalReminder) o;
        return Objects.equals(scheduled, that.scheduled) &&
                Objects.equals(toRemind, that.toRemind) &&
                Objects.equals(vitalType, that.vitalType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scheduled, toRemind, vitalType);
    }
}
