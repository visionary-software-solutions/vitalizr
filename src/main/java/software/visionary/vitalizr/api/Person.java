package software.visionary.vitalizr.api;

public interface Person extends Lifeform, Authenticatable {
    EmailAddress getEmailAddress();
}
