package org.restau.api_central.endpoint.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.restau.api_central.model.StatusDishOrderType;
import org.restau.api_central.model.StatusOrderType;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DishOrderResponse {

    private Long id;
    private DishResponse dish;
    private Integer quantity;
    private List<DishOrderStatusResponse> statusHistories;
    private StatusOrderType orderStatus;
    private SalesPointResponse salesPoint;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DishResponse {
        private Long id;
        private String name;
        private Double actualPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DishOrderStatusResponse {
        private StatusDishOrderType status;
        private Instant changeDatetime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesPointResponse {
        private Long id;
        private String name;
    }
}

