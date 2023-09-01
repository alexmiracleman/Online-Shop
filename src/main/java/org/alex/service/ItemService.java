package org.alex.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.dao.ItemDao;
import org.alex.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemService {

    private ItemDao itemDao;

    public List<Item> findAll() {
        List<Item> items = itemDao.findAll();
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

    public Item findById(int id) {
        List<Item> items = itemDao.findAll();
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Item findByIdInCart(List cart, int id) {
        List<Item> items = cart;
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
