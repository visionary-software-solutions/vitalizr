package software.visionary.vitalizr;

import java.io.InputStream;
import java.io.OutputStream;

public class ListPeopleFactory implements ExecutableFactory<ListPeopleRequest> {
    @Override
    public ListPeopleRequest create(final InputStream received, final OutputStream sent) {
        return new ListPeopleRequest(received, sent);
    }
}
