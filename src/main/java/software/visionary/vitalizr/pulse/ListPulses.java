package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.GetAllByID;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.UUID;

public final class ListPulses extends GetAllByID {
    @Override
    protected Collection<Pulse> forId(final UUID id) {
        return Vitalizr.lookup(id, Vitalizr::getPulsesFor);
    }
}
