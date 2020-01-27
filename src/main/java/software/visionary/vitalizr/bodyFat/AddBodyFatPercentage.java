package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBodyFatPercentage extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BodyFatPercentage store = new BioelectricalImpedance(Instant.now(), Double.parseDouble(tokens[1]), person);
        Vitalizr.storeBodyFatPercentage(store);
        return store;
    }
}
