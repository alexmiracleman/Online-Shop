package org.alex.dao;


import org.alex.entity.Salt;


import java.util.List;

public interface SaltDao {

    List<Salt> findAll();

    void add(Salt salt);


}
