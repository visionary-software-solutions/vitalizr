package software.visionary.vitalizr.bodyMassIndex;

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

final class ListBodyMassIndicesForPersonTest {
    @Test
    void canRetrieveVital() {
        final Double bmi = 33.1;
        final Person p = Fixtures.createRandomPerson();
        final BodyMassIndex saved = new QueteletIndex(Instant.now(), bmi, p);
        Vitalizr.storeBodyMassIndexFor(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBodyMassIndicesForPerson action = new ListBodyMassIndicesForPerson();
        final Collection<BodyMassIndex> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(bmi)));
    }
}
