package org.alex.dao.jdbc.mapper;

import org.alex.entity.Salt;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SaltRowMapper {
    public Salt saltRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String passSalt = resultSet.getString("pass_salt");
        Salt salt = Salt.builder().
                id(id)
                .email(email)
                .passSalt(passSalt)
                .build();

        return salt;


    }
}
