package org.restau.api_central.endpoint;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.endpoint.mapper.DishProcessingTimeRestMapper;
import org.restau.api_central.endpoint.rest.BestProcessingTimeRest;
import org.restau.api_central.endpoint.rest.DishProcessingTimeRest;
import org.restau.api_central.endpoint.rest.DurationUnit;
import org.restau.api_central.model.DishOrder;
import org.restau.api_central.service.DishOrderService;
import org.restau.api_central.service.utils.AggregationType;
import org.restau.api_central.service.utils.DishSalesPointKey;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProcessingTimeController {

    private final DishOrderService dishOrderService;
    private final DishProcessingTimeRestMapper dishProcessingTimeRestMapper;

    @GetMapping("/dishes/{id}/bestProcessingTime")
    public BestProcessingTimeRest getBestProcessingTimes(
            @PathVariable("id") Long dishId,
            @RequestParam(name = "top") int top,
            @RequestParam(name = "durationUnit", defaultValue = "SECONDS") DurationUnit durationUnit,
            @RequestParam(name = "calculationMode", defaultValue = "AVERAGE") AggregationType aggregationType
    ) {
        Map<DishSalesPointKey, Duration> durationMap = dishOrderService
                .getProcessingTimeByDishAndSalesPoint(dishId, aggregationType);

        List<DishProcessingTimeRest> bestProcessingTimes = durationMap.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue()))
                .limit(top)
                .map(entry -> {
                    DishOrder fakeDishOrder = new DishOrder();
                    fakeDishOrder.setDish(entry.getKey().dish());
                    fakeDishOrder.setSalesPoint(entry.getKey().salesPoint());
                    return dishProcessingTimeRestMapper.toRest(fakeDishOrder, entry.getValue(), durationUnit);
                })
                .collect(Collectors.toList());

        return new BestProcessingTimeRest(Instant.now(), bestProcessingTimes);
    }
}
