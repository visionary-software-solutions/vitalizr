package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
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
        Vitalizr.loadAll();
        final T store = deserialize(scanner);
        saveVital(store);
        final File saveFile = saveVitalsFor(store.belongsTo());
        writer.write(String.format(" Vital %s stored in %s%n", store, saveFile.getAbsolutePath()));
    }

    protected abstract T deserialize(final Scanner scanner);
    protected abstract void saveVital(final T vital);

    private static File saveVitalsFor(final Lifeform lifeform) {
        final Path toSave = Vitalizr.getHomeDirectory();
        final File saveFile = Paths.get(toSave.toAbsolutePath().toString(), lifeform.getID().toString(), Long.toString(Instant.now().toEpochMilli())).toFile();
        if (!saveFile.exists()) {
            try {
                saveFile.getParentFile().mkdirs();
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Vitalizr.saveVitalsToFile(saveFile);
        return saveFile;
    }

    protected Person lookupExistingOrCreateNew(final String token) {
        return token.contains(":") ? Human.createPerson(token) : (Person) Vitalizr.getPersonById(UUID.fromString(token)).orElseThrow(RuntimeException::new);
    }
}
