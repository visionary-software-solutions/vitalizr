package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBloodSugar extends AddVital<BloodSugar> {
    @Override
    protected void saveVital(final BloodSugar store) {
        Vitalizr.storeBloodSugar(store);
    }

    @Override
    protected BloodSugar deserialize(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = Human.createPerson(tokens[0]);
        return new WholeBloodGlucose(Instant.now(), Integer.parseInt(tokens[1]), person);
    }
}
