package software.visionary.vitalizr.api;

import java.util.function.Consumer;

public interface Repository<V> {
    void save(V toSave);

    void accept(Consumer<V> visitor);
}
