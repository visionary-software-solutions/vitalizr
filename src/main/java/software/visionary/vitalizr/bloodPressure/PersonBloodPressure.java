package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonBloodPressure implements Vital {
    private final Person person;
    private final BloodPressure bloodPressure;

    public PersonBloodPressure(Person person, BloodPressure toStore) {
        this.person = Objects.requireNonNull(person);
        this.bloodPressure = Objects.requireNonNull(toStore);
    }

    @Override
    public Person getPerson() {
        return person;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonBloodPressure that = (PersonBloodPressure) o;
        return Objects.equals(getPerson(), that.getPerson()) &&
                Objects.equals(bloodPressure, that.bloodPressure);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPerson(), bloodPressure);
    }
}
