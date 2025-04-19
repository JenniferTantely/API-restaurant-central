package org.restau.api_central.dao.operations;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.dao.mapper.SalesPointMapper;
import org.restau.api_central.model.SalesPoint;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.restau.api_central.dao.util.DaoUtil.findById;

@Repository
@RequiredArgsConstructor

public class SalesPointDAO {
    private final DbConnection dbConnection;
    private final SalesPointMapper salesPointMapper;

    public SalesPoint getSalesPointById(long id) {
        String sql = "SELECT * FROM sales_point WHERE id = ?";
        return findById(dbConnection, sql, id, salesPointMapper);
    }

    @SneakyThrows
    public List<SalesPoint> findAll() {

        List<SalesPoint> salesPoints = new ArrayList<>();
        String sql = "SELECT sp.id, sp.name, sp.url FROM sales_point sp";

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            System.out.println(connection.getMetaData().getURL());
            while (resultSet.next()) {
                SalesPoint salesPoint = new SalesPoint(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("url")
                );
                System.out.println(salesPoint.toString());
                salesPoints.add(salesPoint);

            }
            return salesPoints;
        }

    }
}
