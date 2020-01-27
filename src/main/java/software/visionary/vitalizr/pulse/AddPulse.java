package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddPulse extends AddVital {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final Pulse store = new HeartrateMonitor(Instant.now(), Integer.parseInt(tokens[1]), person);
        Vitalizr.storePulse(store);
        return store;
    }
}
