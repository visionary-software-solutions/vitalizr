package software.visionary.vitalizr.api;

public interface Vital<T extends Lifeform> extends Scalable {
    T belongsTo();
}
