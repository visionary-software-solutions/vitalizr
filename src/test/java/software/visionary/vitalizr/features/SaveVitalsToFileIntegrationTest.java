package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveVitalsToFileIntegrationTest {
    @Test
    void canSaveVitalsToFile() throws IOException {
        // Given: A person
        final Person mom = Human.createPerson(UUID.randomUUID().toString() + ":Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        // And: Some Vitals for the person
        final Collection<BloodPressure> thirdStored = bloodPressures(mom);
        final Collection<BloodSugar> fourthStored = bloodSugars(mom);
        // And: Vitalizr has stored those vitals
        thirdStored.forEach(Vitalizr::storeBloodPressure);
        fourthStored.forEach(Vitalizr::storeBloodSugar);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BloodPressure> foundBPs = Combined.deserialize(written.stream()).collect(Collectors.toList());
        assertTrue(foundBPs.containsAll(thirdStored));
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

    private static Collection<BloodPressure> bloodPressures(final Person person) {
        final Collection<BloodPressure> stored = new ArrayList<>(3);
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(14), 151, 71, person));
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(3), 139, 68, person));
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(2), 145, 71, person));
        return stored;
    }
}
