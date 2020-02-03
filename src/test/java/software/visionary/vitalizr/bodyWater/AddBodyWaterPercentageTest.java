package software.visionary.vitalizr.bodyWater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Scanner;

final class AddBodyWaterPercentageTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Double water = 52.1;
        final String input = String.format("%s&%f&\u0004", p, water);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyWaterPercentage action = new AddBodyWaterPercentage();
        final BodyWaterPercentage result = action.deserialize(scanner);
        Assertions.assertEquals(water, result.getQuantity());
        action.saveVital(result);
        final Collection<BodyWaterPercentage> stored = Vitalizr.getBodyWaterPercentagesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }

    @Test
    void canSaveVitalWithJustID() {
        final Person p = Fixtures.createRandomPerson();
        Vitalizr.storeBodyWaterPercentage(new BioelectricalImpedance(Instant.now().minus(1, ChronoUnit.HOURS), 54.1, p));
        final Double water = 52.1;
        final String input = String.format("%s&%f&\u0004", p.getID(), water);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyWaterPercentage action = new AddBodyWaterPercentage();
        final BodyWaterPercentage result = action.deserialize(scanner);
        Assertions.assertEquals(water, result.getQuantity());
        action.saveVital(result);
        final Collection<BodyWaterPercentage> stored = Vitalizr.getBodyWaterPercentagesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
