package software.visionary.eventr;

import java.util.Set;

public interface EventMediator {
    void register(Observable toObserve, Observer observer);
    void unregister(Observable toObserve, Observer observer);
    void notifyObserversForObservable(Observable changed, Event notifyAbout);
    Set<Observable> getAvailableObservables();
}
