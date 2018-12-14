package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.pulse.PersonPulse;
import software.visionary.vitalizr.pulse.Pulse;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PulseStorageIntegrationTest {
    @Test
    void canStorePulse() {
        // Given: A pulse at a particular point in time and quantity to be stored
        final Pulse toStore = Fixtures.pulse();
        // And: A person to store blood pressure for
        final Person mom = Fixtures.person();
        // When: I call store
        Vitalizr.storePulseFor(mom, toStore);
        // Then: the weight is stored
        final Collection<PersonPulse> stored = Vitalizr.getPulsesFor(mom);
        assertTrue(stored.contains(new PersonPulse(mom, toStore)));
    }
}
