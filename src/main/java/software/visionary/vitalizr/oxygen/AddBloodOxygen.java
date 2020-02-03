package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddBloodOxygen extends AddVital<BloodOxygen> {
    @Override
    protected void saveVital(final BloodOxygen store) {
        Vitalizr.storeBloodOxygen(store);
    }

    @Override
    protected BloodOxygen deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new PeripheralOxygenSaturation(Instant.now(), Integer.parseInt(tokens[1]), person);
    }
}
