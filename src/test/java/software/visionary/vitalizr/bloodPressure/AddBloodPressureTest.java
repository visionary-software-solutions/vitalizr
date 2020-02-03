package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.numbers.Fraction;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;

final class AddBloodPressureTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Integer systolic = 140;
        final Integer diastolic = 70;
        final String input = String.format("%s&%d&%d\u0004", p, systolic, diastolic);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodPressure action = new AddBloodPressure();
        final Combined pressure = action.deserialize(scanner);
        Assertions.assertTrue(pressure.getQuantity() instanceof Fraction);
        final Fraction num = (Fraction) pressure.getQuantity();
        Assertions.assertEquals(systolic.intValue(), num.getNumerator().intValue());
        Assertions.assertEquals(diastolic.intValue(), num.getDenominator().intValue());
        action.saveVital(pressure);
        final Collection<BloodPressure> stored = Vitalizr.getBloodPressuresFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(pressure));
    }

    @Test
    void canSaveVitalByID() {
        final Person p = Fixtures.createRandomPerson();
        Vitalizr.storeBloodPressure(Combined.systolicAndDiastolicBloodPressure(Instant.now(), 137, 62, p));
        final Integer systolic = 140;
        final Integer diastolic = 70;
        final String input = String.format("%s&%d&%d\u0004", p.getID(), systolic, diastolic);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodPressure action = new AddBloodPressure();
        final Combined pressure = action.deserialize(scanner);
        Assertions.assertTrue(pressure.getQuantity() instanceof Fraction);
        final Fraction num = (Fraction) pressure.getQuantity();
        Assertions.assertEquals(systolic.intValue(), num.getNumerator().intValue());
        Assertions.assertEquals(diastolic.intValue(), num.getDenominator().intValue());
        action.saveVital(pressure);
        final Collection<BloodPressure> stored = Vitalizr.getBloodPressuresFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(pressure));
    }
}
