package software.visionary.serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class GZipFilesTest {
    @Test
    void canWriteAnEntryToFileAndReadItBack() throws IOException {
        final File f = Files.createTempFile(".", "entry").toFile();
        final String toWrite = "foo";
        GZipFiles.writeOutGZippedFile(f.toPath(), toWrite, StandardCharsets.UTF_8);
        final String written = GZipFiles.slurpGZippedFileAsString(f.toPath(), StandardCharsets.UTF_8);
        assertEquals(toWrite, written);
        Files.deleteIfExists(f.toPath());
    }

    @Test
    void doesNothingIfToldToWriteToNonExistantFile() throws IOException {
        GZipFiles.writeOutGZippedFile(Paths.get("fake"), "not written", StandardCharsets.UTF_8);
    }

    @Test
    void returnsEmptyListIfToldToReadFromNonExistantFile() throws IOException {
        final List<String> read = GZipFiles.slurpGZippedFile(Paths.get("fake"), StandardCharsets.UTF_8);
        assertTrue(read.isEmpty());
    }
}
