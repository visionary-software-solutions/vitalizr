package software.visionary.vitalizr;

import software.visionary.eventr.Event;
import software.visionary.eventr.Observer;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalSerializationStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class VitalPersister implements Observer {
    private static final VitalSerializationStrategy<File> SERIALIZER = VitalAsGZipString.INSTANCE;
    private static final Path DEFAULT_HOME = Paths.get(new File("").getAbsolutePath(), ".vitalizr");

    private final Set<Vital> alreadyPersisted;
    private final Path root;

    public VitalPersister(final Collection<Vital> vitalsToIgnore) {
        this(DEFAULT_HOME, vitalsToIgnore);
    }

    public VitalPersister(final Path rootDirectory, final Collection<Vital> vitalsToIgnore) {
        try {
            root = Objects.requireNonNull(rootDirectory);
            if (!root.toFile().exists()) {
                Files.createDirectory(root);
            }
            loadAll(root);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        alreadyPersisted = new CopyOnWriteArraySet<>();
        alreadyPersisted.addAll(vitalsToIgnore);
    }

    static void loadAll(final Path rootDirectory) throws IOException {
        if (rootDirectory.toFile().exists() && rootDirectory.toFile().isDirectory()) {
            Files.list(rootDirectory).forEach(contents -> {
                if (contents.toFile().isDirectory()) {
                    try {
                        Files.list(contents).forEach(f -> loadVitalsFromFile(f.toFile()));
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    loadVitalsFromFile(contents.toFile());
                }
            });
        }
    }

    public static void loadVitalsFromFile(final File data) {
        SERIALIZER.deserialize(data).forEach(Vitalizr::storeVital);
    }

    @Override
    public void update(final Event event) {
        if (event instanceof VitalSavedEvent) {
            final VitalSavedEvent e = (VitalSavedEvent) event;
            final Vital vital = e.getVital();
            if (!alreadyPersisted.contains(vital)) {
                final Lifeform owner = vital.belongsTo();
                saveVitalsFor(owner, root);
                alreadyPersisted.add(vital);
            }
        }
    }

    static File saveVitalsFor(final Lifeform lifeform, final Path rootDirectory) {
        final File saveFile = Paths.get(rootDirectory.toAbsolutePath().toString(), lifeform.getID().toString(), Long.toString(Instant.now().toEpochMilli())).toFile();
        if (!saveFile.exists()) {
            try {
                saveFile.getParentFile().mkdirs();
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        saveVitalsToFile(saveFile);
        return saveFile;
    }

    public static void saveVitalsToFile(final File data) {
        SERIALIZER.serialize(Vitalizr.getVitalsMatching(Vital.class, Objects::nonNull), data);
    }

}
