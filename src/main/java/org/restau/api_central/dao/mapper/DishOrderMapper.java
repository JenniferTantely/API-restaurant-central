package org.restau.api_central.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.restau.api_central.dao.operations.DishDAO;
import org.restau.api_central.dao.operations.DishOrderStatusDAO;
import org.restau.api_central.dao.operations.SalesPointDAO;
import org.restau.api_central.model.*;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishOrderMapper implements Function<ResultSet, DishOrder> {
    private final DishOrderStatusDAO dishOrderStatusDAO;
    private final DishDAO dishDAO;
    private final SalesPointDAO salesPointDAO;


    @SneakyThrows
    @Override
    public DishOrder apply(ResultSet resultSet) {
        Long dishOrderId = resultSet.getLong("id");
        Long dishId = resultSet.getLong("id_dish");
        Long salesPointId = resultSet.getLong("sales_point_id");

        List<DishOrderStatus> statusList = dishOrderStatusDAO.getDishOrderStatusByDishOrderId(dishOrderId);
        Dish orderedDish = dishDAO.getDishById(dishId);
        SalesPoint salesPoint = salesPointDAO.getSalesPointById(salesPointId);

        return new DishOrder(
                dishOrderId,
                orderedDish,
                resultSet.getInt("quantity"),
                statusList,
                StatusOrderType.valueOf(resultSet.getString("order_status")),
                salesPoint
        );
    }
}
