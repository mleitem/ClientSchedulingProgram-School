package Model;

import Helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public abstract class CustomerQuery {

    public static int addCustomer(String customerName, String customerAddress, String postalCode, String phone, int divId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divId);
        int rowsAffected = ps.executeUpdate();

        Customer customer = new Customer(customerName, customerAddress, postalCode, phone, divId);
        Inventory.allCustomers.add(customer);

        return rowsAffected;
    }

    public static int updateCustomer(int customerId, String customerName) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setInt(2,customerId);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    // Need update address, phone, etc.

    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static void allCustomers() throws SQLException {
        Inventory.allCustomers.clear();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divId = rs.getInt("Division_ID");
            /*System.out.print(customerId + " | ");

            System.out.print(customerName + " | ");
            System.out.print(address + " | ");
            System.out.print(postalCode + " | ");
            System.out.print(phone + " | ");
            System.out.print(divId + "\n");*/
            Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divId);
            Inventory.addCustomer(customer);
        }
    }

    /*public static void select(int divId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            System.out.print(customerId + " | ");
            System.out.print(customerName + " | ");
            System.out.print(address + " | ");
            System.out.print(postalCode + " | ");
            System.out.print(phone + " | ");
            System.out.print(divisionId + "\n");
        }
    }*/

    public static ObservableList<String> viewAllCountries() throws SQLException {

        ObservableList<String> allCountries = FXCollections.observableArrayList();

        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            String completeCountry = countryID + ": " + countryName;
            allCountries.add(completeCountry);

        }
        return allCountries;
    }

    public static ObservableList<String> viewUSRegions() throws SQLException {

        ObservableList<String> usRegions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");

            String completeCountry = divisionID + ": " + divisionName;
            usRegions.add(completeCountry);

        }
        return usRegions;
    }

    public static ObservableList<String> viewUKRegions() throws SQLException {

        ObservableList<String> ukRegions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");

            String completeCountry = divisionID + ": " + divisionName;
            ukRegions.add(completeCountry);

        }
        return ukRegions;
    }
    public static ObservableList<String> viewCanadaRegions() throws SQLException {

        ObservableList<String> canadaRegions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");

            String completeCountry = divisionID + ": " + divisionName;
            canadaRegions.add(completeCountry);

        }
        return canadaRegions;
    }

    public static ObservableList<Integer> getCountry(int divId) throws SQLException {

        ObservableList<Integer> country = FXCollections.observableArrayList();

        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            country.add(countryId);
        }
        return country;
    }

}
