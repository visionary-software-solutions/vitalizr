package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucoseConverter;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucoseSerializationProxy;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadBloodSugarsFromFileIntegrationTest {
    @Test
    void canLoadBloodSugarsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final BloodSugar toStore4 = new WholeBloodGlucose(Instant.now().plus(-2, ChronoUnit.DAYS), 225, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WholeBloodGlucoseSerializationProxy serialized4 = WholeBloodGlucoseConverter.INSTANCE.to((WholeBloodGlucose) toStore4);
        final WriteObjectAsGZip<WholeBloodGlucoseSerializationProxy> writer4 = new WriteObjectAsGZip<>(serialized4, data.toPath());
        writer4.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        // Then: The vitals should be returned
        final Collection<BloodSugar> storedToo = Vitalizr.getBloodSugarsFor(mom);
        assertTrue(storedToo.contains(toStore4));
        data.delete();
    }
}
