package software.visionary.vitalizr.pulse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListPulsesTest {
    @Test
    void canRetrieveVital() {
        final Integer pulse = 58;
        final Person p = Fixtures.createRandomPerson();
        final Pulse saved = new HeartrateMonitor(Instant.now(), pulse, p);
        Vitalizr.storePulse(saved);
        final ListPulses action = new ListPulses();
        final Collection<Pulse> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
