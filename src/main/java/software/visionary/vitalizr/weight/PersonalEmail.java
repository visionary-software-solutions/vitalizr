package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Name;

import java.util.Objects;

public final class PersonalEmail implements EmailAddress {
    private final Name userName;
    private final Name domain;

    private PersonalEmail(final Name userName, final Name domain) {
        this.userName = Objects.requireNonNull(userName);
        this.domain = Objects.requireNonNull(domain);
    }

    static EmailAddress createFrom(final String input) {
        final String[] parts = input.split("@");
        return new PersonalEmail(new Name(parts[0]), new Name(parts[1]));
    }

    @Override
    public Name getName() {
        return userName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonalEmail that = (PersonalEmail) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(getDomain(), that.getDomain());
    }

    @Override
    public Name getDomain() {
        return domain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, getDomain());
    }

    @Override
    public String toString() {
        return String.format("%s@%s", userName, domain);
    }
}
