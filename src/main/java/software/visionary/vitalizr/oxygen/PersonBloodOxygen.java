package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonBloodOxygen implements Vital<Person> {
    private final Person person;
    private final BloodOxygen o2;

    public PersonBloodOxygen(final Person person, final BloodOxygen o2) {
        this.person = Objects.requireNonNull(person);
        this.o2 = Objects.requireNonNull(o2);
    }

    @Override
    public Unit getUnit() {
        return o2.getUnit();
    }

    @Override
    public Number getQuantity() {
        return o2.getQuantity();
    }

    @Override
    public Instant observedAt() {
        return o2.observedAt();
    }

    @Override
    public String toString() {
        return "PersonBloodOxygen{" +
                "person=" + person +
                ", o2=" + o2 +
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
        final PersonBloodOxygen that = (PersonBloodOxygen) o;
        return Objects.equals(belongsTo(), that.belongsTo()) &&
                Objects.equals(o2, that.o2);
    }

    @Override
    public Person belongsTo() {
        return person;
    }

    @Override
    public int hashCode() {

        return Objects.hash(belongsTo(), o2);
    }
}
