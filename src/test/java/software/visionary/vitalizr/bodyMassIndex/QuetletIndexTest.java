package software.visionary.vitalizr.bodyMassIndex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.api.Lifeform;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

class QuetletIndexTest {
    private Lifeform lifeform;
    private Instant observedAt;
    private Number value;
    private QueteletIndex toTest;

    @BeforeEach
    void setup() {
        lifeform = Fixtures.person();
        observedAt = Instant.now();
        value = ThreadLocalRandom.current().nextInt(0, 300);
        toTest = new QueteletIndex(observedAt, value, lifeform);
    }

    @Test
    void rejectsNullInstant() {
        Assertions.assertThrows(NullPointerException.class, () -> new QueteletIndex(null, value,  lifeform));
    }

    @Test
    void rejectsNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> new QueteletIndex(observedAt, null, lifeform));
    }

    @Test
    void rejectsNullOwner() {
        Assertions.assertThrows(NullPointerException.class, () -> new QueteletIndex(observedAt, value, null));
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
    void defaultsToMillimolar() {
        Assertions.assertEquals(KilogramsPerMetersSquared.INSTANCE, toTest.getUnit());
    }

    @Test
    void implementsHashCodeCorrectly() {
        Assertions.assertEquals(toTest.hashCode(), toTest.hashCode());
        final QueteletIndex another = new QueteletIndex(observedAt, value, lifeform);
        Assertions.assertEquals(toTest.hashCode(), another.hashCode());
        Assertions.assertEquals(another.hashCode(), toTest.hashCode());
    }

    @Test
    void implementsEqualsCorrectly() {
        Assertions.assertNotEquals(toTest, null);
        Assertions.assertNotEquals(toTest, new Object());
        Assertions.assertEquals(toTest, toTest);
        final QueteletIndex another = new QueteletIndex(observedAt, value, lifeform);
        Assertions.assertEquals(toTest, another);
        Assertions.assertEquals(another, toTest);
        final QueteletIndex aThird = new QueteletIndex(observedAt, value, lifeform);
        Assertions.assertEquals(another, aThird);
        Assertions.assertEquals(toTest, aThird);
    }
}
