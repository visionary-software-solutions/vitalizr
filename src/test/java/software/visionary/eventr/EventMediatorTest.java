package software.visionary.eventr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.visionary.eventr.testImpls.*;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventMediatorTest {
    private EventingSystem toTest;
    private Observable toObserve;
    private Observer interested;

    @BeforeEach
    public void setup() {
        toObserve = mock(Observable.class);
        interested = mock(Observer.class);
        toTest = new EventingSystem();
    }

    @Test
    public void canRegisterObserversAndObservables() {
        // when: "an observable observer pair is registered with the mediator"
        toTest.register(toObserve, interested);
        //then: "the mediator has the observer in the observers for the observable"
        assertTrue(toTest.has(interested));
    }

    @Test
    public void canOnlyRegisterTheSameObserverOnce() {
        // when: "an observable observer pair is registered with the mediator"
        toTest.register(toObserve, interested);
        // and: "the same pair is registered again"
        toTest.register(toObserve, interested);
        // then: "the mediator has only one instance of the observer in the observers for the observable"
        final Collection<Observer> observers = toTest.observersOf(toObserve);
        assertTrue(observers.contains(interested));
        assertEquals(1, observers.size());
    }

    @Test
    public void canRegisterMultipleObserversForAnObservable() {
        // when: "an observable observer pair is registered with the mediator"
        toTest.register(toObserve, interested);
        // and: "I want to add another observer"
        final Observer another = mock(Observer.class);
        toTest.register(toObserve, another);
        // then: "the mediator has the observer in the list of observers for the observable"
        assertTrue(toTest.observersOf(toObserve).contains(another));
    }

    @Test
    public void canUnregisterAnObserverObservablePair() {
        //given: "an observable observer pair is registered with the mediator"
        toTest.register(toObserve, interested);
        //when: "the pair is unregistered"
        toTest.unregister(toObserve, interested);
        //then: "the mediator has the observer in the observers for the observable"
        assertFalse(toTest.observersOf(toObserve).contains(interested));
    }

    @Test
    public void notifiesObserversWhenObservableHasAnEvent() {
        // given: "an observable observer pair is registered with the mediator"
        toTest.register(toObserve, interested);
        // and: "I want to add another observer"
        final Observer another = mock(Observer.class) ;
        toTest.register(toObserve, another);
        // and: "an interesting event"
        final Event e = mock(Event.class);
        // when: "the observable wants to notify observers about the event"
        toTest.notifyObserversForObservable(toObserve, e);
        //then: "the first interested party is notified"
        verify(interested).update(e);
        // and: "the other party is notified as well"
        verify(another).update(e);
    }

    @Test
    public void canGetTheAvailableObservables() {
        // given: "a registered observable"
        toTest.register(toObserve, interested);
        // and: "another possible Observable"
        final Observable another = mock(Observable.class);
        toTest.register(another, null);
        // when: "the available observables are asked for"
        final Set<Observable> result = toTest.getAvailableObservables();
        // then: "both observables are included"
        assertTrue(result.contains(toObserve) && result.contains(another));
    }

    @Test
    public void canChainUpdatesBetweenMultipleObservableObserverConnections() {
        // given: "an observable that can trigger events"
        final MessageRepository base = new MessageRepository(toTest);
        // and: "an intermediary that both observes the original observable and can be observed by others"
        final MessageCrier crier = new MessageCrier(toTest);
        base.add(crier);
        // and: "an observer for the intermediary"
        final MessageCapitalizer captain = new MessageCapitalizer();
        crier.add(captain);
        // and: "something interesting happens on the original observable"
        final String text = "I should chain!";
        final Message message = new Message();
        message.setText(text);
        // when: "the original observable has an interesting event"
        base.saveMessage(message);
        // then: "the intermediary should receive the event"
        assertEquals(crier.getReceived(), text);
        // and: "the observer for the intermediary should receive the event"
        assertEquals(message.getText(), text.toUpperCase());
    }
}
