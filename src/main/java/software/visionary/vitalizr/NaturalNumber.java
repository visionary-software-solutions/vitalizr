package software.visionary.vitalizr;

import java.util.Comparator;

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
    public int compareTo(NaturalNumber o) {
        return Comparator.nullsFirst(Comparator.comparingInt((NaturalNumber n) -> n.number)).compare(this, o);
    }

    @Override
    public String toString() { return String.valueOf(number); }
}
