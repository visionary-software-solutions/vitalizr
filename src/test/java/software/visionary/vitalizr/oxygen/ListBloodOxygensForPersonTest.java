package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyWater.BioelectricalImpedance;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;
import software.visionary.vitalizr.bodyWater.ListBodyWaterPercentagesForPerson;
import software.visionary.vitalizr.pulse.HeartrateMonitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;

final class ListBloodOxygensForPersonTest {
    @Test
    void canRetrieveVital() {
        final Integer spO2 = 94;
        final Person p = Fixtures.createRandomPerson();
        final BloodOxygen saved = new PeripheralOxygenSaturation(Instant.now(), spO2, p);
        Vitalizr.storeBloodOxygenFor(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBloodOxygensForPerson action = new ListBloodOxygensForPerson();
        final Collection<BloodOxygen> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(spO2)));
    }
}
