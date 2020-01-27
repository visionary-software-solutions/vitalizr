package software.visionary.vitalizr.api;

import software.visionary.identifr.api.Identifiable;

public interface Lifeform extends Identifiable {
    Name getName();

    Birthdate getBirthdate();
}
