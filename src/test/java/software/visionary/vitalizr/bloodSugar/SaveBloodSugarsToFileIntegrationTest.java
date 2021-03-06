package software.visionary.vitalizr.bloodSugar;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.VitalPersister;
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

class SaveBloodSugarsToFileIntegrationTest {
    @Test
    void canSaveVitalsToFile() throws IOException {
        // Given: A person
        final Person mom = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<BloodSugar> fourthStored = bloodSugars(mom);
        // And: Vitalizr has stored those vitals
        fourthStored.forEach(Vitalizr::storeBloodSugar);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        VitalPersister.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BloodSugar> foundBloodSugars = WholeBloodGlucose.Factory.INSTANCE.create(written.stream()).collect(Collectors.toList());
        assertTrue(foundBloodSugars.containsAll(fourthStored));
        data.delete();
    }

    private static Collection<BloodSugar> bloodSugars(final Person person) {
        final Collection<BloodSugar> alsoStored = new ArrayList<>(3);
        alsoStored.add(new WholeBloodGlucose(Instant.now(), 240, person));
        alsoStored.add(new WholeBloodGlucose(Instant.now().plus(-1, ChronoUnit.DAYS), 137, person));
        alsoStored.add(new WholeBloodGlucose(Instant.now().plus(-2, ChronoUnit.DAYS), 199, person));
        return alsoStored;
    }
}
