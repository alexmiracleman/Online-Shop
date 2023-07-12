package org.alex.service;

import org.alex.dao.SaltDao;
import org.alex.entity.Salt;

import java.util.List;

public class SaltService {

    private final SaltDao saltDao;

    public SaltService(SaltDao saltDao) {
        this.saltDao = saltDao;
    }

    public List<Salt> findAll() {
        List<Salt> salts = saltDao.findAll();
        System.out.println("Obtained salts: " + salts.size());
        return salts;
    }
    public void add(Salt salt) {
        saltDao.add(salt);
    }
}
