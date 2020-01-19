package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

class ImperialWeightTest {
    private Lifeform lifeform;
    private Instant observedAt;
    private Number value;
    private Weight toTest;

    @BeforeEach
    void setup() {
        lifeform = Fixtures.person();
        observedAt = Instant.now();
        value = ThreadLocalRandom.current().nextInt(0, 300);
        toTest = new ImperialWeight(observedAt, value, lifeform);
    }

    @Test
    void rejectsNullInstant() {
        Assertions.assertThrows(NullPointerException.class, () -> new ImperialWeight(null, value,  lifeform));
    }

    @Test
    void rejectsNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> new ImperialWeight(observedAt, null, lifeform));
    }

    @Test
    void rejectsNullOwner() {
        Assertions.assertThrows(NullPointerException.class, () -> new ImperialWeight(observedAt, value, null));
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
    void defaultsToPounds() {
        Assertions.assertEquals(Pound.INSTANCE, toTest.getUnit());
    }

    @Test
    void implementsHashCodeCorrectly() {
        Assertions.assertEquals(toTest.hashCode(), toTest.hashCode());
        final Weight another = new ImperialWeight(observedAt, value, lifeform);
        Assertions.assertEquals(toTest.hashCode(), another.hashCode());
        Assertions.assertEquals(another.hashCode(), toTest.hashCode());
    }

    @Test
    void implementsEqualsCorrectly() {
        Assertions.assertNotEquals(toTest, null);
        Assertions.assertNotEquals(toTest, new Object());
        Assertions.assertEquals(toTest, toTest);
        final Weight another = new ImperialWeight(observedAt, value, lifeform);
        Assertions.assertEquals(toTest, another);
        Assertions.assertEquals(another, toTest);
        final Weight aThird = new ImperialWeight(observedAt, value, lifeform);
        Assertions.assertEquals(another, aThird);
        Assertions.assertEquals(toTest, aThird);
    }
}
