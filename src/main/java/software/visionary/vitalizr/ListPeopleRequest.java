package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.*;
import java.util.Collection;
import java.util.function.BiConsumer;

@Meeseeks(port = 13337)
public final class ListPeopleRequest implements BiConsumer<InputStream, OutputStream> {
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try {
            Vitalizr.loadAll();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
            return;
        }
        final Collection<Lifeform> lifeforms = Vitalizr.listPeople();
        final String s = (lifeforms.isEmpty()) ? "No people stored" : lifeforms.toString();
        writeToOutput(s, sent);
        try {
            sent.close();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
        }
    }

    private void writeToOutput(final String message, final OutputStream sent) {
        try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent))){
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
