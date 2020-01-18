package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.bodyMassIndex.QuetletIndexConverter;
import software.visionary.vitalizr.bodyMassIndex.QuetletIndexSerializationProxy;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.Weight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadBMIsFromFileIntegrationTest {
    @Test
    void canLoadBMIsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final BodyMassIndex toStore2 = new QueteletIndex(Instant.now().plus(-2, ChronoUnit.DAYS), 33.1, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final QuetletIndexSerializationProxy serialized2 = QuetletIndexConverter.INSTANCE.to((QueteletIndex) toStore2);
        final WriteObjectAsGZip<QuetletIndexSerializationProxy> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<Weight> stored = Vitalizr.getWeightsFor(mom);
        // Then: The vitals should be returned
        final Collection<BodyMassIndex> alsoStored = Vitalizr.getBodyMassIndicesFor(mom);
        assertTrue(alsoStored.contains(toStore2));
        data.delete();
    }
}
