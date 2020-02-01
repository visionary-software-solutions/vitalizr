package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Lifeform;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public final class ListPeople extends Wish {
    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        try {
            tryDo(writer);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tryDo(final BufferedWriter writer) throws IOException {
        Vitalizr.loadAll();
        final Collection<Lifeform> lifeforms = Vitalizr.listPeople();
        final String s = (lifeforms.isEmpty()) ? "No people stored" : lifeforms.toString();
        writer.write(s);
        writer.newLine();
    }
}
