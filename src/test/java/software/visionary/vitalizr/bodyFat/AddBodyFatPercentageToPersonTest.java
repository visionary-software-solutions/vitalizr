package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;

final class AddBodyFatPercentageToPersonTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Double fat = 27.9;
        final String input = String.format("%s&%f&\u0004", p, fat);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyFatPercentage action = new AddBodyFatPercentage();
        final BodyFatPercentage result = action.deserialize(scanner);
        Assertions.assertEquals(fat, result.getQuantity());
        action.saveVital(result);
        final Collection<BodyFatPercentage> stored = Vitalizr.getBodyFatPercentagesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }

    @Test
    void canSaveVitalWithJustID() {
        final Person p = Fixtures.createRandomPerson();
        Vitalizr.storeBodyFatPercentage(new BioelectricalImpedance(Instant.now(), 26.3, p));
        final Double fat = 27.9;
        final String input = String.format("%s&%f&\u0004", p.getID(), fat);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyFatPercentage action = new AddBodyFatPercentage();
        final BodyFatPercentage result = action.deserialize(scanner);
        Assertions.assertEquals(fat, result.getQuantity());
        action.saveVital(result);
        final Collection<BodyFatPercentage> stored = Vitalizr.getBodyFatPercentagesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
