package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightSerializationProxy;
import software.visionary.vitalizr.weight.Weight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadFromVitalsFileIntegrationTest {
    @Test
    void canLoadVitalsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Human.createPerson("Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        final MetricWeight toStore = MetricWeight.inKilograms(100, Instant.now(), mom);
        final MetricWeight toStore2 = MetricWeight.inKilograms(102, Instant.now().plus(-2, ChronoUnit.DAYS), mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_vitals")).toFile();
        data.deleteOnExit();
        final MetricWeightSerializationProxy serialized = MetricWeightSerializationProxy.fromMetricWeight(toStore);
        final MetricWeightSerializationProxy serialized2 = MetricWeightSerializationProxy.fromMetricWeight(toStore2);
        final WriteObjectAsGZip<MetricWeightSerializationProxy> writer = new WriteObjectAsGZip<>(serialized, data.toPath());
        writer.run();
        final WriteObjectAsGZip<MetricWeightSerializationProxy> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<Weight> stored = Vitalizr.getWeightsFor(mom);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore));
        assertTrue(stored.contains(toStore2));
        data.delete();
    }
}
