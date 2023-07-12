package org.alex.dao.jdbc;

import org.alex.dao.UserDao;
import org.alex.dao.jdbc.mapper.UserRowMapper;
import org.alex.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private ConnectionFactory connectionFactory;

    public JdbcUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String FIND_ALL_SQL = """
            SELECT id, email, password FROM users;
            """;
    private static final String ADD_SQL = """
            INSERT INTO users (email, password) VALUES (?, ?);
             """;

    @Override
    public List<User> findAll() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the finding all users", e);
        }
    }

    public void add(User user) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the user creation", e);
        }
    }


}
