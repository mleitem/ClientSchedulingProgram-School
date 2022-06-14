package Model;

import Helper.GeneralInterface;
import Helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public abstract class CustomerQuery implements GeneralInterface {

    /** This query adds a new customer to the customer table.
     * @param customerName is the name of the customer.
     * @param customerAddress is the address of the customer.
     * @param postalCode is the postal code of the customer.
     * @param phone is the phone number of the customer.
     * @param divId is the division ID for the customer.
     * @return rows affected by the addition.
     */
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

    /** This query updates a customer in the customers table.
     * @param id is the customer to be modified.
     * @param name is the name of the customer.
     * @param address is the address of the customer.
     * @param postal is the postal code of the customer.
     * @param phone is the phone number of the customer.
     * @param divId is the division ID for the customer.
     * @return rows affected by the update.
     * @throws SQLException
     */
    public static int updateCustomer(int id, String name, String address, String postal, String phone, int divId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setInt(5, divId);
        ps.setInt(6, id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /** This query deletes a customer from the customers table.
     * @param customerId is the customer to be deleted.
     * @return rows affected by deletion.
     */
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /** This query deletes all appointments that belong to a customer.
     * @param customerId is the customer whose appointments will be deleted.
     * @return rows affected by deletion.
     */
    public static int deleteCustomerAppointments(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /** This query gets customers from the customers table and adds them to the list of all customers.*/
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

            Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divId);
            Inventory.addCustomer(customer);
        }
    }

    /** This query gets all first level divisions that match the provided divID.
     * @param divId the ID used to find a corresponding first-level division.
     * @return an observable list of first-level divisions whose country ID matches divID.
     * @throws SQLException
     */
    public static ObservableList<String> getFirstLevel(int divId) throws SQLException {

        ObservableList<String> firstLevel = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            String divName = rs.getString("Division");

            String completeDivision = divisionId + ": " + divName;
            firstLevel.add(completeDivision);
        }
        return firstLevel;
    }

    /** This query pulls everything from the countries table.
     * @return an observable list of all countries.
     * @throws SQLException
     */
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

    /** This query pulls the first-level divisions that belong to the U.S.
     * @return an observable list of U.S. first-level divisions.
     */
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

    /** This query pulls the first-level divisions that belong to the U.K.
     * @return an observable list of U.K. first-level divisions.
     */
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

    /** This query pulls the first-level divisions that belong to Canada.
     * @return an observable list of Canadian first-level divisions.
     */
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



    /** This query gets the country that matches the provided ID.
     * @param divId the ID used to find a corresponding country.
     *
     *
     * LAMBDA EXPRESSION JUSTIFICATION: I chose to implement a lambda expression below to quickly concatenate
     * the country ID and name for the observable list.
     *
     *  @return an observable list of countries that match divId.
     */
    public static ObservableList<String> getCountry(int divId) throws SQLException {

        ObservableList<String> country = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String name = null;
            if(countryId == 1){
                name = "U.S.";
            }
            if(countryId == 2){
                name = "UK";
            }
            if(countryId == 3){
                name = "Canada";
            }

            GeneralInterface country1 = (String a, String b) -> {
                String fullName = a + ": " + b;
                return fullName;
            };

           String stringId = String.valueOf(countryId);
           country.add(country1.completeCountry(stringId, name));
        }
        return country;
    }

}
