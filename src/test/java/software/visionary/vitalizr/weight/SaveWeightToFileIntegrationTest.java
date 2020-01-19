package software.visionary.vitalizr.weight;

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

class SaveWeightToFileIntegrationTest {

    public static final Person PERSON = Fixtures.createRandomPerson();

    @ParameterizedTest
    @MethodSource
    void canSaveWeightsToFile(final Collection<Weight> stored) throws IOException {
        // Given: A person
        // And: Some Weights for the person
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeWeightFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<Weight> foundWeights =
                Stream.concat(MetricWeight.deserialize(written.stream()), ImperialWeight.deserialize(written.stream())).collect(Collectors.toList());
        assertTrue(foundWeights.containsAll(stored));
        data.delete();
    }

    private static Stream<Collection<Weight>> canSaveWeightsToFile() {
        final Collection<Weight> stored = new ArrayList<>(3);
        stored.add(new MetricWeight(Instant.now(), 100.1, PERSON));
        stored.add(new MetricWeight(Instant.now().plus(-1, ChronoUnit.DAYS), 101.2, PERSON));
        stored.add(new MetricWeight(Instant.now().plus(-2, ChronoUnit.DAYS), 105.0, PERSON));
        final Collection<Weight> alsoStored = new ArrayList<>(3);
        alsoStored.add(new ImperialWeight(Instant.now(), 200.0, PERSON));
        alsoStored.add(new ImperialWeight(Instant.now().plus(-1, ChronoUnit.DAYS), 205.0, PERSON));
        alsoStored.add(new ImperialWeight(Instant.now().plus(-2, ChronoUnit.DAYS), 218.3, PERSON));
        return Stream.of(stored, alsoStored);
    }
}
