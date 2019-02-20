package software.visionary.vitalizr;

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
}
