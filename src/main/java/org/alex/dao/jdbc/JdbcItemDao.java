package org.alex.dao.jdbc;

import org.alex.dao.ItemDao;
import org.alex.dao.jdbc.mapper.ItemRowMapper;
import org.alex.entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemDao implements ItemDao {
    private static final ItemRowMapper ITEM_ROW_MAPPER = new ItemRowMapper();
    private static final String FIND_ALL_SQL = """
            SELECT id, name, price, department, creation_date FROM inventory ORDER BY id;
            """;
    private static final String ADD_SQL = """
            INSERT INTO inventory (name, price, department, creation_date) VALUES (?, ?, ?, ?);
             """;
    private static final String DELETE_SQL = """
            DELETE FROM inventory WHERE name = ?;
             """;
    private static final String UPDATE_SQL = """
            UPDATE inventory SET price = ?, creation_date = ? WHERE name = ?;
             """;

    @Override
    public List<Item> findAll() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                Item item = ITEM_ROW_MAPPER.mapRow(resultSet);
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with finding all items");
        }

    }

    public void add(Item item) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getPrice());
            preparedStatement.setString(3, item.getItemDepartmentType().getId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(item.getCreationDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the item insertion", e);
        }
    }

    public void delete(Item item) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the item removal", e);
        }
    }

    public void update(Item item) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, item.getPrice());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(item.getCreationDate()));
            preparedStatement.setString(3, item.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with the item update", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/onlinestore", "postgres", "Alex123!");
    }
}
