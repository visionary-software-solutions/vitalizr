package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Fraction;
import software.visionary.vitalizr.NaturalNumber;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

final class AddBloodPressureToPersonTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Integer systolic = 140;
        final Integer diastolic = 70;
        final String input = String.format("%s&%d&%d\u0004", p, systolic, diastolic);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodPressureToPerson action = new AddBloodPressureToPerson();
        action.saveVital(scanner);
        final Collection<BloodPressure> stored = Vitalizr.getBloodPressuresFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(new Fraction(new NaturalNumber(systolic), new NaturalNumber(diastolic)))));
    }
}
