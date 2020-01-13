package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.MetricWeight;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class ListWeightsForPerson implements BiConsumer<InputStream, OutputStream> {
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try {
            Vitalizr.loadAll();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
            closeConnection(sent);
            return;
        }
        final String[] tokens = getInput(received);
        System.out.println("tokens are " + tokens);
        final Person person = Human.createPerson(tokens[0]);
        final MetricWeight store = MetricWeight.inKilograms(Integer.valueOf(tokens[1]), Instant.now(), person);
        Vitalizr.storeWeightFor(store);
        final Path toSave = Vitalizr.getHomeDirectory();
        final File saveFile = new File(toSave.toAbsolutePath().toString() + File.separator + "vitals" + Instant.now().toEpochMilli());
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (final IOException e) {
                writeToOutput(e.getMessage(), sent);
            }
        }
        Vitalizr.saveVitalsToFile(saveFile);
        writeToOutput(String.format(" Weight %s stored in %s", store, saveFile.getAbsolutePath()), sent);
        closeConnection(sent);
    }

    private void writeToOutput(final String message, final OutputStream sent) {
        try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent))){
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeConnection(final OutputStream sent) {
        try {
            sent.close();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
        }
    }

    private static String[] getInput(final InputStream received) {
        final String delimiter = "&";
        String text;
        try (Scanner scanner = new Scanner(received, StandardCharsets.UTF_8.name())) {
            text = scanner.next();
        }
        System.out.println("text is " + text);
        return text.split(delimiter);
    }
}
