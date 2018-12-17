package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Family;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;

class FamilyStorageIntegrationTest {
    @Test
    void canStoreFamilyForPerson() {
        // Given: A person whose Vitals are stored in Vitalizr
        final Person mom = Fixtures.person();
        // And: Another person who is part of that person's Family
        final Family nick = Fixtures.family(mom);
        // When: I call store
        Vitalizr.addFamilyMember(nick);
        // Then: the other person is stored as part of the original person's family
        final Collection<Family> members = Vitalizr.getFamilyFor(mom);
        Assertions.assertTrue(members.contains(nick));
    }
}
