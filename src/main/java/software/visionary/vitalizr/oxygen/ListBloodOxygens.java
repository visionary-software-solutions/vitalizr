package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBloodOxygens extends GetAllByID {
    @Override
    protected Collection<BloodOxygen> forId(final UUID id) {
        return Vitalizr.lookup(id, Vitalizr::getBloodOxygensFor);
    }
}
