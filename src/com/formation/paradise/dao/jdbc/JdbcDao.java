package com.formation.paradise.dao.jdbc;

import com.formation.paradise.util.ConnectionManager;

import java.sql.Connection;

public abstract class JdbcDao {

    protected Connection connection = ConnectionManager.getConnection();

    public Connection getConnection() {
        return connection;
    }
}
