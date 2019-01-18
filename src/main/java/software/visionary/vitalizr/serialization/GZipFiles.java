package software.visionary.vitalizr.serialization;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Utility class to get data from a GZip file.
 */
public final class GZipFiles {
    /**
     * Do not instantiate.
     */
    private GZipFiles() {

    }

    /**
     * reads in a GZipped file.
     *
     * @param toFile  the file to be read
     * @param charset the charset to use
     * @return the lines of the file
     * @throws IOException if something goes wrong
     */
    static String slurpGZippedFileAsString(final Path toFile,
                                           final Charset charset) throws IOException {
        return slurpGZippedFile(toFile, charset)
                .stream()
                .collect(Collectors.joining());
    }

    /**
     * reads in a GZipped file.
     *
     * @param toFile  the file to be read
     * @param charset the charset to use
     * @return the lines of the file
     * @throws IOException if something goes wrong
     */
    public static List<String> slurpGZippedFile(final Path toFile,
                                                final Charset charset) throws IOException {
        if (!toFile.toFile().exists() || toFile.toFile().length() == 0) {
            return Collections.emptyList();

        }
        try (final FileInputStream fis = new FileInputStream(toFile.toFile());
             final GZIPInputStream gis = new GZIPInputStream(fis);
             final BufferedReader br = new BufferedReader(new InputStreamReader(gis, charset.name()))) {
            return br.lines().collect(Collectors.toList());
        }
    }

    /**
     * writes out a string to a file, gzipped.
     *
     * @param toFile  the file to be written
     * @param s       the string to write, as gzipped
     * @param charset the charset to use
     * @throws IOException if something goes wrong
     */
    static void writeOutGZippedFile(final Path toFile, final String s,
                                    final Charset charset) throws IOException {
        if (!toFile.toFile().exists()) {
            return;
        }

        try (final FileOutputStream fos = new FileOutputStream(toFile.toFile());
             final GZIPOutputStream gos = new GZIPOutputStream(fos)) {
            gos.write(s.getBytes(charset));
        }
    }
}
