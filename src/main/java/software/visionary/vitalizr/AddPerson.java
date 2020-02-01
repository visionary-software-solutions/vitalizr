package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Person;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public final class AddPerson extends Wish {
    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        try {
            tryDo(scanner);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tryDo(final Scanner scanner) throws IOException {
        final String input = scanner.useDelimiter("\u0004").next();
        final Person person = Human.createPerson(input);
        Vitalizr.addPerson(person);
    }
}
