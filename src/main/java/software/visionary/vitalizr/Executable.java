package software.visionary.vitalizr;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

public abstract class Executable implements Runnable {
    private final InputStream received;
    private final OutputStream sent;

    public Executable(final InputStream received, final OutputStream sent) {
        this.received = Objects.requireNonNull(received);
        this.sent = Objects.requireNonNull(sent);
    }

    protected final void writeToOutput(final String s) {
        try (final PrintStream writer = new PrintStream(sent)){
            writer.println(s);
        }
    }

    @Override
    public final void run() {
        execute(received, sent);
    }

    protected abstract void execute(final InputStream received, final OutputStream sent);
}
