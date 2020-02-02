package software.visionary.vitalizr.bloodSugar;

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

final class AddBloodSugarTest {
    @Test
    void canSaveVital() {
        final Person p = Fixtures.createRandomPerson();
        final Integer glucose = 140;
        final String input = String.format("%s&%d&\u0004", p, glucose);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final AddBloodSugar action = new AddBloodSugar();
        final BloodSugar result = action.deserialize(scanner);
        Assertions.assertEquals(glucose, result.getQuantity());
        action.saveVital(result);
        final Collection<BloodSugar> stored = Vitalizr.getBloodSugarsFor(p);
        Assertions.assertFalse(stored.isEmpty());
        Assertions.assertTrue(stored.contains(result));
    }
}
