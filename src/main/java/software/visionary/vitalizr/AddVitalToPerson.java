package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Scanner;

public abstract class AddVitalToPerson extends Wish {
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
        final Path toSave = Vitalizr.getHomeDirectory();
        final File saveFile = new File(toSave.toAbsolutePath().toString() + File.separator + "vitals" + Instant.now().toEpochMilli());
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        Vitalizr.saveVitalsToFile(saveFile);
        writer.write(String.format(" Weight %s stored in %s%n", store, saveFile.getAbsolutePath()));
    }

    protected abstract Vital saveVital(final Scanner tokens);
}
