package software.visionary.identifr.api;

public interface Credentials {
    byte[] encrypt(byte[] source);
    byte[] decrypt(byte[] source);
}
