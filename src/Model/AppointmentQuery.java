package Model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class AppointmentQuery {

    public static void allAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Date start = rs.getDate("Start");
            Date end = rs.getDate("End");
            int customerId1 = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            /*System.out.print(customerId + " | ");
            System.out.print(customerName + " | ");
            System.out.print(address + " | ");
            System.out.print(postalCode + " | ");
            System.out.print(phone + " | ");
            System.out.print(divId + "\n");*/
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId1, userId, contactId);
            Customer.addAppointment(appointment);
        }
    }

    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static int addAppointment(String title, String description, String location, String type, Date start, Date end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setDate(5, (java.sql.Date) start);
        ps.setDate(6, (java.sql.Date) end);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static ObservableList<Appointment> viewThisMonthAppointments() throws SQLException {

        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE YEAR(Start) = YEAR(now()) AND MONTH(Start) = MONTH(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            /*String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Date start = rs.getDate("Start");
            Date end = rs.getDate("End");
            int customerId1 = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");*/

            for(int i = 0; i < Customer.associatedAppointments.size(); ++i){
                appointment = Customer.associatedAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        return filteredAppointments;
    }

    public static void viewNextMonthAppointments() throws SQLException {

        String sql = "SELECT * FROM appointments WHERE YEAR(Start) = YEAR(now()) AND MONTH(Start) = MONTH(now() + INTERVAL 1 MONTH)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String title = rs.getString("Title");
            System.out.println(title);
        }
    }

    public static void viewLastMonthAppointments() throws SQLException {

        String sql = "SELECT * FROM appointments WHERE YEAR(Start) = YEAR(now()) AND MONTH(Start) = MONTH(now() - INTERVAL 1 MONTH)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String title = rs.getString("Title");
            System.out.println(title);
        }
    }

    public static void viewNextWeekAppointments() throws SQLException {

        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(now() + INTERVAL 1 WEEK)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String title = rs.getString("Title");
            System.out.println(title);

        }
    }

    public static void viewLastWeekAppointments() throws SQLException {

        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWeek(now() - INTERVAL 1 WEEK)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String title = rs.getString("Title");
            System.out.println(title);
        }
    }

    public static ObservableList<Appointment> viewThisWeekAppointments() throws SQLException {

        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start, 0) = YEARWeek(now(), 0)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            for(int i = 0; i < Customer.associatedAppointments.size(); ++i){
                appointment = Customer.associatedAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        return filteredAppointments;
    }



}






