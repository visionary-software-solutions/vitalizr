package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;
import software.visionary.eventr.EventMediator;
import software.visionary.eventr.Observable;
import software.visionary.eventr.Observer;

public class MessageCrier implements Observer, Observable {
    public MessageCrier(EventMediator med) {
        mediator = med;
    }

    @Override
    public void update(Event event) {
        if (event instanceof MessageSavedEvent) {
            MessageSavedEvent e = (MessageSavedEvent) event;
            received = e.getMessage().getText();
            MessageReceivedEvent evento = new MessageReceivedEvent();
            evento.setOriginal(e.getMessage());
            notifyObservers(evento);
        }
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

    public String getReceived() {
        return received;
    }

    private String received = "";
    private final EventMediator mediator;
}
