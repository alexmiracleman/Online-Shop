package org.alex.service;

import org.alex.dao.UserDao;
import org.alex.entity.User;
import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> findAll() {
        List<User> users = userDao.findAll();
        System.out.println("Obtained users: " + users.size());
        return users;

    }
    public void add(User user) {
        userDao.add(user);
    }




}
