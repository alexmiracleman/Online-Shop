package org.alex.service;

import org.alex.dao.ItemDao;
import org.alex.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

public class ItemService {
    private ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> findAll() {
        List<Item> items = itemDao.findAll();
        System.out.println("Obtained items: " + items.size());
        return items;

    }
    public void add(Item item) {
        LocalDateTime now = LocalDateTime.now();
        item.setCreationDate(now);
        itemDao.add(item);
    }
    public void delete(Item item) {
        itemDao.delete(item);
    }
    public void update(Item item) {
        LocalDateTime now = LocalDateTime.now();
        item.setCreationDate(now);
        itemDao.update(item);
    }


}
