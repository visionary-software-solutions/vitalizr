package software.visionary.vitalizr.bloodPressure;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveBloodPressuresToFileIntegrationTest {
    @Test
    void canSaveBloodPressuresToFile() throws IOException {
        // Given: A person
        final Person mom = Fixtures.createRandomPerson();
        // And: Some Vitals for the person
        final Collection<BloodPressure> thirdStored = bloodPressures(mom);
        // And: Vitalizr has stored those vitals
        thirdStored.forEach(Vitalizr::storeBloodPressure);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        VitalPersister.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<BloodPressure> foundBPs = Combined.deserialize(written.stream()).collect(Collectors.toList());
        assertTrue(foundBPs.containsAll(thirdStored));
        data.delete();
    }

    private static Collection<BloodPressure> bloodPressures(final Person person) {
        final Collection<BloodPressure> stored = new ArrayList<>(3);
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(14), 151, 71, person));
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(3), 139, 68, person));
        stored.add(Combined.systolicAndDiastolicBloodPressure(Fixtures.observationAtMidnightNDaysAgo(2), 145, 71, person));
        return stored;
    }
}
