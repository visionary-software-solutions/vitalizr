package software.visionary.eventr;

public interface Observable {
    void notifyObservers(Event event);
    void add(Observer toAdd);
    void remove(Observer toRemove);
}
