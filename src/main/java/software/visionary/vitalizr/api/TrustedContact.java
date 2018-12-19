package software.visionary.vitalizr.api;

public interface TrustedContact extends Person {
    default boolean caresAbout(final Vital vital) {
        return connectedTo() != null &&
                vital.belongsTo() != null &&
                vital.belongsTo().equals(connectedTo());
    }

    Lifeform connectedTo();
}
