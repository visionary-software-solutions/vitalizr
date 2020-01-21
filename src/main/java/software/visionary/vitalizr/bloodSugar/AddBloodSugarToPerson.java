package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddBloodSugarToPerson extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BloodSugar store = new WholeBloodGlucose(Instant.now(), Integer.parseInt(tokens[1]), person);
        Vitalizr.storeBloodSugarFor(store);
        return store;
    }
}
