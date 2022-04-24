package com.company;

import Helper.CustomerQuery;
import Helper.JDBC;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();

        int rowsAffected = CustomerQuery.updateName(4, "Derrick Barrie");
        if(rowsAffected > 0) {
            System.out.println("Update Successful!");
        }
        else{
            System.out.println("Update Failed!");
        }

        JDBC.closeConnection();
    }
}
