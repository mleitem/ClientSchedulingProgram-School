package Model;

import Helper.JDBC;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int id;
    private String username;
    private String password;

    /*public static void login(String checkUsername, String checkPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, checkUsername);
        ps.setString(2, checkPassword);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Success!");


        }
        else{
            System.out.println("Try again!");
        }
    }*/
}
