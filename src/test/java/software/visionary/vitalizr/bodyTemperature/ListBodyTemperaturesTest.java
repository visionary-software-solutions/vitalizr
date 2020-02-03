package software.visionary.vitalizr.bodyTemperature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBodyTemperaturesTest {
    @Test
    void canRetrieveVital() {
        final Double temp = 97.9;
        final Person p = Fixtures.createRandomPerson();
        final BodyTemperature saved = new ImperialTemperature(Instant.now(), temp, p);
        Vitalizr.storeTemperature(saved);
        final ListBodyTemperatures action = new ListBodyTemperatures();
        final Collection<BodyTemperature> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
