package org.restau.api_central.dao.operations;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.dao.mapper.DishMapper;
import org.restau.api_central.model.Dish;
import org.springframework.stereotype.Repository;

import static org.restau.api_central.dao.util.DaoUtil.findById;

@Repository
@RequiredArgsConstructor
public class DishDAO {
    private final DbConnection dbConnection;
    private final DishMapper dishMapper;

    public Dish getDishById(long id) {
        String sql = "SELECT * FROM dish WHERE id = ?";
        return findById(dbConnection,sql, id, dishMapper);
    }
}
