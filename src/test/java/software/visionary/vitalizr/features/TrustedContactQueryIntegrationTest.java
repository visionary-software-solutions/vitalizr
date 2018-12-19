package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.TrustedContact;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.notifications.VitalNotification;

import java.time.Instant;
import java.util.Collection;

class TrustedContactQueryIntegrationTest {
    @Test
    void canCreateNotificationsToTrustedContactsForAPerson() {
        // Given: A person to store vitals for
        final Person mom = Fixtures.person();
        // And: A Vital to store
        final Vital first = Fixtures.temperatureAt(97.9, Instant.now(), mom);
        Vitalizr.storeVital(first);
        // And: Another Vital to store
        final Vital second = Fixtures.bloodSugarAt(139, Instant.now(), mom);
        Vitalizr.storeVital(second);
        // And: A TrustedContact for that person
        final TrustedContact son = Fixtures.family(mom);
        Vitalizr.storeTrustedContact(son);
        // And: Another TrustedContact for that person
        final TrustedContact doctor = Fixtures.doctor(mom);
        Vitalizr.storeTrustedContact(doctor);
        // When: We ask for Vital notifications for TrustedContacts
        final Collection<VitalNotification> notifications = Vitalizr.createNotificationsForTrustedContacts(mom);
        // Then: The notifications contain a notification for the first TrustedContact and the first vital
        Assertions.assertTrue(notifications.contains(new VitalNotification(first, son)));
        // And: The notifications contain a notification for the second TrustedContact and the first vital
        Assertions.assertTrue(notifications.contains(new VitalNotification(first, doctor)));
        // Then: The notifications contain a notification for the second TrustedContact and the second vital
        Assertions.assertTrue(notifications.contains(new VitalNotification(second, son)));
        // And: The notifications contain a notification for the second TrustedContact and the second vital
        Assertions.assertTrue(notifications.contains(new VitalNotification(second, doctor)));
    }
}
