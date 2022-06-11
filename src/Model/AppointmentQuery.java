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

    /** This query pulls all appointments from the appointments table. */
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
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId1, userId, contactId);
            Inventory.addAppointment(appointment);
        }
    }

    /** This query deletes an appointment from the appointments table.
     * @param appointmentId is the appointment to be deleted
     */
    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /** This query adds an appointment to the appointments table.
     * @param title is the appointment's title.
     * @param description is the appointment's description.
     * @param location is the appointment's location.
     * @param type is the appointment's type.
     * @param start is the appointment's start date/time.
     * @param end is the appointment's end date/time
     * @param customerId is the customer associated with the appointment.
     * @param userId is the user booking the appointment.
     */
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

    /** This query updates an appointment in the appointments table.
     * @param id is the appointment to be modified.
     * @param title is the title of the appointment.
     * @param description is the description of the appointment.
     * @param location is the location of the appointment.
     * @param type is the type of appointment.
     * @param start is the start date/time of the appointment.
     * @param end is the end date/time of the appointment.
     * @param customerId is the customer associated with the appointment.
     * @param userId is the user booking the appointment.
     * @param contactId is the contact associated with the appointment.
     */
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

    /** This query pulls appointments that have a start date in the current month.
     * @return an observable list with appointments that have a start date this month.
     */
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

    /** This query pulls all of the contacts from the contacts table.
     * @return an observable list containing all contacts.
     */
    public static ObservableList<String> viewAllContacts() throws SQLException {

        ObservableList<String> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int contactId = rs.getInt("Contact_ID");
            String newId = String.valueOf(contactId);
            String contactName = rs.getString("Contact_Name");
            String fullInfo = newId + ": " + contactName;
            allContacts.add(fullInfo);

        }
        return allContacts;
    }

    /** This query pulls all customers from the customers table.
     * @return an observable list containing all customers.
     */
    public static ObservableList<String> viewAllCustomers() throws SQLException {

        ObservableList<String> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String id = String.valueOf(customerId);
            String fullInfo = id + ": " + customerName;
            allCustomers.add(fullInfo);
        }
        return allCustomers;
    }

    /** This query puts together a list of start times based on the company's business hours and the user's time zone.
     * @return an observable list of start times that have been converted to the user's time zone.
     */
    public static ObservableList<LocalTime> viewStartTimes() {
        ObservableList<LocalTime> localZDTTimes = FXCollections.observableArrayList();
        ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8,00);
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

    /** This query puts together a list of end times based on the company's hours and the user's time zone.
     * @return an observable list of end times that have been converted to the user's time zone.
     */
    public static ObservableList<LocalTime> viewEndTimes() {

        ObservableList<LocalTime> localZDTTimes = FXCollections.observableArrayList();
        ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(9,00);
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

    /** This query pulls all users from the user table.
     * @return an observable list of all users.
     */
    public static ObservableList<String> viewAllUsers() throws SQLException {

        ObservableList<String> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");

            String id = String.valueOf(userId);
            String fullInfo = id + ": " + userName;

            allUsers.add(fullInfo);
        }
        return allUsers;
    }

    /**
     *  This query pulls a user's name, based on the id entered. It concatenates the id with the name for the combo box
     *  on the UpdateAppointment page.
     * @param id the user's id
     * @return an observable list of users' names and corresponding id's
     */
    public static ObservableList<String> viewUserName(int id) throws SQLException {

        ObservableList<String> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");

            String newId = String.valueOf(userId);
            String fullInfo = newId + ": " + userName;

            allUsers.add(fullInfo);
        }
        return allUsers;
    }

    /** This query pulls a customer's name, based on the id entered. It concatenates the id with the name for the combo box
     *  on the UpdateAppointment page.
     * @param id the customer's id
     * @return an observable list of customers' names and corresponding id's
     */
    public static ObservableList<String> viewCustomerName(int id) throws SQLException {

        ObservableList<String> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int custId = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");

            String newId = String.valueOf(custId);
            String fullInfo = newId + ": " + custName;

            allUsers.add(fullInfo);
        }
        return allUsers;
    }

    /** This query pulls a contact's name, based on the id entered. It concatenates the id with the name for the combo box
     *  on the UpdateAppointment page.
     * @param id the contact's id
     * @return an observable list of contacts' names and corresponding id's
     */
    public static ObservableList<String> viewContactName(int id) throws SQLException {

        ObservableList<String> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int conId = rs.getInt("Contact_ID");
            String conName = rs.getString("Contact_Name");

            String newId = String.valueOf(conId);
            String fullInfo = newId + ": " + conName;

            allUsers.add(fullInfo);
        }
        return allUsers;
    }

    /** This query pulls appointments that have start dates in current week.
     * @return an observable list of appointments starting this week.
     */
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

    /** This query pulls appointments that have the same start date as the current date.
     * @return an observable list of all appointments that have a start date the same as the current date.
     */
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

    /** This query pulls appointments that have conflicting start date/times
     * @param start is the timestamp being compared to appointments in the appointments table.
     * @return an observable list of appointments that have conflicting start date/times.
     */
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

    /** This query pulls appointments that have conflicting start date/times with appointments other than
     * the appointment being updated.
     * @param start is the timestamp being compared to appointments in the appointments table.
     * @return an observable list of appointments that have conflicting start date/times.
     */
    public static ObservableList<Appointment> viewConflictingAppointmentsUpdate(Timestamp start, int id) throws SQLException {

        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Start = ? AND APPOINTMENT_ID <> ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, start);
        ps.setInt(2, id);
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






