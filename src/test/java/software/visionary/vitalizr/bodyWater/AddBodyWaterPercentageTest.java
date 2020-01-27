package software.visionary.vitalizr.bodyWater;

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

final class AddBodyWaterPercentageTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Double water = 52.1;
        final String input = String.format("%s&%f&\u0004", p, water);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyWaterPercentage action = new AddBodyWaterPercentage();
        action.saveVital(scanner);
        final Collection<BodyWaterPercentage> stored = Vitalizr.getBodyWaterPercentagesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(water)));
    }
}
