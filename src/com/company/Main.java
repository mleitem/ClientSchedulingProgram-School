package com.company;

import Helper.CustomerQuery;
import Helper.JDBC;
import Model.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();

        User.login("admin", "admin");

        JDBC.closeConnection();
    }
}
