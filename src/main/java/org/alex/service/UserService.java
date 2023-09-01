package org.alex.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.dao.UserDao;
import org.alex.entity.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserService {

    private UserDao userDao;

    public List<User> findAll() {
        List<User> users = userDao.findAll();
        System.out.println("Obtained users: " + users.size());
        return users;

    }

    public void add(User user) {
        userDao.add(user);
    }


}
