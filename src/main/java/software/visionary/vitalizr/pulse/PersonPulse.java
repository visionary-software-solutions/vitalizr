package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonPulse implements Vital<Person> {
    private final Person person;
    private final Pulse pulse;

    public PersonPulse(final Person person, final Pulse pulse) {
        this.person = Objects.requireNonNull(person);
        this.pulse = Objects.requireNonNull(pulse);
    }

    @Override
    public Unit getUnit() {
        return pulse.getUnit();
    }

    @Override
    public Number getQuantity() {
        return pulse.getQuantity();
    }

    @Override
    public Instant observedAt() {
        return pulse.observedAt();
    }

    @Override
    public String toString() {
        return "PersonPulse{" +
                "person=" + person +
                ", pulse=" + pulse +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonPulse that = (PersonPulse) o;
        return Objects.equals(belongsTo(), that.belongsTo()) &&
                Objects.equals(pulse, that.pulse);
    }

    @Override
    public Person belongsTo() {
        return person;
    }

    @Override
    public int hashCode() {

        return Objects.hash(belongsTo(), pulse);
    }
}
