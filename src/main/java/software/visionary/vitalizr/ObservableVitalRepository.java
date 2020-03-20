package software.visionary.vitalizr;

import software.visionary.eventr.Event;
import software.visionary.eventr.Observable;
import software.visionary.eventr.Observer;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

final class ObservableVitalRepository implements VitalRepository, Observable {
    private final Set<Observer> interestedParties;
    private final VitalRepository wrapped;

    ObservableVitalRepository(final VitalRepository repository) {
        this.wrapped = Objects.requireNonNull(repository);
        interestedParties = new HashSet<>();
    }

    @Override
    public void notifyObservers(final Event event) {
        interestedParties.parallelStream().forEach(o -> o.update(event));
    }

    @Override
    public void add(final Observer toAdd) {
        interestedParties.add(Objects.requireNonNull(toAdd));
    }

    @Override
    public void remove(final Observer toRemove) {
        interestedParties.remove(toRemove);
    }

    @Override
    public void save(final Vital toSave) {
        wrapped.save(toSave);
        notifyObservers(new VitalSavedEvent(toSave));
    }

    @Override
    public void accept(final Consumer<Vital> visitor) {
        wrapped.accept(visitor);
    }
}
