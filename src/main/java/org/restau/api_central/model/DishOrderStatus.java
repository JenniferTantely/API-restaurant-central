package org.restau.api_central.model;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DishOrderStatus {
    private StatusDishOrderType status;
    private Instant changeDatetime;
}
