package org.restau.api_central.endpoint.mapper;

import org.restau.api_central.endpoint.rest.DishOrderResponse;
import org.restau.api_central.model.DishOrderStatus;
import org.springframework.stereotype.Component;

@Component
public class DishOrderStatusResponseMapper {
    public DishOrderStatus toModel (DishOrderResponse.DishOrderStatusResponse dishOrderStatusResponse) {
        return new DishOrderStatus(dishOrderStatusResponse.getStatus(), dishOrderStatusResponse.getChangeDatetime());
    }
}
