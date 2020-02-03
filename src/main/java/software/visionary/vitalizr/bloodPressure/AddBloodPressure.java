package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBloodPressure extends AddVital<BloodPressure> {
    @Override
    protected void saveVital(final BloodPressure store) {
        Vitalizr.storeBloodPressure(store);
    }

    @Override
    protected Combined deserialize(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return Combined.systolicAndDiastolicBloodPressure(Instant.now(), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), person);
    }
}
