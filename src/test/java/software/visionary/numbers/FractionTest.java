package software.visionary.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionTest {
    @Test
    void canMakeFraction() {
        final int numerator = 1;
        final int denominator = 2;
        final Fraction made = new Fraction(numerator, denominator);
        Assertions.assertEquals(numerator, made.getNumerator());
        Assertions.assertEquals(denominator, made.getDenominator());
    }

    @Test
    void rejectsNullDenominator() {
        Assertions.assertThrows(NullPointerException.class, () -> new Fraction(4,null));
    }

    @Test
    void rejectsNullNumerator() {
        Assertions.assertThrows(NullPointerException.class, () -> new Fraction(null,3));
    }

    @Test
    void rejects0Denominator() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Fraction(4,0));
    }

    @Test
    void rejectsStringsThatDoNotContainSlash() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Fraction.valueOf("blah blah"));
    }

    @Test
    void rejectsStringsThatHaveTooManySlashes() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Fraction.valueOf("4/3/5"));
    }

    @Test
    void canParseStringsIntoFractions() {
        final Fraction result = Fraction.valueOf("4/3");
        Assertions.assertEquals(0, new Fraction(4,3).compareTo(result));
    }

    @Test
    void intValueDoesFlooring() {
        final Fraction f = new Fraction(2,3);
        Assertions.assertEquals(0, f.intValue());
    }

    @Test
    void doubleValueIsExact() {
        final Fraction f = new Fraction(2,3);
        Assertions.assertEquals(2.0/3.0, f.doubleValue());
    }

    @Test
    void longValueDoesFlooring() {
        final Fraction f = new Fraction(2L,3L);
        Assertions.assertEquals(0L, f.longValue());
    }

    @Test
    void floatValueIsExact() {
        final Fraction f = new Fraction(2f,3f);
        Assertions.assertEquals(2f/3f, f.floatValue());
    }

    @Test
    void hashCodeIsImplementedCorrectly() {
        final Fraction f = new Fraction(2,3);
        final int hash = f.hashCode();
        Assertions.assertEquals(hash, f.hashCode());
        final Fraction same = new Fraction(2, 3);
        Assertions.assertEquals(hash, same.hashCode());
        final Fraction other = new Fraction(4, 6);
        Assertions.assertNotEquals(hash, other.hashCode());
    }

    @Test
    void equalsCodeIsImplementedCorrectly() {
        final Fraction f = new Fraction(2,3);
        Assertions.assertEquals(f, f);
        final Fraction same = new Fraction(2, 3);
        Assertions.assertEquals(f, same);
        final Fraction other = new Fraction(4, 6);
        Assertions.assertNotEquals(f, other);
        Assertions.assertFalse(f.equals(null));
        Assertions.assertFalse(f.equals(Double.parseDouble("1.25")));
    }

    @Test
    void compareToIsImplementedCorrectly() {
        final Fraction f = new Fraction(2,3);
        Assertions.assertEquals(0, f.compareTo(f));
        final Fraction same = new Fraction(2, 3);
        Assertions.assertEquals(0, f.compareTo(same));
        final Fraction other = new Fraction(4, 6);
        Assertions.assertEquals(0, f.compareTo(other));
        final Fraction lessThan = new Fraction(1, 6);
        Assertions.assertEquals(1, f.compareTo(lessThan));
        final Fraction greaterThan = new Fraction(4, 3);
        Assertions.assertEquals(-1, f.compareTo(greaterThan));
        Assertions.assertThrows(NullPointerException.class, () -> f.compareTo(null));
    }

    @Test
    void toStringPrintsReasonably() {
        final Fraction f = new Fraction(2,3);
        final String result = f.toString();
        Assertions.assertEquals("2/3", result);
    }
}
