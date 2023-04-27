package org.alex.dao.jdbc.mapper;

import org.alex.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ItemRowMapper {
    public Item mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int quantity = resultSet.getInt("quantity");
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        Item item = Item.builder().
                id(id)
                .name(name)
                .quantity(quantity)
                .creationDate(creationDate.toLocalDateTime())
                .build();

        return item;


    }
}
