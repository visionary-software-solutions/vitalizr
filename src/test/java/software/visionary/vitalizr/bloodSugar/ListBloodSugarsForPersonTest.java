package software.visionary.vitalizr.bloodSugar;

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

final class ListBloodSugarsForPersonTest {
    @Test
    void canRetrieveVital() {
        final Integer glucose = 140;
        final Person p = Fixtures.createRandomPerson();
        final BloodSugar saved = new WholeBloodGlucose(Instant.now(), glucose, p);
        Vitalizr.storeBloodSugar(saved);
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final ListBloodSugarsForPerson action = new ListBloodSugarsForPerson();
        final Collection<BloodSugar> stored = action.getVitals(scanner);
        Assertions.assertFalse(stored.isEmpty());
        // TODO: Shouldn't WholeBloodGlucose use a NaturalNumber?
        Assertions.assertTrue(stored.parallelStream()
                .anyMatch(bp -> bp.getQuantity().equals(glucose)));
    }
}
