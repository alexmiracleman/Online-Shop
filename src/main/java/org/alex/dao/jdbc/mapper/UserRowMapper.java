package org.alex.dao.jdbc.mapper;

import org.alex.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        User user = User.builder().
                id(id)
                .email(email)
                .password(password)
                .build();

        return user;


    }
}
