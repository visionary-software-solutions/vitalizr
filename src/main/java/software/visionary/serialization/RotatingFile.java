package software.visionary.serialization;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Path based abstraction that handles periodic update and submission for writing.
 * An example of the periodic update would be file rotation, such as changing the file to write to every hour.
 */
public final class RotatingFile {
    /**
     * A log.
     */
    private static final Logger LOG = Logger.getLogger(RotatingFile.class.getName());
    /**
     * The executor that rotates files.
     */
    private static final ScheduledExecutorService ROTATOR = Executors.newSingleThreadScheduledExecutor();
    /**
     * The executor that writes data out to a file.
     */
    private static final ExecutorService WRITER = Executors.newSingleThreadExecutor();
    /**
     * The Callable that will calculate the file name.
     */
    private final Callable<Path> updater;
    /**
     * where to write.
     */
    private final AtomicReference<Path> toFile;

    /**
     * Default constructor, will configure rotation for 1 hour.
     * @param callable to get the path from
     */
    public RotatingFile(final Callable<Path> callable) {
        this(callable, 1, TimeUnit.HOURS);
    }

    /**
     * configurable constructor.
     * @param callable to get the path from
     * @param period how long
     * @param unit the unit
     */
    public RotatingFile(final Callable<Path> callable, final long period, final TimeUnit unit) {
        updater = Objects.requireNonNull(callable);
        try {
            toFile = new AtomicReference<>(updater.call());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        ROTATOR.scheduleAtFixedRate(this::rotate, 0, period, unit);
    }

    /**
     * rotates the file by calling the update strategy and setting the stored path.
     */
    private void rotate() {
        try {
            LOG.log(Level.INFO, "Old log was " + toFile.get().toString());
            final Path newLog = updater.call();
            LOG.log(Level.INFO, "New log is " + newLog.toString());
            toFile.set(newLog);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error rotating service log", e);
        }
    }

    /**
     * submits a runnable to the writer for execution and will block until it is done.
     * @param r the runnable to execute
     */
    public void submitForWriting(final Runnable r) {
        LOG.log(Level.INFO, "submitting writing task to executor");
        try {
            WRITER.submit(r).get();
        } catch (final Exception e) {
            LOG.log(Level.SEVERE, "error writing", e);
        }
    }

    /**
     * @return the path to be written to.
     */
    public Path getPath() {
        return toFile.get();
    }
}
