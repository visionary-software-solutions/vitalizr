package software.visionary.vitalizr.pulse;

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

final class ListPulsesTest {
    @Test
    void canRetrieveVital() {
        final Integer pulse = 58;
        final Person p = Fixtures.createRandomPerson();
        final Pulse saved = new HeartrateMonitor(Instant.now(), pulse, p);
        Vitalizr.storePulse(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListPulses action = new ListPulses();
        final Collection<Pulse> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(pulse)));
    }
}
