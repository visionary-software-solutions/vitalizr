package software.visionary.serialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Just appends the entry to the file as plain text.
 * @param <T> the type of object to write
 */
public final class WriteObjectAsString<T> extends WriteObjectToFile<T> {
    /**
     * constructs the task.
     *
     * @param entry  should not be null
     * @param toFile should not be null
     */
    public WriteObjectAsString(final T entry, final Path toFile) {
        super(entry, toFile);
    }

    /**
     * writes out the entry to the file as UTF-8 bytes.
     * @param toFile the file to write to
     * @param entry the entry to write
     * @throws IOException if something goes wrong
     */
    protected void writeToFile(final Path toFile, final T entry) throws IOException {
        Files.write(toFile, entry.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }
}
