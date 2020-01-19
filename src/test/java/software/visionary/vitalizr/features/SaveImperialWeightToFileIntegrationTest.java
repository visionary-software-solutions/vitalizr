package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.weight.ImperialWeight;
import software.visionary.vitalizr.weight.StringImperialWeightConverter;

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

class SaveImperialWeightToFileIntegrationTest {
    @Test
    void canSaveWeightsToFile() throws IOException {
        // Given: A person
        final Person person = Fixtures.createRandomPerson();
        // And: Some Weights for the person
        final Collection<ImperialWeight> stored = weights(person);
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeWeightFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), person.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<ImperialWeight> foundWeights = StringImperialWeightConverter.INSTANCE.to(written.stream()).collect(Collectors.toList());
        assertTrue(foundWeights.containsAll(stored));
        data.delete();
    }

    private static Collection<ImperialWeight> weights(final Person mom) {
        final Collection<ImperialWeight> stored = new ArrayList<>(3);
        stored.add(new ImperialWeight(Instant.now(), 200.0, mom));
        stored.add(new ImperialWeight(Instant.now().plus(-1, ChronoUnit.DAYS), 205.0, mom));
        stored.add(new ImperialWeight(Instant.now().plus(-2, ChronoUnit.DAYS), 218.3, mom));
        return stored;
    }
}
