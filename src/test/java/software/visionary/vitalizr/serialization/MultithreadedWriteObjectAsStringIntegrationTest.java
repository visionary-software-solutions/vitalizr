package software.visionary.vitalizr.serialization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MultithreadedWriteObjectAsStringIntegrationTest {
    private Path tempFile;
    private List<Puppy> toWrite;
    private ExecutorService pool;

    @BeforeEach
    void setup() throws IOException {
        final List<Puppy> simple = new ArrayList<>(1001);
        pool = Executors.newCachedThreadPool();
        tempFile = Files.createTempFile(".", "puppies");
        IntStream.rangeClosed(0, 1001)
                .parallel()
                .forEach(i -> simple.add(new Puppy(UUID.randomUUID().toString(), "dalmation")));
        toWrite = new CopyOnWriteArrayList<>(simple);
    }

    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void handlesMultiThreadWritingGracefully() throws InterruptedException, IOException {
        toWrite.parallelStream().forEach(p -> pool.submit(new WriteObjectAsString<Puppy>(p, tempFile)));
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        final List<String> pups = Files.readAllLines(tempFile);
        final List<String> expected = toWrite.stream()
                .map(puppy -> puppy.toString().trim())
                .collect(Collectors.toList());
        // NOTE: submitting to a pool asynchronously like this means that there are no ordering guarantees.
        // The tasks do not do any kind of buffering or make attempts to enforce how they are executed, so
        // writing out a collection in parallel means you may not get it emitted or retrieved in the same order
        pups.forEach(s -> assertTrue(expected.contains(s)));
    }

    private static class Puppy {
        private final String name;
        private final String breed;

        private Puppy(final String name, final String breed) {
            this.name = name;
            this.breed = breed;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, breed);
        }

        @Override
        public boolean equals(final Object that) {
            return this == that
                    || !(that instanceof Puppy)
                    || (Objects.equals(name, ((Puppy) that).name) && Objects.equals(breed, ((Puppy) that).breed));
        }

        @Override
        public String toString() {
            return String.format("%s %s%n", name, breed);
        }
    }
}
