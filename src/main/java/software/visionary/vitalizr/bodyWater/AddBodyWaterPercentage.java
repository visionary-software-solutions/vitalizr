package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddBodyWaterPercentage extends AddVital<BodyWaterPercentage> {
    @Override
    protected void saveVital(final BodyWaterPercentage store) {
        Vitalizr.storeBodyWaterPercentage(store);
    }

    @Override
    protected BodyWaterPercentage deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new BioelectricalImpedance(Instant.now(), Double.parseDouble(tokens[1]), person);
    }
}
