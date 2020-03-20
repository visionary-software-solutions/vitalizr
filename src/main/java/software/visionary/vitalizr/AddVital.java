package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public abstract class AddVital<T extends Vital> extends Wish {
    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        try {
            tryDo(scanner, writer);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryDo(final Scanner scanner, final BufferedWriter writer) throws IOException {
        final T store = deserialize(scanner);
        saveVital(store);
        writer.write(String.format("Vital %s stored %n", store));
    }

    protected abstract T deserialize(final Scanner scanner);
    protected abstract void saveVital(final T vital);

    protected Person lookupExistingOrCreateNew(final String token) {
        return token.contains(":") ? Human.createPerson(token) : (Person) Vitalizr.getPersonById(UUID.fromString(token)).orElseThrow(RuntimeException::new);
    }
}
