package software.visionary.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.Randomizr;

class NaturalNumberTest {
    @Test
    void canMakeNaturalNumber() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(naturalNumber, made.intValue());
    }

    private int createNaturalNumber() {
        return Randomizr.INSTANCE.createRandomNumberBetween(0, Integer.MAX_VALUE);
    }

    @Test
    void rejectsNegativeNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new NaturalNumber(-1));
    }

    @Test
    void intValueReturnsNumber() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(naturalNumber, made.intValue());
    }

    @Test
    void doubleValueReturnsNumber() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(naturalNumber, made.doubleValue());
    }

    @Test
    void longValueReturnsNumber() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(naturalNumber, made.longValue());
    }

    @Test
    void floatValueReturnsNumber() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(naturalNumber, made.floatValue());
    }

    @Test
    void hashCodeIsImplementedCorrectly() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        final int hash = made.hashCode();
        Assertions.assertEquals(hash, made.hashCode());
        final NaturalNumber same = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(hash, same.hashCode());
        final NaturalNumber other = new NaturalNumber(createNaturalNumber());
        Assertions.assertNotEquals(hash, other.hashCode());
    }

    @Test
    void equalsCodeIsImplementedCorrectly() {
        final int naturalNumber = createNaturalNumber();
        final NaturalNumber made = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(made, made);
        final NaturalNumber same = new NaturalNumber(naturalNumber);
        Assertions.assertEquals(made, same);
        final NaturalNumber other = new NaturalNumber(createNaturalNumber());
        Assertions.assertNotEquals(made, other);
        Assertions.assertFalse(made.equals(null));
        Assertions.assertFalse(made.equals(Integer.valueOf(naturalNumber)));
    }

    @Test
    void compareToIsImplementedCorrectly() {
        final int randomInt = createNaturalNumber();
        final NaturalNumber f = new NaturalNumber(randomInt);
        Assertions.assertEquals(0, f.compareTo(f));
        final NaturalNumber same = new NaturalNumber(randomInt);
        Assertions.assertEquals(0, f.compareTo(same));
        final int anotherRandomInt = createNaturalNumber();
        final NaturalNumber other = new NaturalNumber(anotherRandomInt);
        Assertions.assertEquals(Integer.compare(randomInt, anotherRandomInt), f.compareTo(other));
        final int less = Randomizr.INSTANCE.createRandomNumberBetween(0, randomInt - 1);
        final NaturalNumber lessThan = new NaturalNumber(less);
        Assertions.assertEquals(1, f.compareTo(lessThan));
        final int more = Randomizr.INSTANCE.createRandomNumberBetween(randomInt + 1, Integer.MAX_VALUE);
        final NaturalNumber greaterThan = new NaturalNumber(more);
        Assertions.assertEquals(-1, f.compareTo(greaterThan));
    }

    @Test
    void toStringPrintsReasonably() {
        final NaturalNumber f = new NaturalNumber(2);
        final String result = f.toString();
        Assertions.assertEquals("2", result);
    }
}
