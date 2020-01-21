package software.visionary.vitalizr.api;

public interface Authenticatable extends Identifiable {
    Credentials getCredentials();
}
