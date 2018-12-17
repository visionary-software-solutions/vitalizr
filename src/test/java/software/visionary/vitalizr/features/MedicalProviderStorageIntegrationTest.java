package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.MedicalProvider;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

class MedicalProviderStorageIntegrationTest {
    @Test
    void canStoreMedicalProviderForPerson() {
        // Given: A person whose Vitals are stored in Vitalizr
        final Person mom = Fixtures.person();
        // And: Another person who is a medical provider for that person
        final MedicalProvider doctor = Fixtures.doctor(mom);
        // When: I call store
        Vitalizr.addMedicalProvider(doctor);
        // Then: the other person is stored as part of the original person's medical providers
        final Collection<MedicalProvider> careTeam = Vitalizr.getMedicalProvidersFor(mom);
        Assertions.assertTrue(careTeam.contains(doctor));
    }
}
