package org.restau.api_central.dao.operations;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.dao.mapper.DishOrderMapper;
import org.restau.api_central.exception.ServerException;
import org.restau.api_central.model.DishOrder;
import org.restau.api_central.model.DishOrderStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishOrderDAO {
    private final DbConnection dbConnection;
    private final DishOrderMapper dishOrderMapper;

    public List<DishOrder> getAllDishOrdersByDishId(Long dishId) {
        List<DishOrder> dishOrders = new ArrayList<>();
        String sql = """
    SELECT 
        d_o.id,
        d_o.dish_id AS id_dish,
        d_o.quantity,
        d_o.order_status,
        d_o.sales_point_id AS sales_point_id
    FROM dish_order d_o
    JOIN dish d ON d_o.dish_id = d.id
    JOIN sales_point sp ON d_o.sales_point_id = sp.id
    WHERE d.id = ?
    """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dishId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrders.add(dishOrderMapper.apply(resultSet));
                }
            }
            return dishOrders;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public void deleteAll(Long salesPointId) {
        String sql = "DELETE FROM dish_order WHERE sales_point_id = ?";
        try (Connection conn = dbConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, salesPointId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete dish orders", e);
        }
    }

    public void saveAll(List<DishOrder> dishOrders, Long salesPointId) {
        String insertDishOrderSql = "INSERT INTO dish_order (id, dish_id, sales_point_id, quantity, order_status) VALUES (?, ?, ?, ?, ?)";
        String insertStatusSql = "INSERT INTO dish_order_status (dish_order_id, status, creation_datetime) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            for (DishOrder order : dishOrders) {
                try (PreparedStatement stmt = conn.prepareStatement(insertDishOrderSql)) {
                    stmt.setLong(1, order.getId());
                    stmt.setLong(2, order.getDish().getId());
                    stmt.setLong(3, salesPointId);
                    stmt.setInt(4, order.getQuantity());
                    stmt.setObject(5, String.valueOf(order.getStatusOrderType()), Types.OTHER);
                    stmt.executeUpdate();
                }

                for (DishOrderStatus status : order.getStatusHistories()) {
                    try (PreparedStatement stmt = conn.prepareStatement(insertStatusSql)) {
                        stmt.setLong(1, order.getId());
                        stmt.setObject(2, String.valueOf(status.getStatus()), Types.OTHER);
                        System.out.println(status.getChangeDatetime());
                        stmt.setTimestamp(3, Timestamp.from(status.getChangeDatetime()));
                        stmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert dish orders", e);
        }
    }
}
