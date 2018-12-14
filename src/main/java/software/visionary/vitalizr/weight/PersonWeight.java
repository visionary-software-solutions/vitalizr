package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Objects;

public final class PersonWeight implements Vital {
    private final Person person;
    private final Weight weight;

    public PersonWeight(final Person person, final Weight weight) {
        this.person = Objects.requireNonNull(person);
        this.weight = Objects.requireNonNull(weight);
    }

    @Override
    public Unit getUnit() {
        return weight.getUnit();
    }

    @Override
    public Number getQuantity() {
        return weight.getQuantity();
    }

    @Override
    public Instant observedAt() {
        return weight.observedAt();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonWeight that = (PersonWeight) o;
        return Objects.equals(getPerson(), that.getPerson()) &&
                Objects.equals(weight, that.weight);
    }

    @Override
    public Person getPerson() {
        return person;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPerson(), weight);
    }
}
