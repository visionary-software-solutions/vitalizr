package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBloodPressure extends AddVital {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = Human.createPerson(tokens[0]);
        final Combined store = Combined.systolicAndDiastolicBloodPressure(Instant.now(), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), person);
        Vitalizr.storeBloodPressure(store);
        return store;
    }
}