package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBloodOxygensTest {
    @Test
    void canRetrieveVital() {
        final Integer spO2 = 94;
        final Person p = Fixtures.createRandomPerson();
        final BloodOxygen saved = new PeripheralOxygenSaturation(Instant.now(), spO2, p);
        Vitalizr.storeBloodOxygen(saved);
        final ListBloodOxygens action = new ListBloodOxygens();
        final Collection<BloodOxygen> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
