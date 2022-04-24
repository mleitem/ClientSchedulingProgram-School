package Helper;

import java.sql.PreparedStatement;
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

}
