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

    public static ObservableList<Appointment> totalAppointmentsByType(String type) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
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

    public static ObservableList<Appointment> totalAppointmentsByMonth(String month) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE YEAR(Start) = YEAR(now()) AND MONTHNAME(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
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

    public static ObservableList<String> viewAppointmentTypes() throws SQLException {

        ObservableList<String> types = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String appointmentType = rs.getString("Type");
            types.add(appointmentType);
            System.out.println(appointmentType);
        }

        return types;
    }

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

    /*public static ObservableList<Appointment> totalByType() throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Appointment appointment = null;
        String sql = "SELECT Type, Start FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");

            LocalDateTime ldt = start.toLocalDateTime();
            String appointmentMonth = ldt.getMonth().toString();
            int appointmentYear = ldt.getYear();
            int count = 1;

            for(int i = 0; i < Inventory.getAllAppointments().size(); i++) {
                appointment = Inventory.allAppointments.get(i);
                LocalDateTime appointmentStart = (appointment.getStart()).toLocalDateTime();
                String compMonth = appointmentStart.getMonth().toString();
                int compYear = appointmentStart.getYear();
                if(appointment.getType().equalsIgnoreCase(type) && compMonth.equals(appointmentMonth) && compYear == appointmentYear) {
                    count = count + 1;
                }
            }
            Appointment addAppointment = new Appointment(type, appointmentMonth, appointmentYear, count);
            appointments.add(addAppointment);
        }
        return appointments;
    }*/


}
