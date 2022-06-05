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
    private ComboBox<Integer> userid;

    Stage stage;
    Parent scene;


    public void setAppointment(Appointment appointment){

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
        userid.setValue(appointment.getUserId());

    }

    public void endSelect(ActionEvent event) {

        if(starttimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime start = starttimeid.getSelectionModel().getSelectedItem();
            LocalTime end = start.plusHours(1);
            System.out.println("Start time: " + start + " - " + "End time: " + end);
            endtimeid.getSelectionModel().select(end);
        }
    }

    public void startSelect(ActionEvent event) {

        if(endtimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime end = endtimeid.getSelectionModel().getSelectedItem();
            LocalTime start = end.minusHours(1);
            starttimeid.setValue(start);
        }
    }

    public void endDate(ActionEvent event) {
        if(startdateid.getValue() != null){
            LocalDate start = startdateid.getValue();
            LocalDate end = start;
            System.out.println("Start date: " + start + " - " + "End date: " + end);
            enddateid.setValue(end);

        }

    }

    public void startDate(ActionEvent event){
        if(enddateid.getValue() != null) {
            LocalDate end = enddateid.getValue();
            LocalDate start = end;
            startdateid.setValue(start);
        }
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

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
        int userId = userid.getValue();
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
            AppointmentQuery.updateAppointment(id, title, description, location, type, sqlDateStart, sqlDateEnd, customerId, userId, contactId);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
        }
    }
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
