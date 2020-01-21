package software.visionary.vitalizr;

import software.visionary.vitalizr.api.*;

import java.util.Objects;
import java.util.UUID;

public final class Human implements Person {
    private final UUID id;
    private final Name name;
    private final Birthdate birthdate;
    private final EmailAddress email;
    private final Credentials credentials;

    private Human(final UUID id, final Name name, final Birthdate birthdate, final EmailAddress email) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.birthdate = Objects.requireNonNull(birthdate);
        this.email = Objects.requireNonNull(email);
        this.credentials = new PasswordCredentials(this, Integer.toBinaryString(Objects.hash(name, birthdate, email)));
    }

    public static Person createPerson(final String input) {
        final String delimiter = ":";
        final String[] tokens = input.split(delimiter);
        return new Human(UUID.fromString(tokens[0]), new Name(tokens[1]), Birthday.createFrom(tokens[2]), PersonalEmail.createFrom(tokens[3]));
    }

    @Override
    public EmailAddress getEmailAddress() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s:%s", id, name, birthdate, email);
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
    public UUID getID() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Human human = (Human) o;
        return id.equals(human.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }
}
