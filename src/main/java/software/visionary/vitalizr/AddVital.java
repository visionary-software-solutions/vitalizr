package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Scanner;

public abstract class AddVital extends Wish {
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
        final Vital store = saveVital(scanner);
        final File saveFile = saveVitalsFor(store.belongsTo());
        writer.write(String.format(" Vital %s stored in %s%n", store, saveFile.getAbsolutePath()));
    }

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

    protected abstract Vital saveVital(final Scanner tokens);
}
