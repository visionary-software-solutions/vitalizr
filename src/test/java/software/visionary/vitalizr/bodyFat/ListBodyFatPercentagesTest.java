package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;

final class ListBodyFatPercentagesTest {
    @Test
    void canRetrieveVital() {
        final Double fatPercentage = 28.2;
        final Person p = Fixtures.createRandomPerson();
        final BodyFatPercentage saved = new BioelectricalImpedance(Instant.now(), fatPercentage, p);
        Vitalizr.storeBodyFatPercentage(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBodyFatPercentages action = new ListBodyFatPercentages();
        final Collection<BodyFatPercentage> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(fatPercentage)));
    }
}
