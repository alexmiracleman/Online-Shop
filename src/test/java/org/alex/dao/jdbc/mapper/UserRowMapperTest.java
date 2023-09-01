package org.alex.dao.jdbc.mapper;

import org.alex.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {
    @Test
    public void MappingTestForUser() throws SQLException {

        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("user_type")).thenReturn("USER");
        when(resultSet.getString("email")).thenReturn("test@test.com");
        when(resultSet.getString("password")).thenReturn("qwerty");
        when(resultSet.getString("salt")).thenReturn("salty");

        User user = userRowMapper.mapRow(resultSet);

        assertEquals("USER", user.getUserType().toString());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("qwerty", user.getPassword());
        assertEquals("salty", user.getSalt());
    }
}