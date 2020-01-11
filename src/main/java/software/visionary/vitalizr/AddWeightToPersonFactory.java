package software.visionary.vitalizr;

import java.io.InputStream;
import java.io.OutputStream;

public class AddWeightToPersonFactory implements ExecutableFactory<AddWeightToPersonRequest> {
    @Override
    public AddWeightToPersonRequest create(final InputStream received, final OutputStream sent) {
        return new AddWeightToPersonRequest(received, sent);
    }
}
