package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;
import software.visionary.eventr.EventMediator;
import software.visionary.eventr.Observable;
import software.visionary.eventr.Observer;

import java.util.*;

public class EventingSystem implements EventMediator {
    private final Map<Observable, Set<Observer>> eventMap;

    public EventingSystem() {
        eventMap = new HashMap<>();
    }

    public boolean has(final Observer sought) {
        return eventMap.values().stream().flatMap(Collection::stream).anyMatch(o -> o.equals(sought));
    }

    public Collection<Observer> observersOf(final Observable ob) {
        return eventMap.get(ob);
    }

    @Override
    public void register(Observable toObserve, Observer observer) {
        if (eventMap.containsKey(toObserve)) {
            final Set<Observer> observers = eventMap.get(toObserve);
            observers.add(observer);
        } else {
            eventMap.put(toObserve, new HashSet<>() {{ add(observer); }});
        }
    }

    @Override
    public void unregister(Observable toObserve, Observer observer) {
        if (eventMap.containsKey(toObserve)) {
            final Set<Observer> observers = eventMap.get(toObserve);
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserversForObservable(Observable changed, final Event notifyAbout) {
        if (eventMap.containsKey(changed)) {
            final Set<Observer> observers = eventMap.get(changed);
            observers.forEach( o -> o.update(notifyAbout));
        }
    }

    @Override
    public Set<Observable> getAvailableObservables() {
        return eventMap.keySet();
    }
}
