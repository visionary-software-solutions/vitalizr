package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public final class InMemoryVitalRepository implements VitalRepository {
    private final Collection<Vital> stored;

    InMemoryVitalRepository() {
        stored = new CopyOnWriteArraySet<>();
    }

    @Override
    public void save(final Vital toSave) {
        stored.add(toSave);
    }

    @Override
    public void accept(final Consumer<Vital> visitor) {
        stored.forEach(visitor);
    }
}
