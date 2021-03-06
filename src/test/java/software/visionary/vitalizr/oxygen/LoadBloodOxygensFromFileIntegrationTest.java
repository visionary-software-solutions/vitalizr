package software.visionary.vitalizr.oxygen;

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

class LoadBloodOxygensFromFileIntegrationTest {
    @Test
    void canLoadBloodOxygensFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final PeripheralOxygenSaturation toStore3 = new PeripheralOxygenSaturation(Instant.now().plus(-2, ChronoUnit.DAYS), 96, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final Object serialized3 = toStore3.asSerializationProxy();
        final WriteObjectAsGZip<Object> writer3 = new WriteObjectAsGZip<>(serialized3, data.toPath());
        writer3.run();
        // When: I call loadVitalsFromFile
        VitalPersister.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        // Then: The vitals should be returned
        final Collection<BloodOxygen> storedAsWell = Vitalizr.getBloodOxygensFor(mom);
        assertTrue(storedAsWell.contains(toStore3));
        data.delete();
    }
}
