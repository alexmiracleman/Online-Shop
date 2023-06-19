package org.alex.dao;


import org.alex.entity.Item;
import org.alex.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    void add(User user);


}
