package org.restau.api_central.endpoint.mapper;

import org.restau.api_central.endpoint.rest.DishProcessingTimeRest;
import org.restau.api_central.endpoint.rest.DurationUnit;
import org.restau.api_central.model.DishOrder;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DishProcessingTimeRestMapper {
    public DishProcessingTimeRest toRest(DishOrder dishOrder, Duration duration, DurationUnit durationUnit) {
        double converted = switch (durationUnit) {
            case MINUTES -> duration.toMillis() / 1000.0 / 60;
            case HOURS -> duration.toMillis() / 1000.0 / 60 / 60;
            default -> duration.toMillis() / 1000.0;
        };
        return new DishProcessingTimeRest(
                dishOrder.getSalesPoint().getName(),
                dishOrder.getDish().getName(),
                converted,
                durationUnit
        );
    }
}
