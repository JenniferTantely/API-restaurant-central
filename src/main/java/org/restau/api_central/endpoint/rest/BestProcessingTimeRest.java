package org.restau.api_central.endpoint.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BestProcessingTimeRest {
    private Instant updatedAt;
    private List<DishProcessingTimeRest> bestProcessingTimes;
}
