package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListBodyTemperatures extends GetAllByID {
    @Override
    protected Collection<BodyTemperature> forId(final UUID id) {
        return Vitalizr.lookup(id, Vitalizr::getBodyTemperaturesFor);
    }
}
