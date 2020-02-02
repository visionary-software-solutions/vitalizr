package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListWeights extends GetAllByID {
    @Override
    protected Collection<Weight> forId(final UUID id) {
        return Vitalizr.getWeightsById(id);
    }
}
