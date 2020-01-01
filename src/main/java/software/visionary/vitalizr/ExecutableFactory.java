package software.visionary.vitalizr;

import java.io.InputStream;
import java.io.OutputStream;

public interface ExecutableFactory<T extends Executable> {
    T create(InputStream received, OutputStream sent);
}
