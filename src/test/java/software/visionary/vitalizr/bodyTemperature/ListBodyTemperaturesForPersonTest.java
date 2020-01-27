package software.visionary.vitalizr.bodyTemperature;

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

final class ListBodyTemperaturesForPersonTest {
    @Test
    void canRetrieveVital() {
        final Double temp = 97.9;
        final Person p = Fixtures.createRandomPerson();
        final BodyTemperature saved = new ImperialTemperature(Instant.now(), temp, p);
        Vitalizr.storeTemperature(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBodyTemperaturesForPerson action = new ListBodyTemperaturesForPerson();
        final Collection<BodyTemperature> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(temp)));
    }
}
