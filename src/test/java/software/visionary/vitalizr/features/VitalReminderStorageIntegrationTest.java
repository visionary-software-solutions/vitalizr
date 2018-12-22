package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.notifications.VitalReminder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

class VitalReminderStorageIntegrationTest {
    @Test
    void canCreateReminderToCheckVitalForPerson() {
        // Given: A person whose Vitals are stored in Vitalizr
        final Person mom = Fixtures.person();
        // And: A time to remind her to check her blood sugars
        final Instant tommorrow = Instant.now().plus(1, ChronoUnit.DAYS);
        // When: I create a reminder
        Vitalizr.addReminderForVital(mom, BloodSugar.class, tommorrow);
        // Then: the reminder is stored
        final Collection<VitalReminder> reminders = Vitalizr.getRemindersFor(mom);
        Assertions.assertTrue(reminders.contains(new VitalReminder(tommorrow, mom, BloodSugar.class)));
    }
}
