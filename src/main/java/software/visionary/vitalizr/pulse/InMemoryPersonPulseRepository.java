package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryPersonPulseRepository implements VitalRepository<PersonPulse> {
    private final Map<Person, List<PersonPulse>> stored;

    public InMemoryPersonPulseRepository() {
        stored = new ConcurrentHashMap<>();
    }

    @Override
    public void save(final PersonPulse toSave) {
        stored.computeIfPresent(toSave.getPerson(), (person, pressures) -> Stream.concat(pressures.stream(), Stream.of(toSave)).collect(Collectors.toList()));
        stored.putIfAbsent(toSave.getPerson(), Stream.of(toSave).collect(Collectors.toList()));
    }

    @Override
    public void accept(final Consumer<PersonPulse> visitor) {
        stored.values().stream().flatMap(List::stream).forEach(visitor);
    }
}
