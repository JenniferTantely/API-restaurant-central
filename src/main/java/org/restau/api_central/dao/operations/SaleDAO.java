package org.restau.api_central.dao.operations;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.dao.mapper.SaleMapper;
import org.restau.api_central.exception.ServerException;
import org.restau.api_central.model.Sale;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaleDAO {
    private final DbConnection dbConnection;
    private final SaleMapper saleMapper;

    public List<Sale> getAllSales() {
        List<Sale> allSales = new ArrayList<>();
        String sql = """
                SELECT s.id, s.sales_point_id, s.dish_id, s.quantity_sold, s.total_amount
                FROM sale s
                JOIN sales_point sp ON s.sales_point_id = sp.id
                JOIN dish d ON s.dish_id = d.id
                ORDER BY s.quantity_sold DESC, s.total_amount
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    allSales.add(saleMapper.apply(resultSet));
                }
            }
            return allSales;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public void deleteAll(Long salesPointId) {
        String sql = """
                DELETE FROM sale WHERE central.public.sale.sales_point_id = ?
                """;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, salesPointId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete sales", e);
        }
    }

    public void saveAll(List<Sale> sales) {
        String sql = "INSERT INTO sale (dish_id, sales_point_id, quantity_sold, total_amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            for (Sale sale : sales) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, sale.getDish().getId());
                    stmt.setLong(2, sale.getSalesPoint().getId());
                    stmt.setInt(3, sale.getQuantitySold());
                    stmt.setDouble(4, sale.getTotalAmount());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert sales", e);
        }
    }
}

