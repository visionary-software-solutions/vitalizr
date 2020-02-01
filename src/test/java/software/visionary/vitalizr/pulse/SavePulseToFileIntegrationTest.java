package software.visionary.vitalizr.pulse;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.serialization.GZipFiles;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SavePulseToFileIntegrationTest {
    @Test
    void canSavePulsesToFile() throws IOException {
        // Given: A person
        final Person p = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<Pulse> alsoStored = pulses(p);
        // And: Vitalizr has stored those vitals
        alsoStored.forEach(Vitalizr::storePulse);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), p.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<Pulse> found = HeartrateMonitor.Factory.INSTANCE.create(written.stream()).collect(Collectors.toList());
        assertTrue(found.containsAll(alsoStored));
        data.delete();
    }

    private static Collection<Pulse> pulses(final Person mom) {
        final Collection<Pulse> alsoStored = new ArrayList<>(3);
        alsoStored.add(new HeartrateMonitor(Instant.now(), 52, mom));
        alsoStored.add(new HeartrateMonitor(Instant.now().plus(-1, ChronoUnit.DAYS), 55, mom));
        alsoStored.add(new HeartrateMonitor(Instant.now().plus(-2, ChronoUnit.DAYS), 58, mom));
        return alsoStored;
    }
}
