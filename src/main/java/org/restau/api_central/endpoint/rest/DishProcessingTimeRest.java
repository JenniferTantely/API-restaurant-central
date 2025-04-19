package org.restau.api_central.endpoint.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishProcessingTimeRest {
    private String salesPoint;
    private String dish;
    private Double preparationDuration;
    private DurationUnit durationUnit;
}
