package org.restau.api_central.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Dish {
    private Long id;
    private String name;
    private Double actualPrice;
}
