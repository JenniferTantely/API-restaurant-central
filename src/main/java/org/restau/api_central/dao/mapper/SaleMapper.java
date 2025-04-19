package org.restau.api_central.dao.mapper;

import lombok.SneakyThrows;
import org.restau.api_central.dao.operations.DishDAO;
import org.restau.api_central.dao.operations.SalesPointDAO;
import org.restau.api_central.model.Sale;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class SaleMapper implements Function<ResultSet, Sale> {
    private final SalesPointDAO salesPointDAO;
    private final DishDAO dishDAO;

    public SaleMapper(SalesPointDAO salesPointDAO, DishDAO dishDAO) {
        this.salesPointDAO = salesPointDAO;
        this.dishDAO = dishDAO;
    }

    @SneakyThrows
    @Override

    public Sale apply(ResultSet resultSet) {
        Long dishId = resultSet.getLong("dish_id");
        Long salesPointId = resultSet.getLong("sales_point_id");

        Sale sale = new Sale();
        sale.setId(resultSet.getLong("id"));
        sale.setSalesPoint(salesPointDAO.getSalesPointById(salesPointId));
        sale.setDish(dishDAO.getDishById(dishId));
        sale.setQuantitySold(resultSet.getInt("quantity_sold"));
        sale.setTotalAmount(resultSet.getDouble("total_amount"));
        return sale;
    }

}
