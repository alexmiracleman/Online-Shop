package org.alex.dao;


import org.alex.entity.Item;

import java.util.List;

public interface ItemDao {

    List<Item> findAll();

    void add(Item item);

    void delete(Item item);

    void update(Item item);
}
