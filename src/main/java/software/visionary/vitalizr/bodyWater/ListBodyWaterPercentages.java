package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBodyWaterPercentages extends GetAllByID {
    @Override
    protected Collection<BodyWaterPercentage> forId(final UUID id) {
        return Vitalizr.getBodyWaterPercentagesForID(id);
    }
}
