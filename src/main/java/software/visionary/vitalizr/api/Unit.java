package software.visionary.vitalizr.api;

public interface Unit {
    String getName();
    String getSymbol();

    enum NONE implements Unit {
        INSTANCE;

        @Override
        public String getName() {
            return NONE.class.getSimpleName();
        }

        @Override
        public String getSymbol() {
            return "";
        }
    }
}
