package software.visionary.vitalizr.api;

import java.util.Objects;

public final class Name {
    private final String letters;

    public Name(final String input) {
        letters = Objects.requireNonNull(input);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(letters, name.letters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(letters);
    }

    @Override
    public String toString() {
        return letters;
    }
}
