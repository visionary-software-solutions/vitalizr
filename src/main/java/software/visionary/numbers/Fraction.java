package software.visionary.numbers;

import java.util.Objects;

public final class Fraction extends Number implements Comparable<Fraction> {
    private final Number numerator;
    private final Number denominator;

    public Fraction(final Number numerator, final Number denominator) {
        if (Objects.requireNonNull(denominator).equals(0)) {
            throw new IllegalArgumentException("denominator cannot be zero");
        }
        this.numerator = Objects.requireNonNull(numerator);
        this.denominator = denominator;
    }

    public static Fraction valueOf(final String toParse) {
        if (!toParse.contains("/")) {
            throw new IllegalArgumentException("The string should contain a /, got " + toParse);
        }
        final String[] pieces = toParse.split("/");
        if (pieces.length > 2) {
            throw new IllegalArgumentException("We don't handle compound fractions. Create separate instances for each numerator / denominator pair.");
        }
        return new Fraction(Long.valueOf(pieces[0].trim()), Long.valueOf(pieces[1].trim()));
    }

    @Override
    public int intValue() {
        return (int) this.doubleValue();
    }

    @Override
    public double doubleValue() {
        return getNumerator().doubleValue() / getDenominator().doubleValue();
    }

    public Number getNumerator() {
        return numerator;
    }

    public Number getDenominator() {
        return denominator;
    }

    @Override
    public long longValue() {
        return (long) this.doubleValue();
    }

    @Override
    public float floatValue() {
        return getNumerator().floatValue() / getDenominator().floatValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Fraction fraction = (Fraction) o;
        return getNumerator().equals(fraction.getNumerator()) &&
                getDenominator().equals(fraction.getDenominator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumerator(), getDenominator());
    }

    @Override
    public int compareTo(final Fraction o) {
        return this.getDenominator().equals(o.getDenominator()) ?
                Integer.compare(getNumerator().intValue(), o.getNumerator().intValue()) :
                Long.compare(getNumerator().intValue() * o.getDenominator().intValue(),
                        getDenominator().intValue() * o.getNumerator().intValue());
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getNumerator(), getDenominator());
    }
}
