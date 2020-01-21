package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;

import java.time.Instant;
import java.util.Scanner;

public final class AddBodyFatPercentage extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final BodyFatPercentage store = new BioelectricalImpedance(Instant.now(), Double.parseDouble(tokens[1]), person);
        Vitalizr.storeBodyFatPercentageFor(store);
        return store;
    }
}
