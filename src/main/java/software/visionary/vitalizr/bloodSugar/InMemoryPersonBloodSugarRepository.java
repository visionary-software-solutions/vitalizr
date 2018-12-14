package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryPersonBloodSugarRepository implements VitalRepository<PersonBloodSugar> {
    private final Map<Person, List<PersonBloodSugar>> stored;

    public InMemoryPersonBloodSugarRepository() {
        stored = new ConcurrentHashMap<>();
    }

    @Override
    public void save(final PersonBloodSugar toSave) {
        stored.computeIfPresent(toSave.belongsTo(), (person, sugars) -> Stream.concat(sugars.stream(), Stream.of(toSave)).collect(Collectors.toList()));
        stored.putIfAbsent(toSave.belongsTo(), Stream.of(toSave).collect(Collectors.toList()));
    }

    @Override
    public void accept(final Consumer<PersonBloodSugar> visitor) {
        stored.values().stream().flatMap(List::stream).forEach(visitor);
    }
}
