package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.numbers.Fraction;
import software.visionary.numbers.NaturalNumber;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

class CombinedTest {
    private Lifeform lifeform;
    private Instant observedAt;
    private Number value, value2;
    private Combined toTest;

    @BeforeEach
    void setup() {
        lifeform = Fixtures.person();
        observedAt = Instant.now();
        value = ThreadLocalRandom.current().nextInt(0, 300);
        value2 = ThreadLocalRandom.current().nextInt(0, 300);
        toTest = Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), value2.intValue(), lifeform);
    }

    @Test
    void rejectsNullInstant() {
        Assertions.assertThrows(NullPointerException.class, () -> Combined.systolicAndDiastolicBloodPressure(null, value.intValue(), value2.intValue(), lifeform));
    }

    @Test
    void rejectsNegativeSystolic() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Combined.systolicAndDiastolicBloodPressure(observedAt, -1, value2.intValue(), lifeform));
    }

    @Test
    void rejectsNegativeDiastolic() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), -1, lifeform));
    }

    @Test
    void rejectsBloodPressuresFromDifferentPeople() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final Person someoneElse = Fixtures.createRandomPerson();
            new Combined(new Systolic(observedAt, new NaturalNumber(value.intValue()), lifeform),
                    new Diastolic(observedAt, new NaturalNumber(value2.intValue()), someoneElse));
        });
    }

    @Test
    void rejectsNullOwner() {
        Assertions.assertThrows(NullPointerException.class, () -> Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), value2.intValue(), null));
    }

    @Test
    void canGetInstant() {
        Assertions.assertEquals(observedAt, toTest.observedAt());
    }

    @Test
    void canGetQuantity() {
        Assertions.assertEquals(new Fraction(new NaturalNumber(value.intValue()),new NaturalNumber(value2.intValue())), toTest.getQuantity());
    }

    @Test
    void defaultsToMillimetersOfMercury() {
        Assertions.assertEquals(MillimetersOfMercury.INSTANCE, toTest.getUnit());
    }

    @Test
    void implementsHashCodeCorrectly() {
        Assertions.assertEquals(toTest.hashCode(), toTest.hashCode());
        final Combined another = Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), value2.intValue(), lifeform);
        Assertions.assertEquals(toTest.hashCode(), another.hashCode());
        Assertions.assertEquals(another.hashCode(), toTest.hashCode());
    }

    @Test
    void implementsEqualsCorrectly() {
        Assertions.assertNotEquals(toTest, null);
        Assertions.assertNotEquals(toTest, new Object());
        Assertions.assertEquals(toTest, toTest);
        final Combined another = Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), value2.intValue(), lifeform);
        Assertions.assertEquals(toTest, another);
        Assertions.assertEquals(another, toTest);
        final Combined aThird = Combined.systolicAndDiastolicBloodPressure(observedAt, value.intValue(), value2.intValue(), lifeform);
        Assertions.assertEquals(another, aThird);
        Assertions.assertEquals(toTest, aThird);
    }
}
