package org.restau.api_central.model;

import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DishOrder {
    private Long id;
    private Dish dish;
    private Integer quantity;
    private List<DishOrderStatus> statusHistories;
    private StatusOrderType statusOrderType;
    private SalesPoint salesPoint;


    public DishOrder(Long id, Dish dish, Integer quantity, List<DishOrderStatus> statusHistories, StatusOrderType statusOrderType) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.statusHistories = statusHistories;
        this.statusOrderType = statusOrderType;
    }

    public Optional<Duration> getProcessingTimePerUnit() {
        Optional<Instant> inProgressTime = getStatusHistories().stream()
                .filter(s -> s.getStatus() == StatusDishOrderType.IN_PROGRESS)
                .map(DishOrderStatus::getChangeDatetime)
                .findFirst();

        Optional<Instant> finishedTime = getStatusHistories().stream()
                .filter(s -> s.getStatus() == StatusDishOrderType.FINISHED)
                .map(DishOrderStatus::getChangeDatetime)
                .findFirst();

        if (inProgressTime.isPresent() && finishedTime.isPresent() && getQuantity() > 0) {
            Duration processingDuration = Duration.between(inProgressTime.get(), finishedTime.get());
            return Optional.of(processingDuration.dividedBy(getQuantity()));
        }

        return Optional.empty();
    }
}
