package org.alex.dao.jdbc.mapper;

import org.alex.dao.jdbc.mapper.ItemRowMapper;
import org.alex.entity.Item;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ItemRowMapperTest {
    @Test
    public void testItemRowMap() throws SQLException {
        ItemRowMapper itemRowMapper = new ItemRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        Timestamp time = Timestamp.valueOf(localDateTime);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(7);
        when(resultSet.getString("name")).thenReturn("Alex");
        when(resultSet.getInt("price")).thenReturn(199);
        when(resultSet.getString("department")).thenReturn("Furniture");
        when(resultSet.getTimestamp("creation_date")).thenReturn(time);

        Item actual = itemRowMapper.mapRow(resultSet);
        assertEquals(7, actual.getId());
        assertEquals("Alex", actual.getName());
        assertEquals(199, actual.getPrice());
        assertNotEquals("Furniture", actual.getItemDepartmentType());
        assertEquals(localDateTime, actual.getCreationDate());
    }
}
