package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Repository;
import software.visionary.vitalizr.notifications.Reminder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

final class InMemoryReminderRepository implements Repository<Reminder> {
    private final List<Reminder> SAVED = new CopyOnWriteArrayList<>();

    @Override
    public void save(final Reminder toSave) {
        SAVED.add(toSave);
    }

    @Override
    public void accept(final Consumer<Reminder> visitor) {
        SAVED.forEach(visitor);
    }
}
