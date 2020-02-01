package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.numbers.Fraction;
import software.visionary.numbers.NaturalNumber;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;

final class ListBloodPressuresTest {
    @Test
    void canRetrieveVital() {
        final Integer systolic = 140;
        final Integer diastolic = 70;
        final Person p = Fixtures.createRandomPerson();
        final BloodPressure saved = Combined.systolicAndDiastolicBloodPressure(Instant.now(), systolic, diastolic, p);
        Vitalizr.storeBloodPressure(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBloodPressures action = new ListBloodPressures();
        final Collection<BloodPressure> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(new Fraction(new NaturalNumber(systolic), new NaturalNumber(diastolic)))));
    }
}
