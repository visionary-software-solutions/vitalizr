package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddBodyMassIndex extends AddVital<BodyMassIndex> {
    @Override
    protected void saveVital(final BodyMassIndex store) {
        Vitalizr.storeBodyMassIndex(store);
    }

    @Override
    protected BodyMassIndex deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new QueteletIndex(Instant.now(), Double.parseDouble(tokens[1]), person);
    }
}
