package software.visionary.eventr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import software.visionary.eventr.testImpls.EventingSystem;
import software.visionary.eventr.testImpls.Message;
import software.visionary.eventr.testImpls.MessageRepository;
import software.visionary.eventr.testImpls.MessageSavedEvent;

public class ObservableTest {
    @BeforeEach
    public void setup() {
        med = new EventingSystem();
        toTest = new MessageRepository(med);
        // given: "an observer to add"
        interested = Mockito.mock(Observer.class);
    }

    @Test
    public void canAddAnObserver() {
        // when: "I add an observer"
        toTest.add(interested);
        // then: "the observer list contains the observer"
        Assertions.assertTrue(med.has(interested));
    }

    @Test
    public void canRemoveAnObserver() {
        // given: "an observer has been added"
        toTest.add(interested);
        // when: "I remove that observer"
        toTest.remove(interested);
        // then: "the observer list does not contain the observer"
        Assertions.assertFalse(med.has(interested));
    }

    @Test
    public void canNotifyObservers() {
        // given: "I add an observer for an interested event"
        toTest.add(interested);
        // when: "I take an action that notifies observers"
        Message message = new Message();
        message.setText("Hi!");
        ((MessageRepository) toTest).saveMessage(message);
        // then: "my observers are notified"
        Mockito.verify(interested).update(ArgumentMatchers.any(MessageSavedEvent.class));
    }

    private Observable toTest;
    private Observer interested;
    private EventingSystem med;
}
