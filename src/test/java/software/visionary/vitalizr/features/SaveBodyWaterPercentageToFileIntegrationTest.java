package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import software.visionary.vitalizr.bodyWater.BioelectricalImpedance;
import software.visionary.vitalizr.bodyWater.StringBioelectricalImpedanceConverter;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;
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

class SaveBodyWaterPercentageToFileIntegrationTest {
    @Test
    void canSaveBodyWaterPercentagesToFile() throws IOException {
        // Given: A person
        final Person mom = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<BodyWaterPercentage> alsoStored = waters(mom);
        // And: Vitalizr has stored those vitals
        alsoStored.forEach(Vitalizr::storeBodyWaterPercentageFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BodyWaterPercentage> found = StringBioelectricalImpedanceConverter.INSTANCE.to(written.stream()).collect(Collectors.toList());
        assertTrue(found.containsAll(alsoStored));
        data.delete();
    }

    private static Collection<BodyWaterPercentage> waters(final Person mom) {
        final Collection<BodyWaterPercentage> alsoStored = new ArrayList<>(3);
        alsoStored.add(new BioelectricalImpedance(Instant.now(), 39.9, mom));
        alsoStored.add(new BioelectricalImpedance(Instant.now().plus(-1, ChronoUnit.DAYS), 40.1, mom));
        alsoStored.add(new BioelectricalImpedance(Instant.now().plus(-2, ChronoUnit.DAYS), 40.2, mom));
        return alsoStored;
    }
}
