package visionarysoftwaresolutions.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import visionarysoftwaresolutions.vitalizr.api.Unit;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

class WeightTest {
    Instant observedAt;
    Number value;
    Unit unit = new Gram();
    Weight toTest;

    @BeforeEach
    void setup () {
        observedAt = Instant.now();
        value = ThreadLocalRandom.current().nextDouble();
        toTest = new Weight(observedAt, value, unit);
    }

    @Test
    void rejectsNullInstant() {
        Assertions.assertThrows(NullPointerException.class, () -> new Weight(null, value, unit));
    }

    @Test
    void rejectsNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> new Weight(observedAt, null, unit));
    }

    @Test
    void rejectsNullUnit() {
        Assertions.assertThrows(NullPointerException.class, () -> new Weight(observedAt, value, null));
    }

    @Test
    void canGetInstant() {
        Assertions.assertEquals(observedAt, toTest.observedAt());
    }

    @Test
    void canGetQuantity() {
        Assertions.assertEquals(value, toTest.getQuantity());
    }

    @Test
    void canGetUnit() {
        Assertions.assertEquals(unit, toTest.getUnit());
    }

    @Test
    void implementsHashCodeCorrectly() {
        Assertions.assertEquals(toTest.hashCode(), toTest.hashCode());
        final Weight another = new Weight(observedAt, value, unit);
        Assertions.assertEquals(toTest.hashCode(), another.hashCode());
        Assertions.assertEquals(another.hashCode(), toTest.hashCode());
    }

    @Test
    void implementsEqualsCorrectly() {
        Assertions.assertNotEquals(toTest, null);
        Assertions.assertNotEquals(toTest, new Object());
        Assertions.assertEquals(toTest, toTest);
        final Weight another = new Weight(observedAt, value, unit);
        Assertions.assertEquals(toTest, another);
        Assertions.assertEquals(another, toTest);
        final Weight aThird= new Weight(observedAt, value, unit);
        Assertions.assertEquals(another, aThird);
        Assertions.assertEquals(toTest, aThird);
    }
}
