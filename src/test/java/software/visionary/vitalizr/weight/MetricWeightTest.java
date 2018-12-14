package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.NaturalNumber;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

class MetricWeightTest {
    private Instant observedAt;
    private Number value;
    private Weight toTest;

    @BeforeEach
    void setup() {
        observedAt = Instant.now();
        value = ThreadLocalRandom.current().nextInt(0, 300);
        toTest = MetricWeight.inKilograms(value, observedAt);
    }

    @Test
    void rejectsNullInstant() {
        Assertions.assertThrows(NullPointerException.class, () -> MetricWeight.inKilograms(value, null));
    }

    @Test
    void rejectsNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> MetricWeight.inKilograms(null, observedAt));
    }

    @Test
    void canGetInstant() {
        Assertions.assertEquals(observedAt, toTest.observedAt());
    }

    @Test
    void getQuantityConvertsToKilograms() {
        Assertions.assertEquals(new NaturalNumber(value.intValue() * 1000), toTest.getQuantity());
    }

    @Test
    void defaultsToGrams() {
        Assertions.assertEquals(Gram.INSTANCE, toTest.getUnit());
    }

    @Test
    void implementsHashCodeCorrectly() {
        Assertions.assertEquals(toTest.hashCode(), toTest.hashCode());
        final Weight another = MetricWeight.inKilograms(value, observedAt);
        Assertions.assertEquals(toTest.hashCode(), another.hashCode());
        Assertions.assertEquals(another.hashCode(), toTest.hashCode());
    }

    @Test
    void implementsEqualsCorrectly() {
        Assertions.assertNotEquals(toTest, null);
        Assertions.assertNotEquals(toTest, new Object());
        Assertions.assertEquals(toTest, toTest);
        final Weight another = MetricWeight.inKilograms(value, observedAt);
        Assertions.assertEquals(toTest, another);
        Assertions.assertEquals(another, toTest);
        final Weight aThird = MetricWeight.inKilograms(value, observedAt);
        Assertions.assertEquals(another, aThird);
        Assertions.assertEquals(toTest, aThird);
    }
}
