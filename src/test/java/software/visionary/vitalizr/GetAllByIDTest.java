package software.visionary.vitalizr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.api.Vital;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.UUID;

final class GetAllByIDTest {
    private static final class SimpleChild extends GetAllByID  {
        static UUID stored;
        static Collection<? extends Vital> collection = Collections.emptyList();

        @Override
        protected Collection<? extends Vital> forId(final UUID id) {
            stored = id;
            return collection;
        }
    }

    @Test
    void correctlyParses() {
        final UUID p = UUID.randomUUID();
        final String input = String.format("%s\u0004", p);
        final InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Scanner scanner = new Scanner(stream);
        final GetAllByID toTest = new SimpleChild();
        Collection<? extends Vital> result = toTest.getVitals(scanner);
        Assertions.assertEquals(SimpleChild.collection, result);
        Assertions.assertEquals(p, SimpleChild.stored);
    }
}
