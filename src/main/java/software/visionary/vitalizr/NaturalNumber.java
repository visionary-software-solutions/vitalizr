package software.visionary.vitalizr;

import java.util.Comparator;
import java.util.Objects;

public final class NaturalNumber extends Number implements Comparable<NaturalNumber> {
    private final int number;

    public NaturalNumber(final int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Invalid Natural Number, must be > 0");
        }
        this.number = number;
    }

    @Override
    public int intValue() {
        return number;
    }

    @Override
    public long longValue() {
        return number;
    }

    @Override
    public float floatValue() {
        return number;
    }

    @Override
    public double doubleValue() {
        return number;
    }

    @Override
    public int compareTo(final NaturalNumber o) {
        return Comparator.nullsFirst(Comparator.comparingInt((NaturalNumber n) -> n.number)).compare(this, o);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NaturalNumber that = (NaturalNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
