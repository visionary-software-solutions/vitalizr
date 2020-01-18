package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.oxygen.PeripheralOxygenSaturation;
import software.visionary.vitalizr.oxygen.StringPeripheralOxygenSaturationConverter;
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

class SaveBloodOxygenToFileIntegrationTest {
    @Test
    void canSaveBloodOxygensToFile() throws IOException {
        // Given: A person
        final Person mom = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<BloodOxygen> alsoStored = bloodOxygens(mom);
        // And: Vitalizr has stored those vitals
        alsoStored.forEach(Vitalizr::storeBloodOxygenFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BloodOxygen> foundOxys = StringPeripheralOxygenSaturationConverter.INSTANCE.to(written.stream()).collect(Collectors.toList());
        assertTrue(foundOxys.containsAll(alsoStored));
        data.delete();
    }

    private static Collection<BloodOxygen> bloodOxygens(final Person mom) {
        final Collection<BloodOxygen> alsoStored = new ArrayList<>(3);
        alsoStored.add(new PeripheralOxygenSaturation(Instant.now(), 95, mom));
        alsoStored.add(new PeripheralOxygenSaturation((Instant.now().plus(-1, ChronoUnit.DAYS)), 91, mom));
        alsoStored.add(new PeripheralOxygenSaturation((Instant.now().plus(-2, ChronoUnit.DAYS)), 97, mom));
        return alsoStored;
    }
}
