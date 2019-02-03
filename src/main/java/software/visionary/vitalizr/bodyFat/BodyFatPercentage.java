package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BodyFatPercentage extends Vital {
    @Override
    default Unit getUnit() {
        return new Unit() {
            @Override
            public String getName() {
                return "NONE";
            }

            @Override
            public String getSymbol() {
                return "";
            }
        };
    }
}
