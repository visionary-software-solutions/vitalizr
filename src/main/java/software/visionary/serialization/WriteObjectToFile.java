package software.visionary.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A task to write a an object to a {@link Path}.
 *
 * @param <T> the type of object to write
 */
public abstract class WriteObjectToFile<T> implements Runnable {
    /**
     * Log please.
     */
    private static final Logger LOG = Logger.getLogger(WriteObjectToFile.class.getName());
    /**
     * The lock.
     */
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    /**
     * where to write.
     */
    private final Path toFile;
    /**
     * The entry to write.
     */
    private final T entry;

    /**
     * constructs the task.
     *
     * @param entry  should not be null
     * @param toFile should not be null
     */
    WriteObjectToFile(final T entry, final Path toFile) {
        this.toFile = Objects.requireNonNull(toFile);
        this.entry = Objects.requireNonNull(entry);
    }

    @Override
    public final void run() {
        LOG.log(Level.INFO, "running to write object to file");
        LOCK.readLock().lock();
        try {
            if (!Files.exists(toFile)) {
                LOG.log(Level.SEVERE, "The file to write to " + toFile.toString() + " is missing!");
                throw new IllegalStateException("The file to write to " + toFile.toString() + " is missing!");
            }
        } finally {
            LOCK.readLock().unlock();
        }

        LOG.log(Level.INFO, "Verified file exists, beginning to write");

        LOCK.writeLock().lock();
        try {
            writeToFile(toFile, entry);
            LOG.log(Level.INFO,
                    String.format("Wrote %s to file %s%n", entry.toString(), toFile.getFileName().toString()));
        } catch (final IOException e) {
            LOG.log(Level.SEVERE, "Error writing to file!", e);
        } finally {
            LOCK.writeLock().unlock();
        }
        LOG.log(Level.INFO, String.format("Wrote %s to file %s %n", entry, toFile.toString()));
    }

    /**
     * TEMPLATE METHOD to allow for different implementations, like GZIP.
     *
     * @param path the file to write to
     * @param e    the object to write
     * @throws IOException if something goes wrong writing
     */
    protected abstract void writeToFile(final Path path, final T e) throws IOException;
}
