package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonBloodSugar implements Vital<Person> {
    private final Person person;
    private final BloodSugar level;

    public PersonBloodSugar(final Person person, final BloodSugar level) {
        this.person = Objects.requireNonNull(person);
        this.level = Objects.requireNonNull(level);
    }

    @Override
    public Unit getUnit() {
        return level.getUnit();
    }

    @Override
    public Number getQuantity() {
        return level.getQuantity();
    }

    @Override
    public Instant observedAt() {
        return level.observedAt();
    }

    @Override
    public String toString() {
        return "PersonBloodSugar{" +
                "person=" + person +
                ", level=" + level +
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
        final PersonBloodSugar that = (PersonBloodSugar) o;
        return Objects.equals(belongsTo(), that.belongsTo()) &&
                Objects.equals(level, that.level);
    }

    @Override
    public Person belongsTo() {
        return person;
    }

    @Override
    public int hashCode() {

        return Objects.hash(belongsTo(), level);
    }
}
