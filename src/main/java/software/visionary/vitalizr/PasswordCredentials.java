package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Authenticatable;
import software.visionary.vitalizr.api.Credentials;

import java.util.Objects;

public class PasswordCredentials implements Credentials {
    private final Authenticatable owner;
    private final String value;

    public PasswordCredentials(final Authenticatable human, final String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.value = password;
        this.owner = Objects.requireNonNull(human);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PasswordCredentials that = (PasswordCredentials) o;
        return owner.equals(that.owner) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, value);
    }
}
