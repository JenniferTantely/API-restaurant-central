package org.restau.api_central.dao.mapper;

import lombok.SneakyThrows;
import org.restau.api_central.model.Dish;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class DishMapper implements Function<ResultSet, Dish> {

    @SneakyThrows
    @Override

    public Dish apply(ResultSet resultSet) {
        return new Dish(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("actual_price")
        );
    }
}
