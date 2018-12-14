package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Birthdate;
import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Name;
import software.visionary.vitalizr.api.Person;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Objects;

public final class Human implements Person {
    private final Name name;
    private final Birthdate birthdate;
    private final EmailAddress email;

    private Human(final Name name, final Birthdate birthdate, final EmailAddress email) {
        this.name = Objects.requireNonNull(name);
        this.birthdate = Objects.requireNonNull(birthdate);
        this.email = Objects.requireNonNull(email);
    }

    public static Person createPerson(final String input) {
        final String delimiter = ":";
        final String[] tokens = input.split(delimiter);
        return new Human(new Name(tokens[0]), Birthday.createFrom(tokens[1]), PersonalEmail.createFrom(tokens[2]));
    }

    @Override
    public EmailAddress getEmailAddress() {
        return email;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name=" + name +
                ", birthdate=" + birthdate +
                ", email=" + email +
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
        final Human human = (Human) o;
        return Objects.equals(getName(), human.getName()) &&
                Objects.equals(getBirthdate(), human.getBirthdate()) &&
                Objects.equals(email, human.email);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Birthdate getBirthdate() {
        return birthdate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthdate(), email);
    }
}
