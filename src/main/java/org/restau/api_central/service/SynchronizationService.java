package org.restau.api_central.service;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.operations.DishOrderDAO;
import org.restau.api_central.dao.operations.SaleDAO;
import org.restau.api_central.dao.operations.SalesPointDAO;
import org.restau.api_central.endpoint.mapper.DishOrderStatusResponseMapper;
import org.restau.api_central.endpoint.rest.DishOrderResponse;
import org.restau.api_central.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SynchronizationService {

    private final SalesPointDAO salesPointRepository;
    private final DishOrderDAO dishOrderRepository;
    private final SaleDAO saleRepository;
    private final DishOrderStatusResponseMapper dishOrderStatusResponseMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public void synchronizeAllSalesPoints() {
        List<SalesPoint> salesPoints = salesPointRepository.findAll();


        for (SalesPoint sp : salesPoints) {
            saleRepository.deleteAll(sp.getId());
            dishOrderRepository.deleteAll(sp.getId());

            List<DishOrder> dishOrders = fetchDishOrdersFromSalesPoint(sp);
            dishOrderRepository.saveAll(dishOrders, sp.getId());

            List<Sale> sales = computeSalesFromDishOrders(dishOrders, sp);
            System.out.println(sales);
            saleRepository.saveAll(sales);

        }
    }

    private List<DishOrder> fetchDishOrdersFromSalesPoint(SalesPoint salesPoint) {
        String endpoint = salesPoint.getUrl() + "/dishOrderDashboard";
        ResponseEntity<DishOrderResponse[]> response =
                restTemplate.getForEntity(endpoint, DishOrderResponse[].class);
        DishOrderResponse[] dtoArray = response.getBody();
        
        List<DishOrder> dishOrders = Arrays.stream(dtoArray)
                .map(this::mapToEntity)
                .toList();

        for (DishOrder dishOrder: dishOrders) {
            dishOrder.setSalesPoint(salesPoint);
        }
        
        return dishOrders;
        
    }

    private DishOrder mapToEntity(DishOrderResponse dto) {
        System.out.println(dto);
        List<DishOrderStatus> dishOrderStatuses = dto.getStatusHistories().stream()
                .map(dishOrderStatusResponseMapper::toModel)
                .toList();
        // Fill every attribute based on dto (implement accordingly)

        Dish dish = new Dish(dto.getDish().getId(), dto.getDish().getName(), dto.getDish().getActualPrice());
        return new DishOrder(dto.getId(), dish, dto.getQuantity(), dishOrderStatuses, dto.getOrderStatus(), null);
    }

    private List<Sale> computeSalesFromDishOrders(List<DishOrder> dishOrders, SalesPoint salesPoint) {
        return dishOrders.stream()
                .collect(Collectors.groupingBy(DishOrder::getDish))
                .entrySet().stream()
                .map(entry -> {
                    Dish dish = entry.getKey();
                    Integer quantitySold = entry.getValue().stream().mapToInt(DishOrder::getQuantity).sum();
                    Double totalAmount = quantitySold * dish.getActualPrice();
                    return new Sale(salesPoint, dish, quantitySold, totalAmount);
                })
                .toList();
    }
}

