package software.visionary.identifr.api;

public interface Authenticatable extends Identifiable {
    Credentials getCredentials();
}
