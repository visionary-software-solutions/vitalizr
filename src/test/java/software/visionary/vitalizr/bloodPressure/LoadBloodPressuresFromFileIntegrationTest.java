package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.VitalPersister;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.serialization.WriteObjectAsGZip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadBloodPressuresFromFileIntegrationTest {
    @Test
    void canLoadBloodPressuresFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final Combined toStore3 = Combined.systolicAndDiastolicBloodPressure(Instant.now().plus(-2, ChronoUnit.DAYS), 138, 89, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final Object serialized3 = toStore3.toSerializationProxy();
        final WriteObjectAsGZip<Object> writer3 = new WriteObjectAsGZip<>(serialized3, data.toPath());
        writer3.run();
        // When: I call loadVitalsFromFile
        VitalPersister.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        // Then: The vitals should be returned
        final Collection<BloodPressure> storedAsWell = Vitalizr.getBloodPressuresFor(mom);
        assertTrue(storedAsWell.contains(toStore3));
        data.delete();
    }

}
