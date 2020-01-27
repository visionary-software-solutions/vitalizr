package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddBloodOxygen extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BloodOxygen store = new PeripheralOxygenSaturation(Instant.now(), Integer.parseInt(tokens[1]), person);
        Vitalizr.storeBloodOxygen(store);
        return store;
    }
}
