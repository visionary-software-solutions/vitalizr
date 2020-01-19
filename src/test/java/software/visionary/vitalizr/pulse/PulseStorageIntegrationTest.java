package software.visionary.vitalizr.pulse;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PulseStorageIntegrationTest {
    @Test
    void canStorePulse() {
        // Given: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // And: A pulse at a particular point in time and quantity to be stored
        final Pulse toStore = Fixtures.pulseAt(91, Instant.now(), mom);
        // When: I call store
        Vitalizr.storePulseFor(toStore);
        // Then: the weight is stored
        final Collection<Pulse> stored = Vitalizr.getPulsesFor(mom);
        assertTrue(stored.contains(toStore));
    }
}
