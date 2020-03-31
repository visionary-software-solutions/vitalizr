package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.AddVital;
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
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new WholeBloodGlucose(Instant.now(), Integer.parseInt(tokens[1]), person);
    }
}
