package software.visionary.eventr.testImpls;

import software.visionary.eventr.Event;
import software.visionary.eventr.Observer;

public class MessageCapitalizer implements Observer {
    @Override
    public void update(Event event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            e.getOriginal().setText(e.getOriginal().getText().toUpperCase());
        }
    }
}
