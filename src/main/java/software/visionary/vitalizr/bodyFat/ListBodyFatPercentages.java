package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBodyFatPercentages extends GetAllByID {
    @Override
    protected Collection<BodyFatPercentage> forId(final UUID id) {
        return Vitalizr.getBodyFatPercentagesByID(id);
    }
}
