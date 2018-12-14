package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryPersonBloodPressureRepository implements VitalRepository<PersonBloodPressure> {
    private final Map<Person, List<PersonBloodPressure>> stored;

    public InMemoryPersonBloodPressureRepository() {
        stored = new ConcurrentHashMap<>();
    }

    @Override
    public void save(final PersonBloodPressure toSave) {
        stored.computeIfPresent(toSave.belongsTo(), (person, pressures) -> Stream.concat(pressures.stream(), Stream.of(toSave)).collect(Collectors.toList()));
        stored.putIfAbsent(toSave.belongsTo(), Stream.of(toSave).collect(Collectors.toList()));
    }

    @Override
    public void accept(final Consumer<PersonBloodPressure> visitor) {
        stored.values().stream().flatMap(List::stream).forEach(visitor);
    }
}
