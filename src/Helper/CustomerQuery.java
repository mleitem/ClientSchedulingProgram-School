package Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery {

    public static int insert(String customerName, String customerAddress, String postalCode, String phone, int divId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static int updateName(int customerId, String customerName) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setInt(2,customerId);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    // Need update address, phone, etc.

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static void select() throws SQLException {
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
            System.out.print(customerId + " | ");
            System.out.print(customerName + " | ");
            System.out.print(address + " | ");
            System.out.print(postalCode + " | ");
            System.out.print(phone + " | ");
            System.out.print(divId + "\n");
        }
    }

    public static void select(int divId) throws SQLException {
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
    }

}
