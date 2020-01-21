package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.weight.MetricWeight;

import java.time.Instant;
import java.util.Scanner;

public final class AddWeightToPerson extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final MetricWeight store = new MetricWeight(Instant.now(), Integer.valueOf(tokens[1]), person);
        Vitalizr.storeWeightFor(store);
        return store;
    }
}
