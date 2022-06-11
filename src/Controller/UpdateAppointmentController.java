package Controller;

import Model.Appointment;
import Model.AppointmentQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UpdateAppointmentController implements Initializable {

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
    private ComboBox<String> userid;

    Stage stage;
    Parent scene;


    /** This method populates the form with information from the appointment selected from the dashboard.
     * @param appointment the appointment to be modified.
     */
    public void setAppointment(Appointment appointment) throws SQLException {

        appointmentid.setText(String.valueOf(appointment.getAppointmentId()));
        titleid.setText(appointment.getTitle());
        descriptionid.setText(appointment.getDescription());
        typeid.setText(appointment.getType());
        locationid.setText(appointment.getLocation());
        contactid.setValue(appointment.getContactId());
        Timestamp start = appointment.getStart();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        LocalDate startDate = start.toInstant().atZone(localZoneId).toLocalDate();
        LocalTime startTime = start.toInstant().atZone(localZoneId).toLocalTime();
        starttimeid.setValue(startTime);
        startdateid.setValue(startDate);
        customerid.setValue(appointment.getCustomerId());
        int userId = appointment.getUserId();
        userid.setValue(AppointmentQuery.viewUserName(userId).get(0));
        //userid.setValue(appointment.getUserId());


    }

    /** This event handler automatically selects an end time (one hour after the start time) as soon as the start time is not null */
    public void endSelect(ActionEvent event) {
        if(starttimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime start = starttimeid.getSelectionModel().getSelectedItem();
            LocalTime end = start.plusHours(1);
            System.out.println("Start time: " + start + " - " + "End time: " + end);
            endtimeid.getSelectionModel().select(end);
        }
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
            System.out.println("Start date: " + start + " - " + "End date: " + end);
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

    /** This event handler takes the user back to the dashboard page. */
    @FXML
    public void back(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler saves the modified appointment, after checking there are no conflicts with office hours or other appointments. */
    public void saveButton(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(appointmentid.getText());
        String title = titleid.getText();
        String description = descriptionid.getText();
        String location  = locationid.getText();
        String type = typeid.getText();
        LocalDate startDate = startdateid.getValue();
        LocalDate endDate = enddateid.getValue();
        LocalTime startTime = starttimeid.getValue();
        LocalTime endTime = endtimeid.getValue();
        int customerId = customerid.getValue();
        String userId = userid.getValue();
        userId = userId.split(":")[0];
        int newId = Integer.parseInt(userId);
        int contactId = contactid.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime startLocalZDT = ZonedDateTime.of(startDateTime, localZoneId);
        ZonedDateTime endLocalZDT = ZonedDateTime.of(endDateTime, localZoneId);
        Instant startLocalToUTC = startLocalZDT.toInstant();
        Instant endLocalToUTC = endLocalZDT.toInstant();

        Timestamp sqlDateStart = Timestamp.from(startLocalToUTC);
        Timestamp sqlDateEnd = Timestamp.from(endLocalToUTC);

        ZoneId eastCoastZoneId = ZoneId.of("America/New_York");
        ZonedDateTime startEastCoastZDT = startLocalZDT.withZoneSameInstant(eastCoastZoneId);
        LocalTime eastCoastStartTime = startEastCoastZDT.toLocalTime();

        ObservableList<Appointment> customerAppointments = AppointmentQuery.viewConflictingAppointments(sqlDateStart);
        if(customerAppointments.size() > 0) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("There is already an appointment scheduled for that date/time. Please choose another date/time.");
            noSelectionAlert.showAndWait();
        }

        if(eastCoastStartTime.isBefore(LocalTime.parse("07:59:00")) || eastCoastStartTime.isAfter(LocalTime.parse("20:00:00"))){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("Please choose an appointment time within business hours: 8:00am-10:00pm, Mon-Sun.");
            noSelectionAlert.showAndWait();
        }

        else {
            AppointmentQuery.updateAppointment(id, title, description, location, type, sqlDateStart, sqlDateEnd, customerId, newId, contactId);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
        }
    }

    /** This initializes the page and populates the combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Integer> allContacts = AppointmentQuery.viewAllContacts();
            ObservableList<Integer> allCustomers = AppointmentQuery.viewAllCustomers();
            ObservableList<String> allUsers = AppointmentQuery.viewAllUsers();
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
