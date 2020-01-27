package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddBodyMassIndex extends AddVital {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BodyMassIndex store = new QueteletIndex(Instant.now(), Double.parseDouble(tokens[1]), person);
        Vitalizr.storeBodyMassIndex(store);
        return store;
    }
}
