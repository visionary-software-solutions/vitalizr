package software.visionary.vitalizr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.visionary.eventr.Event;
import software.visionary.eventr.Observer;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class ObservableVitalRepositoryTest {
    @Mock Observer observer;
    @Mock VitalRepository decorated;
    ObservableVitalRepository toTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        toTest = new ObservableVitalRepository(decorated);
    }

    @Test
    void rejectsNullVitalRepository() {
        Assertions.assertThrows(NullPointerException.class, () -> new ObservableVitalRepository(null));
    }

    @Test
    void canRegisterObservers() {
        toTest.add(observer);
        final Event e = mock(Event.class);
        toTest.notifyObservers(e);
        verify(observer).update(e);
    }

    @Test
    void rejectsNullObserver() {
        Assertions.assertThrows(NullPointerException.class, () -> toTest.add(null));
    }

    @Test
    void canUnsubscribeObservers() {
        toTest.add(observer);
        final Event e = mock(Event.class);
        toTest.notifyObservers(e);
        verify(observer).update(e);
        toTest.remove(observer);
        final Event another = mock(Event.class);
        toTest.notifyObservers(another);
        verifyNoMoreInteractions(observer);
    }

    @Test
    void delegatesQueriesToDecorated() {
        final Consumer<Vital> consumer = mock(Consumer.class);
        toTest.accept(consumer);
        verify(decorated).accept(consumer);
    }

    @Test
    void createsVitalSavedEventForEveryVitalSaved() {
        toTest.add(observer);
        final Vital toSave = mock(Vital.class);
        toTest.save(toSave);
        final ArgumentCaptor<VitalSavedEvent> captor = ArgumentCaptor.forClass(VitalSavedEvent.class);
        verify(observer).update(captor.capture());
        final VitalSavedEvent event = captor.getValue();
        Assertions.assertEquals(toSave, event.getVital());
    }
}
