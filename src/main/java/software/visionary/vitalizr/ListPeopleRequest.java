package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

public final class ListPeopleRequest extends Executable {
    public ListPeopleRequest(final InputStream received, final OutputStream sent) {
        super(received, sent);
    }

    @Override
    protected void execute(final InputStream received, final OutputStream sent) {
        final Collection<Lifeform> lifeforms = Vitalizr.listPeople();
        final String s = (lifeforms.isEmpty()) ? "No people stored" : lifeforms.toString();
        try (final PrintStream writer = new PrintStream(sent)){
            writer.println(s);
        }
    }
}
