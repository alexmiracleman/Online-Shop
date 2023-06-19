package org.alex.dao.jdbc.mapper;

import org.alex.entity.Item;
import org.alex.entity.ItemDeptType;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        //prepare
        ItemRowMapper itemRowMapper = new ItemRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2023, 06, 06, 17, 22);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getString("name")).thenReturn("MK");
        when(resultSetMock.getInt("price")).thenReturn(1000);
        when(resultSetMock.getInt("id")).thenReturn(106);

        when(resultSetMock.getTimestamp("creation_date")).thenReturn(timestamp);
        // when
        Item actual = itemRowMapper.mapRow(resultSetMock);
        //then
        assertEquals(106, actual.getId());
        assertEquals(ItemDeptType.GRO, actual.getItemDeptType());
        assertEquals(1000, actual.getPrice());
        assertEquals(localDateTime, actual.getCreationDate());


    }

}