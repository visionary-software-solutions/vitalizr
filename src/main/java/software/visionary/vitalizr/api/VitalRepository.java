package software.visionary.vitalizr.api;

import java.util.function.Consumer;

public interface VitalRepository<V extends Vital> {
    void save(V toSave);
    void accept(Consumer<V> visitor);
}
