package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;

public class MessageReceivedEvent implements Event {
    public Message getOriginal() {
        return original;
    }

    public void setOriginal(Message original) {
        this.original = original;
    }

    private Message original;
}
