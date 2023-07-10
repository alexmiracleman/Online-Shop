package org.alex.dao.jdbc.mapper;

import org.alex.entity.Item;
import org.alex.entity.ItemDepartmentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ItemRowMapper {
    public Item mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        String itemDepartmentTypeId = resultSet.getString("department");
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        Item item = Item.builder().
                id(id)
                .name(name)
                .price(price)
                .itemDepartmentType(ItemDepartmentType.getById(itemDepartmentTypeId))
                .creationDate(creationDate.toLocalDateTime())
                .build();

        return item;


    }
}
