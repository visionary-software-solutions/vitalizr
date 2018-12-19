package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Caregiver;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

class CaregiverStorageIntegrationTest {
    @Test
    void canStoreCaregiverForPerson() {
        // Given: A person whose Vitals are stored in Vitalizr
        final Person mom = Fixtures.person();
        // And: Another person who is a caregiver for that person
        final Caregiver giver = Fixtures.caregiver(mom);
        // When: I call store
        Vitalizr.addCaregiver(giver);
        // Then: the other person is stored as part of the original person's caregivers
        final Collection<Caregiver> careTeam = Vitalizr.getCaregiversFor(mom);
        Assertions.assertTrue(careTeam.contains(giver));
    }
}
