package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddPulse extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final Pulse store = new HeartrateMonitor(Instant.now(), Double.parseDouble(tokens[1]), person);
        Vitalizr.storePulseFor(store);
        return store;
    }
}
