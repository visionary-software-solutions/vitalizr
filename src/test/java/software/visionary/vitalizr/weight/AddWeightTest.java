package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

final class AddWeightTest {
    @Test
    void canSaveVitalInKilograms() {
        final Person p = Fixtures.createRandomPerson();
        final Double kilograms = 101.7;
        final String input = String.format("%s&%f&%s\u0004", p, kilograms, Kilogram.INSTANCE.getSymbol());
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddWeight action = new AddWeight();
        final Weight result = action.deserialize(scanner);
        Assertions.assertEquals(kilograms, result.getQuantity());
        Assertions.assertEquals(Kilogram.INSTANCE, result.getUnit());
        action.saveVital(result);
        final Collection<Weight> stored = Vitalizr.getWeightsFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }

    @Test
    void canSaveVitalInPounds() {
        final Person p = Fixtures.createRandomPerson();
        final Double pounds = 234.7;
        final String input = String.format("%s&%f&%s\u0004", p, pounds, Pound.INSTANCE.getSymbol());
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddWeight action = new AddWeight();
        final Weight result = action.deserialize(scanner);
        Assertions.assertEquals(pounds, result.getQuantity());
        Assertions.assertEquals(Pound.INSTANCE, result.getUnit());
        action.saveVital(result);
        final Collection<Weight> stored = Vitalizr.getWeightsFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
