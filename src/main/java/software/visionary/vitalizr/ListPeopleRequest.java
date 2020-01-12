package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

@Meeseeks(port = 13337)
public final class ListPeopleRequest extends Executable {
    public ListPeopleRequest(final InputStream received, final OutputStream sent) {
        super(received, sent);
    }

    @Override
    protected void execute(final InputStream received, final OutputStream sent) {
        try {
            Vitalizr.loadAll();
        } catch (IOException e) {
            writeToOutput(e.getLocalizedMessage());
            return;
        }
        final Collection<Lifeform> lifeforms = Vitalizr.listPeople();
        final String s = (lifeforms.isEmpty()) ? "No people stored" : lifeforms.toString();
        writeToOutput(s);
        try {
            sent.close();
        } catch (IOException e) {
            writeToOutput(e.getLocalizedMessage());
        }
    }
}
