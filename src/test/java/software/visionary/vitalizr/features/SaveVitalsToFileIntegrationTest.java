package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightSerializationProxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveVitalsToFileIntegrationTest {
    @Test
    void canSaveVitalsToFile() throws IOException {
        // Given: A person
        final Person mom = Human.createPerson("Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        // And: Some Vitals for the person
        final Collection<MetricWeight> stored = new ArrayList<>(3);
        stored.add(MetricWeight.inKilograms(100, Instant.now(), mom));
        stored.add(MetricWeight.inKilograms(101, Instant.now().plus(-1, ChronoUnit.DAYS), mom));
        stored.add(MetricWeight.inKilograms(105, Instant.now().plus(-2, ChronoUnit.DAYS), mom));
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeWeightFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final Stream<MetricWeight> asObjects = MetricWeightSerializationProxy.getMetricWeightStream(written);
        assertTrue(asObjects.collect(Collectors.toList()).containsAll(stored));
        data.delete();
    }
}
