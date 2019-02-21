package software.visionary.vitalizr.api;

public interface Converter<T, U> {
    U to(T toConvert);

    T from(U toConvert);
}
