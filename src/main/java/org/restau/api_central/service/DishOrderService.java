package org.restau.api_central.service;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.operations.DishOrderDAO;
import org.restau.api_central.model.DishOrder;
import org.restau.api_central.service.utils.AggregationType;
import org.restau.api_central.service.utils.DishSalesPointKey;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DishOrderService {
    private final DishOrderDAO dishOrderDA0;

    public Map<DishSalesPointKey, Duration> getProcessingTimeByDishAndSalesPoint(Long dishId, AggregationType aggregationType) {
        List<DishOrder> dishOrders = dishOrderDA0.getAllDishOrdersByDishId(dishId);
        Map<DishSalesPointKey, List<Duration>> groupedDurations = new HashMap<>();

        for (DishOrder dishOrder : dishOrders) {
            dishOrder.getProcessingTimePerUnit().ifPresent(duration -> {
                DishSalesPointKey key = new DishSalesPointKey(dishOrder.getDish(), dishOrder.getSalesPoint());
                groupedDurations.computeIfAbsent(key, k -> new ArrayList<>()).add(duration);
            });
        }

        Map<DishSalesPointKey, Duration> result = new HashMap<>();
        for (Map.Entry<DishSalesPointKey, List<Duration>> entry : groupedDurations.entrySet()) {
            result.put(entry.getKey(), computeAggregation(entry.getValue(), aggregationType));
        }
        return result;
    }


    private Duration computeAggregation(List<Duration> durations, AggregationType type) {
        if (durations.isEmpty()) return Duration.ZERO;

        return switch (type) {
            case AVERAGE -> {
                long totalMilliSec = durations.stream()
                        .mapToLong(Duration::toMillis)
                        .sum();
                yield Duration.ofMillis(totalMilliSec / durations.size());
            }
            case MINIMUM -> durations.stream()
                    .min(Duration::compareTo)
                    .orElse(Duration.ZERO);
            case MAXIMUM -> durations.stream()
                    .max(Duration::compareTo)
                    .orElse(Duration.ZERO);
        };
    }
}

