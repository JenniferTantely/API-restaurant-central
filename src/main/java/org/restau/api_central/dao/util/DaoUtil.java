package org.restau.api_central.dao.util;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.DbConnection;
import org.restau.api_central.exception.ServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@RequiredArgsConstructor
public class DaoUtil {

    public static <T> T findById(DbConnection dbConnection, String sql, long id, Function<ResultSet, T> mapper) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapper.apply(rs);
                }
            }
            throw new RuntimeException("Not found");

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
