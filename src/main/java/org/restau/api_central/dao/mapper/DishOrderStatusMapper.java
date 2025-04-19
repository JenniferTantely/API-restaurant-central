package org.restau.api_central.dao.mapper;

import lombok.SneakyThrows;
import org.restau.api_central.model.DishOrderStatus;
import org.restau.api_central.model.StatusDishOrderType;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class DishOrderStatusMapper implements Function<ResultSet, DishOrderStatus> {
    @SneakyThrows
    @Override
    public DishOrderStatus apply(ResultSet resultSet) {
        DishOrderStatus status = new DishOrderStatus();
        status.setStatus(StatusDishOrderType.valueOf(resultSet.getString("status")));
        status.setChangeDatetime(resultSet.getTimestamp("creation_datetime").toInstant());
        return status;
    }
}
