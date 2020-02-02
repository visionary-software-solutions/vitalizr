package software.visionary.vitalizr.pulse;

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

final class AddPulseTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Integer pulse = 57;
        final String input = String.format("%s&%d&\u0004", p, pulse);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddPulse action = new AddPulse();
        final Pulse result = action.deserialize(scanner);
        Assertions.assertEquals(pulse, result.getQuantity());
        action.saveVital(result);
        final Collection<Pulse> stored = Vitalizr.getPulsesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
