package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;
import java.util.Scanner;

public final class ListBloodSugarsForPerson extends ListVitals {
    @Override
    protected Collection<BloodSugar> getVitals(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").next();
        final Person person = Human.createPerson(input);
        return Vitalizr.getBloodSugarsFor(person);
    }
}
