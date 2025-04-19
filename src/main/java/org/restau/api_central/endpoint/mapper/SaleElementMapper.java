package org.restau.api_central.endpoint.mapper;

import org.restau.api_central.endpoint.rest.SalesElement;
import org.restau.api_central.model.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleElementMapper {
    public SalesElement toRest(Sale sale) {
        return new SalesElement(
                sale.getSalesPoint().getName(),
                sale.getDish().getName(),
                sale.getQuantitySold(),
                sale.getTotalAmount()
        );
    }
}
