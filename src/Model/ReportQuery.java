package Model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class ReportQuery {

    /** This query pulls everything from the contacts table. */
    public static void viewAllContacts() throws SQLException {

        Inventory.getAllContacts().clear();

        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactId, contactName, contactEmail);
            Inventory.addContact(contact);
        }
        return;
    }

    /** This query pulls all unique appointment types from the appointments table.
     * @return an observable list of appointment types.
     */
    public static ObservableList<String> viewAppointmentTypes() throws SQLException {

        ObservableList<String> types = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String appointmentType = rs.getString("Type");
            types.add(appointmentType);
        }

        return types;
    }

    /** This query pulls all unique appointment locations from the appointments table.
     * @return an observable list of appointment locations.
     */
    public static ObservableList<String> viewAppointmentLocations() throws SQLException {

        ObservableList<String> locations = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT Location FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String appointmentLocation = rs.getString("Location");
            locations.add(appointmentLocation);
        }

        return locations;
    }

    /** This query pulls all appointments that are associated with a specific contact.
     * @param id is the contact with which the appointments are associated.
     * @return an observable list of appointments.
     */
    public static ObservableList<Appointment> totalAppointmentsByContact(int id) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");

            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    /** This query pulls all appointments associated with a specific location.
     * @param location is the location with which the appointments are associated.
     * @return an observable list of appointments.
     */
    public static ObservableList<Appointment> totalAppointmentsByLocation(String location) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Location = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, location);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");

            for (int i = 0; i < Inventory.allAppointments.size(); ++i) {
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    System.out.println(appointmentId);
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    /** This query pulls all appointments that are of a certain type and have a start date in a certain month.
     * @param type the type of appointment.
     * @param month the month of the appointment's start date.
     * @return an observable list of appointments.
     */
    public static ObservableList<Appointment> totalAppointmentsByTypeMonth(String type, String month) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ? AND YEAR(Start) = YEAR(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setString(2, month);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");

            for(int i = 0; i < Inventory.allAppointments.size(); ++i){
                appointment = Inventory.allAppointments.get(i);
                if (appointment.getAppointmentId() == appointmentId) {
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

}
