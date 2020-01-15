package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.*;
import java.util.Collection;
import java.util.function.BiConsumer;

public final class ListPeople implements BiConsumer<InputStream, OutputStream> {
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent))) {
            Vitalizr.loadAll();
            final Collection<Lifeform> lifeforms = Vitalizr.listPeople();
            final String s = (lifeforms.isEmpty()) ? "No people stored" : lifeforms.toString();
            writer.write(s);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
