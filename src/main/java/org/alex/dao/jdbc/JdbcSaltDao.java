package org.alex.dao.jdbc;

import org.alex.dao.SaltDao;
import org.alex.dao.jdbc.mapper.SaltRowMapper;
import org.alex.entity.Salt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSaltDao implements SaltDao {
    private final ConnectionFactory connectionFactory;

    public JdbcSaltDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private static final SaltRowMapper SALT_ROW_MAPPER = new SaltRowMapper();
    private static final String FIND_ALL_SQL = """
            SELECT id, email, pass_salt FROM salts;
            """;
    private static final String ADD_SQL = """
            INSERT INTO salts (email, pass_salt) VALUES (?, ?);
             """;

    @Override
    public List<Salt> findAll() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Salt> salts = new ArrayList<>();
            while (resultSet.next()) {
                Salt salt = SALT_ROW_MAPPER.saltRow(resultSet);
                salts.add(salt);
            }
            return salts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the finding all salts", e);
        }
    }

    public void add(Salt salt) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL)) {
            preparedStatement.setString(1, salt.getEmail());
            preparedStatement.setString(2, salt.getPassSalt());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the salt creation", e);
        }
    }
}
