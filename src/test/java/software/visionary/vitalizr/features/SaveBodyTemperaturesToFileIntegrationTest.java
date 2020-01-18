package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyTemperature.ImperialTemperature;
import software.visionary.vitalizr.bodyTemperature.StringImperialTemperatureConverter;
import software.visionary.vitalizr.serialization.GZipFiles;

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

class SaveBodyTemperaturesToFileIntegrationTest {
    @Test
    void canSaveBodyTemperaturesToFile() throws IOException {
        // Given: A person
        final Person p = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<BodyTemperature> stored = temps(p);
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeTemperature);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), p.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BodyTemperature> found = StringImperialTemperatureConverter.INSTANCE.to(written.stream()).collect(Collectors.toList());
        assertTrue(found.containsAll(stored));
        data.delete();
    }

    private static Collection<BodyTemperature> temps(final Person person) {
        final Collection<BodyTemperature> alsoStored = new ArrayList<>(3);
        alsoStored.add(new ImperialTemperature(person, 97.9, Instant.now()));
        alsoStored.add(new ImperialTemperature(person, 98.2, Instant.now().plus(-1, ChronoUnit.DAYS)));
        alsoStored.add(new ImperialTemperature(person, 98.0, Instant.now().plus(-2, ChronoUnit.DAYS)));
        return alsoStored;
    }
}
