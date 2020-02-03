package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddPulse extends AddVital<Pulse> {
    @Override
    protected void saveVital(final Pulse store) {
        Vitalizr.storePulse(store);
    }

    @Override
    protected Pulse deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new HeartrateMonitor(Instant.now(), Integer.parseInt(tokens[1]), person);
    }
}
