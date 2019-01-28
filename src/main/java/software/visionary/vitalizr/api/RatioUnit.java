package software.visionary.vitalizr.api;

public interface RatioUnit extends Unit {
    default String getSymbol() {
        return String.format("%s/%s", getDivisor().getSymbol(), getDividend().getSymbol());
    }

    Unit getDivisor();

    Unit getDividend();
}
