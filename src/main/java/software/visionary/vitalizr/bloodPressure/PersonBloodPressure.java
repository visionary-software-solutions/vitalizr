package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonBloodPressure implements Vital<Person> {
    private final Person person;
    private final BloodPressure bloodPressure;

    public PersonBloodPressure(final Person person, final BloodPressure toStore) {
        this.person = Objects.requireNonNull(person);
        this.bloodPressure = Objects.requireNonNull(toStore);
    }

    @Override
    public Unit getUnit() {
        return bloodPressure.getUnit();
    }

    @Override
    public Number getQuantity() {
        return bloodPressure.getQuantity();
    }

    @Override
    public Instant observedAt() {
        return bloodPressure.observedAt();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonBloodPressure that = (PersonBloodPressure) o;
        return Objects.equals(belongsTo(), that.belongsTo()) &&
                Objects.equals(bloodPressure, that.bloodPressure);
    }

    @Override
    public Person belongsTo() {
        return person;
    }

    @Override
    public int hashCode() {

        return Objects.hash(belongsTo(), bloodPressure);
    }
}
