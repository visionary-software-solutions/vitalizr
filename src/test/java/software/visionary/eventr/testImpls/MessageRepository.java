package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;
import software.visionary.eventr.EventMediator;
import software.visionary.eventr.Observable;
import software.visionary.eventr.Observer;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements Observable {
    public MessageRepository(final EventMediator med) {
        mediator = med;
    }

    @Override
    public void notifyObservers(Event event) {
        mediator.notifyObserversForObservable(this, event);
    }

    @Override
    public void add(Observer toAdd) {
        mediator.register(this, toAdd);
    }

    @Override
    public void remove(Observer toRemove) {
        mediator.unregister(this, toRemove);
    }

    public void saveMessage(Message toSave) {
        messages.add(toSave);
        MessageSavedEvent event = new MessageSavedEvent();
        event.setMessage(toSave);
        notifyObservers(event);
    }

    private final EventMediator mediator;
    private List<Message> messages = new ArrayList<Message>();
}
