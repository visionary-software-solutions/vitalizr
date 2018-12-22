package software.visionary.vitalizr.notifications;

import software.visionary.vitalizr.api.Person;

import java.time.Instant;

public interface Reminder {
    Instant scheduledFor();

    Person target();
}
