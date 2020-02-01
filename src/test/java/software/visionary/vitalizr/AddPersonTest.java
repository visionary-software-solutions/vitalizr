package software.visionary.vitalizr;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddPersonTest {
    @Test
    void canAddAPerson() throws IOException {
        final Person toAdd = Fixtures.createRandomPerson();
        final AddPerson toTest = new AddPerson();
        final String input = String.format("%s\u0004", toAdd);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        toTest.tryDo(scanner);
        final Collection<Lifeform> stored = Vitalizr.listPeople();
        assertTrue(stored.contains(toAdd));
    }
}
