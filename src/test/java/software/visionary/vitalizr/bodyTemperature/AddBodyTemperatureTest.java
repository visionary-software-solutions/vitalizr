package software.visionary.vitalizr.bodyTemperature;

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

final class AddBodyTemperatureTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Double temp = 97.9;
        final String input = String.format("%s&%f&\u0004", p, temp);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyTemperature action = new AddBodyTemperature();
        action.saveVital(scanner);
        final Collection<BodyTemperature> stored = Vitalizr.getBodyTemperaturesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(temp)));
    }
}
