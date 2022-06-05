package Model;

import Helper.JDBC;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.TimeZone;

public abstract class AppointmentQuery {

    public static void allAppointments() throws SQLException {
        Inventory.allAppointments.clear();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
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
            Inventory.addAppointment(appointment);
        }
    }

    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static int addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();

        Appointment appointment = new Appointment(title, description, location, type, start, end, customerId, userId, contactId);
        Inventory.addAppointment(appointment);

        return rowsAffected;
    }

    public static int updateAppointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, id);
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

            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        return filteredAppointments;
    }

    public static ObservableList<Integer> viewAllContacts() throws SQLException {

        ObservableList<Integer> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT Contact_ID FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int contactId = rs.getInt("Contact_ID");
            allContacts.add(contactId);

        }
        return allContacts;
    }

    public static ObservableList<Integer> viewAllCustomers() throws SQLException {

        ObservableList<Integer> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int customerId = rs.getInt("Customer_ID");
            allCustomers.add(customerId);
            System.out.println(customerId);
        }
        return allCustomers;
    }

    public static ObservableList<LocalTime> viewStartTimes() {
        ObservableList<LocalTime> localZDTTimes = FXCollections.observableArrayList();
        ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(19, 00);



        while(start.isBefore(end.plusSeconds(1))) {
            startTimes.add(start);
            start = start.plusMinutes(15);
        }

        for(int i =0 ; i < startTimes.size(); i++) {

            LocalTime newStart = startTimes.get(i);
            LocalDate todayDate = LocalDate.now();
            ZoneId eastCoastZoneId = ZoneId.of("America/New_York");
            ZonedDateTime eastCoastZDT = ZonedDateTime.of(todayDate, newStart, eastCoastZoneId);

            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime eastCoasttoLocalZDT = eastCoastZDT.withZoneSameInstant(localZoneId);
            LocalTime localAppointment = eastCoasttoLocalZDT.toLocalTime();
            localZDTTimes.add(localAppointment);
        }
        return localZDTTimes;
    }

    public static ObservableList<LocalTime> viewEndTimes() {

        ObservableList<LocalTime> localZDTTimes = FXCollections.observableArrayList();
        ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(9,0);
        LocalTime end = LocalTime.of(20, 00);

        while(start.isBefore(end.plusSeconds(1))) {
            endTimes.add(start);
            start = start.plusMinutes(15);
        }

        for(int i =0 ; i < endTimes.size(); i++) {

            LocalTime newEnd = endTimes.get(i);
            LocalDate todayDate = LocalDate.now();
            ZoneId eastCoastZoneId = ZoneId.of("America/New_York");
            ZonedDateTime eastCoastZDT = ZonedDateTime.of(todayDate, newEnd, eastCoastZoneId);

            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime eastCoasttoLocalZDT = eastCoastZDT.withZoneSameInstant(localZoneId);
            LocalTime localAppointment = eastCoasttoLocalZDT.toLocalTime();
            localZDTTimes.add(localAppointment);
        }
        return localZDTTimes;
    }

    public static ObservableList<Integer> viewAllUsers() throws SQLException {

        ObservableList<Integer> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            allUsers.add(userId);
            System.out.println(userId);
        }
        return allUsers;
    }


    public static ObservableList<Appointment> viewThisWeekAppointments() throws SQLException {

        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start, 0) = YEARWeek(now(), 0)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        return filteredAppointments;
    }


    public static ObservableList<Appointment> viewTodayAppointments() throws SQLException {

        allAppointments();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Date(Start) = curdate()";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        return filteredAppointments;
    }

    public static ObservableList<Appointment> viewConflictingAppointments(Timestamp start) throws SQLException {

        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Start = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, start);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");

            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    customerAppointments.add(appointment);
                }
            }
        }
        return customerAppointments;
    }



}






