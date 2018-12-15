package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface Weight extends Vital {
    @Override
    default Unit getUnit() {
        return Gram.INSTANCE;
    }
}
