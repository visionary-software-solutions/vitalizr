package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;

public class MessageSavedEvent implements Event {
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    private Message message;
}
