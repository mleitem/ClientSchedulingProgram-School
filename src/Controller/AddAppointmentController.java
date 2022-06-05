package Controller;

import Model.Appointment;
import Model.AppointmentQuery;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentid;

    @FXML
    private ComboBox<Integer> contactid;

    @FXML
    private ComboBox<Integer> customerid;

    @FXML
    private TextField descriptionid;

    @FXML
    private DatePicker enddateid;

    @FXML
    private ComboBox<LocalTime> endtimeid;

    @FXML
    private TextField locationid;

    @FXML
    private DatePicker startdateid;

    @FXML
    private ComboBox<LocalTime> starttimeid;

    @FXML
    private TextField titleid;

    @FXML
    private TextField typeid;

    @FXML
    private ComboBox<Integer> userid;


    /** This event handler automatically selects an end time (one hour after the start time) as soon as the start time is not null */
    public void endSelect(ActionEvent event) {

        if(starttimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime start = starttimeid.getSelectionModel().getSelectedItem();
            LocalTime end = start.plusHours(1);
            System.out.println("Start time: " + start + " - " + "End time: " + end);
            endtimeid.getSelectionModel().select(end);
        }
    }

    /** This event handler takes the user back to the dashboard page. */
    @FXML
    public void back(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler automatically selects a start time (one hour before the end time) as soon as the end time is not null */
    public void startSelect(ActionEvent event) {

        if(endtimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime end = endtimeid.getSelectionModel().getSelectedItem();
            LocalTime start = end.minusHours(1);
            starttimeid.setValue(start);
        }
    }

    /** This event handler automatically selects an end date (same date as start) as soon as the start date is not null */
    public void endDate(ActionEvent event) {
        if(startdateid.getValue() != null){
            LocalDate start = startdateid.getValue();
            LocalDate end = start;
            enddateid.setValue(end);

        }

    }

    /** This event handler automatically selects a start date (same date as start) as soon as the end date is not null */
    public void startDate(ActionEvent event){
        if(enddateid.getValue() != null) {
            LocalDate end = enddateid.getValue();
            LocalDate start = end;
            startdateid.setValue(start);
        }
    }

    /** This event handler gets the data entered by the user and creates a new appointment as long as the start time is within
     * business hours and there are no appointments scheduled to start at the same time. */
    public void submitAppointment(ActionEvent event) throws SQLException, IOException {

        String title = titleid.getText();
        String description = descriptionid.getText();
        String location = locationid.getText();
        String type = typeid.getText();
        LocalDate startDate = startdateid.getValue();
        LocalDate endDate = enddateid.getValue();
        LocalTime startTime = starttimeid.getValue();
        LocalTime endTime = endtimeid.getValue();
        int customerId = customerid.getValue();
        int userId = userid.getValue();
        int contactId = contactid.getValue();

        // Create local ZonedDateTime for start/end
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime startLocalZDT = ZonedDateTime.of(startDateTime, localZoneId);
        ZonedDateTime endLocalZDT = ZonedDateTime.of(endDateTime, localZoneId);
        Instant startLocalToUTC = startLocalZDT.toInstant();
        Instant endLocalToUTC = endLocalZDT.toInstant();

        Timestamp sqlDateEnd = Timestamp.from(endLocalToUTC);
        Timestamp sqlDateStart = Timestamp.from(startLocalToUTC);

        ZoneId eastCoastZoneId = ZoneId.of("America/New_York");
        ZonedDateTime startEastCoastZDT = startLocalZDT.withZoneSameInstant(eastCoastZoneId);
        LocalTime eastCoastStartTime = startEastCoastZDT.toLocalTime();


        // Create an observable list of all appointments that start at the same date/time the user requested.
        // If the list contains any appointments, an error message is triggered.
        ObservableList<Appointment> customerAppointments = AppointmentQuery.viewConflictingAppointments(sqlDateStart);
        if(customerAppointments.size() > 0) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("There is already an appointment scheduled for that date/time. Please choose another date/time.");
            noSelectionAlert.showAndWait();
        }

        // Compare the converted start time to the business hours. If there is a conflict, an error message is triggered.
        if(eastCoastStartTime.isBefore(LocalTime.parse("07:59:00")) || eastCoastStartTime.isAfter(LocalTime.parse("20:00:00"))){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("Please choose an appointment time within business hours: 8:00am-10:00pm, Mon-Sun.");
            noSelectionAlert.showAndWait();
        }

        else {
            AppointmentQuery.addAppointment(title, description, location, type, sqlDateStart, sqlDateEnd, customerId, userId, contactId);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
        }
    }

    /** This initializes the page - AppointmentQuery queries are called to populate observable lists for the combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Integer> allContacts = AppointmentQuery.viewAllContacts();
            ObservableList<Integer> allCustomers = AppointmentQuery.viewAllCustomers();
            ObservableList<Integer> allUsers = AppointmentQuery.viewAllUsers();
            ObservableList<LocalTime> startTimes = AppointmentQuery.viewStartTimes();
            ObservableList<LocalTime> endTimes = AppointmentQuery.viewEndTimes();
            contactid.setItems(allContacts);
            customerid.setItems(allCustomers);
            userid.setItems(allUsers);
            starttimeid.setItems(startTimes);
            endtimeid.setItems(endTimes);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}
