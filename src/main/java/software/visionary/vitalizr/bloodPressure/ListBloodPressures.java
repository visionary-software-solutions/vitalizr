package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBloodPressures extends GetAllByID {
    @Override
    protected Collection<BloodPressure> forId(final UUID id) {
        return Vitalizr.lookup(id, Vitalizr::getBloodPressuresFor);
    }
}
