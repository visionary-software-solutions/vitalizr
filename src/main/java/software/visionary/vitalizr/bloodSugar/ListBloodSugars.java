package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBloodSugars extends GetAllByID {
    @Override
    protected Collection<BloodSugar> forId(final UUID id) {
        return Vitalizr.lookup(id, Vitalizr::getBloodSugarsFor);
    }
}
