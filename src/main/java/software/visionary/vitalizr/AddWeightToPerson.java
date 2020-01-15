package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.MetricWeight;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class AddWeightToPerson implements BiConsumer<InputStream, OutputStream> {
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try (final Scanner scanner = new Scanner(received, StandardCharsets.UTF_8.name());
             final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent))){
            Vitalizr.loadAll();
            final String[] tokens = scanner.next().split("&");
            final Person person = Human.createPerson(tokens[0]);
            final MetricWeight store = MetricWeight.inKilograms(Integer.valueOf(tokens[1]), Instant.now(), person);
            Vitalizr.storeWeightFor(store);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
