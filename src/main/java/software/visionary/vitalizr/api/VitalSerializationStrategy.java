package software.visionary.vitalizr.api;

import java.util.Collection;

public interface VitalSerializationStrategy<T> {
    Collection<Vital> deserialize(T origin);

    void serialize(Collection<Vital> toWrite, T destination);
}
