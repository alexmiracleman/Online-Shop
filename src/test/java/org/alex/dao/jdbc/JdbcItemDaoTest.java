package org.alex.dao.jdbc;

import org.alex.entity.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcItemDaoITest {
    @Test
    public void testFindAllReturnCorrectData(){
        JdbcItemDao jdbcItemDao = new JdbcItemDao();
        List<Item> items = jdbcItemDao.findAll();
        assertFalse(items.isEmpty());
        for (Item item : items) {
            assertNotEquals(0,item.getId());
            assertNotNull(item.getName());
            assertNotNull(item.getPrice());
            assertNotNull(item.getCreationDate());
        }

    }

}