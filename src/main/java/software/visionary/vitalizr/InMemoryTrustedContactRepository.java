package software.visionary.vitalizr;

import software.visionary.vitalizr.api.TrustedContact;
import software.visionary.vitalizr.api.TrustedContactRepository;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public final class InMemoryTrustedContactRepository implements TrustedContactRepository {
    private final Collection<TrustedContact> contacts;

    InMemoryTrustedContactRepository() {
        contacts = new CopyOnWriteArraySet<>();
    }

    @Override
    public void save(final TrustedContact toSave) {
        contacts.add(toSave);
    }

    @Override
    public void accept(final Consumer<TrustedContact> visitor) {
        contacts.forEach(visitor);
    }
}
