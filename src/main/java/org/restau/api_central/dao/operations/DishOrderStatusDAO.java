package org.restau.api_central.dao.operations;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.dao.mapper.DishOrderStatusMapper;
import org.restau.api_central.exception.ServerException;
import org.restau.api_central.model.DishOrderStatus;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishOrderStatusDAO {
    private final DbConnection dbConnection;
    private final DishOrderStatusMapper dishOrderStatusMapper;

    public List<DishOrderStatus> getDishOrderStatusByDishOrderId(Long dishOrderId) {
        List<DishOrderStatus> dishOrderStatuses = new ArrayList<>();

        String sql = """
                SELECT dos.id, dos.dish_order_id, dos.status, dos.creation_datetime
                FROM dish_order_status dos
                JOIN dish_order d_o ON d_o.id = dos.dish_order_id
                WHERE d_o.id = ?
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dishOrderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrderStatuses.add(dishOrderStatusMapper.apply(resultSet));
                }
            }
            return dishOrderStatuses;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

}
