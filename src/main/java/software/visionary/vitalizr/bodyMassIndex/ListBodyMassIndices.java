package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.weight.GetAllByID;

import java.util.Collection;
import java.util.UUID;

public final class ListBodyMassIndices extends GetAllByID {
    @Override
    protected Collection<BodyMassIndex> forId(final UUID id) {
        return Vitalizr.getBMIsById(id);
    }
}
