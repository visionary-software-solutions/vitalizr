package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InMemoryPersonWeightRepository implements VitalRepository<PersonWeight> {
    private final Map<Person, List<PersonWeight>> stored;

    public InMemoryPersonWeightRepository() {
        stored = new ConcurrentHashMap<>();
    }

    @Override
    public void save(final PersonWeight toSave) {
        stored.computeIfPresent(toSave.belongsTo(), (person, personWeights) -> Stream.concat(personWeights.stream(), Stream.of(toSave)).collect(Collectors.toList()));
        stored.putIfAbsent(toSave.belongsTo(), Stream.of(toSave).collect(Collectors.toList()));
    }

    @Override
    public void accept(final Consumer<PersonWeight> visitor) {
        stored.values().stream().flatMap(List::stream).forEach(visitor);
    }
}
