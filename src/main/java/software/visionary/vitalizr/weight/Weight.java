package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

public interface Weight extends Scalable {
    @Override
    default Unit getUnit() {
        return Gram.INSTANCE;
    }
}
