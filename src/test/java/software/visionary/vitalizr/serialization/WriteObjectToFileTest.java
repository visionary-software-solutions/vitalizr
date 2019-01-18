package software.visionary.vitalizr.serialization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class WriteObjectToFileTest {
    private Path path;
    private Object toWrite;
    private WriteObjectToFile<Object> toTest;

    @BeforeEach
    void setup() throws IOException {
        path = Files.createTempFile(".", "fake");
        toWrite = new Object();
        toTest = new Stub(toWrite, path);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void rejectsNullObject() {
        assertThrows(NullPointerException.class, () -> new Stub(null, path));
    }

    @Test
    void rejectsNullPath() {
        assertThrows(NullPointerException.class, () -> new Stub(toWrite, null));
    }

    @Test
    void throwsExceptionIfToldToWriteToNonexistantFile() {
        toTest = new Stub(toWrite, Paths.get("fake"));
        assertThrows(IllegalStateException.class, () -> toTest.run());
    }

    @Test
    void canWriteToFile() {
        toTest.run();
        assertEquals(toWrite, ((Stub) toTest).stored);
    }

    @Test
    void exceptionThrownDoesNotBubbleOut() {
        ((Stub) toTest).throwException = true;
        toTest.run();
        assertEquals(toWrite, ((Stub) toTest).stored);
    }

    private static class Stub extends WriteObjectToFile<Object> {
        boolean throwException;
        Object stored = null;

        /**
         * constructs the task.
         *
         * @param entry  should not be null
         * @param toFile should not be null
         */
        Stub(final Object entry, final Path toFile) {
            super(entry, toFile);
        }

        @Override
        protected void writeToFile(final Path path, final Object e) throws IOException {
            stored = e;
            if (throwException) {
                throw new IOException();
            }
        }
    }
}
