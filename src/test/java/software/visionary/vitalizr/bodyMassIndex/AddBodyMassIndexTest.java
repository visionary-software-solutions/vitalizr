package software.visionary.vitalizr.bodyMassIndex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

final class AddBodyMassIndexTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Double bmi = 33.2;
        final String input = String.format("%s&%f&\u0004", p, bmi);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBodyMassIndex action = new AddBodyMassIndex();
        final BodyMassIndex result = action.deserialize(scanner);
        Assertions.assertEquals(bmi, result.getQuantity());
        action.saveVital(result);
        final Collection<BodyMassIndex> stored = Vitalizr.getBodyMassIndicesFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
