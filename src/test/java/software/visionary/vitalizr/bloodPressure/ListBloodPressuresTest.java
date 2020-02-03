package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Collection;

final class ListBloodPressuresTest {
    @Test
    void canRetrieveVital() {
        final Integer systolic = 140;
        final Integer diastolic = 70;
        final Person p = Fixtures.createRandomPerson();
        final BloodPressure saved = Combined.systolicAndDiastolicBloodPressure(Instant.now(), systolic, diastolic, p);
        Vitalizr.storeBloodPressure(saved);
        final ListBloodPressures action = new ListBloodPressures();
        final Collection<BloodPressure> stored = action.forId(p.getID());
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(saved));
    }
}
