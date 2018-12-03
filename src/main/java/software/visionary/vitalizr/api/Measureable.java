package software.visionary.vitalizr.api;

import java.time.Instant;

/**
 * A MARKER INTERFACE for measurement.
 */
public interface Measureable {
    Instant observedAt();
}
