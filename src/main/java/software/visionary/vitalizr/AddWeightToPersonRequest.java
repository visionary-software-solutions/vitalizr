package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.MetricWeight;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Scanner;

@Meeseeks(port = 13338)
public class AddWeightToPersonRequest extends Executable {
    public AddWeightToPersonRequest(final InputStream received, final OutputStream sent) {
        super(received, sent);
    }

    @Override
    protected void execute(final InputStream received, final OutputStream sent) {
        try {
            Vitalizr.loadAll();
        } catch (IOException e) {
            writeToOutput(e.getMessage());
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
                writeToOutput(e.getMessage());
            }
        }
        Vitalizr.saveVitalsToFile(saveFile);
        writeToOutput(String.format(" Weight %s stored in %s", store, saveFile.getAbsolutePath()));
        closeConnection(sent);
    }

    private void closeConnection(final OutputStream sent) {
        try {
            sent.close();
        } catch (IOException e) {
            writeToOutput(e.getMessage());
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
