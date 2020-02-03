package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Scanner;

final class AddBloodOxygenTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Integer spO2 = 93;
        final String input = String.format("%s&%d&\u0004", p, spO2);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodOxygen action = new AddBloodOxygen();
        final BloodOxygen result = action.deserialize(scanner);
        Assertions.assertEquals(spO2, result.getQuantity());
        action.saveVital(result);
        final Collection<BloodOxygen> stored = Vitalizr.getBloodOxygensFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }

    @Test
    void canSaveVitalWithJustID() {
        final Person p = Fixtures.createRandomPerson();
        Vitalizr.storeBloodOxygen(new PeripheralOxygenSaturation(Instant.now().minus(1, ChronoUnit.HOURS), 97, p));
        final Integer spO2 = 93;
        final String input = String.format("%s&%d&\u0004", p.getID(), spO2);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodOxygen action = new AddBloodOxygen();
        final BloodOxygen result = action.deserialize(scanner);
        Assertions.assertEquals(spO2, result.getQuantity());
        action.saveVital(result);
        final Collection<BloodOxygen> stored = Vitalizr.getBloodOxygensFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
