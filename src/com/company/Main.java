package com.company;

import Helper.CustomerQuery;
import Helper.JDBC;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();

        CustomerQuery.select(29);

        JDBC.closeConnection();
    }
}
