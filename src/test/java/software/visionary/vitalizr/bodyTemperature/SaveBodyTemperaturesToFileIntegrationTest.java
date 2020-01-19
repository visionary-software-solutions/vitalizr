package software.visionary.vitalizr.bodyTemperature;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveBodyTemperaturesToFileIntegrationTest {

    private static final Person PERSON = Fixtures.createRandomPerson();

    @ParameterizedTest
    @MethodSource
    void canSaveBodyTemperaturesToFile(final Collection<BodyTemperature> stored) throws IOException {
        // Given: A person
        // And: Some Vitals for the person
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeTemperature);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BodyTemperature> found = ImperialTemperature.deserialize(written.stream()).collect(Collectors.toList());
        assertTrue(found.containsAll(stored));
        data.delete();
    }

    private static Stream<Collection<BodyTemperature>> canSaveBodyTemperaturesToFile() {
        final Collection<BodyTemperature> stored = new ArrayList<>(3);
        stored.add(new MetricTemperature(PERSON, 36.1, Instant.now()));
        stored.add(new MetricTemperature(PERSON, 36.3, Instant.now().plus(-1, ChronoUnit.DAYS)));
        stored.add(new MetricTemperature(PERSON, 37, Instant.now().plus(-2, ChronoUnit.DAYS)));
        final Collection<BodyTemperature> alsoStored = new ArrayList<>(3);
        alsoStored.add(new ImperialTemperature(PERSON, 97.9, Instant.now()));
        alsoStored.add(new ImperialTemperature(PERSON, 98.2, Instant.now().plus(-1, ChronoUnit.DAYS)));
        alsoStored.add(new ImperialTemperature(PERSON, 98.0, Instant.now().plus(-2, ChronoUnit.DAYS)));
        return Stream.of(alsoStored);
    }
}
