package org.restau.api_central.dao.mapper;

import lombok.SneakyThrows;
import org.restau.api_central.model.SalesPoint;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class SalesPointMapper implements Function<ResultSet, SalesPoint> {
    @SneakyThrows
    @Override

    public SalesPoint apply(ResultSet resultSet) {
        return new SalesPoint(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("url")
        );
    }

}
