package software.visionary.vitalizr;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Wish implements BiConsumer<InputStream, OutputStream> {
    /**
     * Log please.
     */
    private static final Logger LOG = Logger.getLogger(Wish.class.getName());
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try (final Scanner scanner = new Scanner(received, StandardCharsets.UTF_8.name());
             final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent)))  {
            doCommand(scanner, writer);
        } catch (final Exception e) {
            LOG.log(Level.SEVERE, "Exception executing " + getClass().getName(), e);
        }
    }

    protected abstract void doCommand(final Scanner scanner, final BufferedWriter writer);
}
