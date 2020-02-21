package software.visionary.eventr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.visionary.eventr.testImpls.Message;
import software.visionary.eventr.testImpls.MessageCrier;
import software.visionary.eventr.testImpls.MessageSavedEvent;

public class ObserverTest {
    @BeforeEach
    public void setup() {
        med = Mockito.mock(EventMediator.class);
        toTest = new MessageCrier(med);
    }

    @Test
    public void canRespondToEventUpdates() {
        // given: "An event that the observer is interested in"
        MessageSavedEvent event = new MessageSavedEvent();
        Message message = new Message();

        message.setText("Yo!");
        event.setMessage(message);
        // when: "the observer receives the event"
        toTest.update(event);
        // then: "the observer acts on the interested event"
        Assertions.assertTrue(((MessageCrier) toTest).getReceived().equals("Yo!"));
    }

    private EventMediator med;
    private Observer toTest;
}
