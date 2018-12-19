package software.visionary.vitalizr.notifications;

import software.visionary.vitalizr.api.TrustedContact;
import software.visionary.vitalizr.api.Vital;

import java.util.Objects;

public final class VitalNotification implements Notification {
    private final Vital vital;
    private final TrustedContact contact;

    public VitalNotification(final Vital v, final TrustedContact c) {
        vital = Objects.requireNonNull(v);
        contact = Objects.requireNonNull(c);
        if (!contact.caresAbout(v)) {
            throw new IllegalArgumentException("Invalid notification, Vital and Contact should be connected via Lifeform ");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VitalNotification that = (VitalNotification) o;
        return Objects.equals(getVital(), that.getVital()) &&
                Objects.equals(getContact(), that.getContact());
    }

    public Vital getVital() {
        return vital;
    }

    private TrustedContact getContact() {
        return contact;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVital(), getContact());
    }
}
