package com.formation.paradise.dao;

import com.formation.paradise.dao.jdbc.JdbcPlaceDao;
import com.formation.paradise.dao.jdbc.JdbcTripDao;

public class DaoFactory {

    private DaoFactory(){

    }

    public static JdbcTripDao getTripDao(){

        JdbcTripDao jtd = new JdbcTripDao();
        return jtd;
    }

    public static JdbcPlaceDao getPlaceDao(){

        JdbcPlaceDao jpd = new JdbcPlaceDao();
        return jpd;
    }
}
