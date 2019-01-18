package software.visionary.vitalizr.serialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.BDDMockito.*;

class WriteObjectAsGZipTest {
    @Mock
    private Path toFile;
    private Object entry;
    private WriteObjectAsGZip<Object> toTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        entry = new Object();
        toTest = new WriteObjectAsGZip<>(entry, toFile);
    }

    @Test
    void rejectsNullEntry() {
        assertThrows(NullPointerException.class, () -> new WriteObjectAsGZip<>(null, toFile));
    }

    @Test
    void rejectsNullPath() {
        assertThrows(NullPointerException.class, () -> new WriteObjectAsGZip<>(entry, null));
    }

    @Test
    void canWriteAnEntryToFile() throws IOException {
        final File f = Files.createTempFile(".", "gzipEntry").toFile();
        given(toFile.toFile()).willReturn(f);
        toTest.writeToFile(toFile, entry);

        final String written = GZipFiles.slurpGZippedFileAsString(toFile, StandardCharsets.UTF_8);
        assertFalse(written.isEmpty());
        Files.deleteIfExists(f.toPath());
    }
}
