package org.restau.api_central.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Sale {
    private Long id;
    private SalesPoint salesPoint;
    private Dish dish;
    private Integer quantitySold;
    private Double totalAmount;

    public Sale(SalesPoint salesPoint, Dish dish, Integer quantitySold, Double totalAmount) {
        this.salesPoint = salesPoint;
        this.dish = dish;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }
}
