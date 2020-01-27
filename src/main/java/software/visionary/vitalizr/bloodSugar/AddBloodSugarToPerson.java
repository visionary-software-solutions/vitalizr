package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBloodSugarToPerson extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BloodSugar store = new WholeBloodGlucose(Instant.now(), Integer.parseInt(tokens[1]), person);
        Vitalizr.storeBloodSugar(store);
        return store;
    }
}
