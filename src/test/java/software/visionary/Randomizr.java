package software.visionary;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public enum  Randomizr {
    INSTANCE;

    public int createRandomNumberBetween(final int start, final int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public char createRandomAlphabeticCharacter() {
        return (char) (ThreadLocalRandom.current().nextInt(26) + 'a');
    }

    public String createRandomAlphabeticString() {
        final int length = ThreadLocalRandom.current().nextInt(1,10);
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(createRandomAlphabeticCharacter());
        }
        return builder.toString();
    }

    public String createRandomPassword() {
        final SecureRandom random = new SecureRandom();
        final int length = random.nextInt(10);
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(26) + 'a');
        }
        return (builder.toString().trim().isEmpty()) ? createRandomPassword() : builder.toString();
    }
}
