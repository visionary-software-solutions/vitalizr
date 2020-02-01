package software.visionary.serialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

class WriteObjectAsStringTest {
    private File f;
    private Object entry;
    private WriteObjectAsString<Object> toTest;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        f = Files.createTempFile(".", "plaintext").toFile();
        entry = new Object();
        toTest = new WriteObjectAsString<>(entry, f.toPath());
    }

    @Test
    void rejectsNullEntry() {
        assertThrows(NullPointerException.class, () -> new WriteObjectAsString<>(null, f.toPath()));
    }

    @Test
    void rejectsNullPath() {
        assertThrows(NullPointerException.class, () -> new WriteObjectAsString<>(entry, null));
    }

    @Test
    void canWriteAnEntryToFile() throws IOException {
        toTest.writeToFile(f.toPath(), entry);
        final List<String> written = Files.lines(f.toPath()).collect(Collectors.toList());
        assertFalse(written.isEmpty());
        Files.deleteIfExists(f.toPath());
    }
}
