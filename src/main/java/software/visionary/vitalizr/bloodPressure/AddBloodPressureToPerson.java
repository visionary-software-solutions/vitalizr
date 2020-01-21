package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddBloodPressureToPerson extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final Combined store = Combined.systolicAndDiastolicBloodPressure(Instant.now(), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), person);
        Vitalizr.storeBloodPressureFor(store);
        return store;
    }
}
