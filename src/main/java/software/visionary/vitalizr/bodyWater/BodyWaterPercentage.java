package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BodyWaterPercentage extends Vital {
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
