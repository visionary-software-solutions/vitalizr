package software.visionary.vitalizr.bloodSugar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBloodSugarsTest {
    @Test
    void canRetrieveVital() {
        final Integer glucose = 140;
        final Person p = Fixtures.createRandomPerson();
        final BloodSugar saved = new WholeBloodGlucose(Instant.now(), glucose, p);
        Vitalizr.storeBloodSugar(saved);
        final ListBloodSugars action = new ListBloodSugars();
        final Collection<BloodSugar> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        // TODO: Shouldn't WholeBloodGlucose use a NaturalNumber?
        Assertions.assertTrue(stored.contains(saved));
    }
}
