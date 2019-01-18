package software.visionary.vitalizr.serialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Appends the entry to the file as GZipped.
 * @param <T> the type of object to write
 */
public final class WriteObjectAsGZip<T> extends WriteObjectToFile<T> {
    /**
     * constructs the task.
     *
     * @param entry  should not be null
     * @param toFile should not be null
     */
    public WriteObjectAsGZip(final T entry, final Path toFile) {
        super(entry, toFile);
    }

    @Override
    protected void writeToFile(final Path toFile, final T entry) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append(GZipFiles.slurpGZippedFileAsString(toFile, StandardCharsets.UTF_8));
        sb.append(entry.toString());
        GZipFiles.writeOutGZippedFile(toFile, sb.toString(), StandardCharsets.UTF_8);
    }
}
