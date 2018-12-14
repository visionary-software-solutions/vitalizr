package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryPersonBloodOxygenRepository implements VitalRepository<PersonBloodOxygen> {
    private final Map<Person, List<PersonBloodOxygen>> stored;

    public InMemoryPersonBloodOxygenRepository() {
        stored = new ConcurrentHashMap<>();
    }

    @Override
    public void save(final PersonBloodOxygen toSave) {
        stored.computeIfPresent(toSave.belongsTo(), (person, pressures) -> Stream.concat(pressures.stream(), Stream.of(toSave)).collect(Collectors.toList()));
        stored.putIfAbsent(toSave.belongsTo(), Stream.of(toSave).collect(Collectors.toList()));
    }

    @Override
    public void accept(final Consumer<PersonBloodOxygen> visitor) {
        stored.values().stream().flatMap(List::stream).forEach(visitor);
    }
}
