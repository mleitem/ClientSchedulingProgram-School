package Controller;

import Helper.JDBC;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> appointmentid1;

    @FXML
    private TableColumn<Appointment, Integer> appointmentid2;

    @FXML
    private TableColumn<Appointment, Integer> appointmentid3;

    @FXML
    private TableColumn<Appointment, String> contact1;

    @FXML
    private TableColumn<Appointment, String> contact2;

    @FXML
    private TableColumn<Appointment, String> contact3;

    @FXML
    private TableView<Appointment> contactappointmentstable;

    @FXML
    private TableView<Appointment> locationappointmentstable;

    @FXML
    private TableColumn<Contact, String> contactemail;

    @FXML
    private TableColumn<Contact, Integer> contactid;

    @FXML
    private TableColumn<Contact, String> contactname;

    @FXML
    private TableView<Contact> contactstable;

    @FXML
    private TableColumn<Appointment, Integer> customerid1;

    @FXML
    private TableColumn<Appointment, Integer> customerid2;

    @FXML
    private TableColumn<Appointment, Integer> customerid3;

    @FXML
    private TableColumn<Appointment, String> description1;

    @FXML
    private TableColumn<Appointment, String> description2;

    @FXML
    private TableColumn<Appointment, String> description3;

    @FXML
    private TableColumn<Appointment, Timestamp> end1;

    @FXML
    private TableColumn<Appointment, Timestamp> end2;

    @FXML
    private TableColumn<Appointment, Timestamp> end3;

    @FXML
    private TableColumn<Appointment, String> location1;

    @FXML
    private TableColumn<Appointment, String> location2;

    @FXML
    private TableColumn<Appointment, String> location3;

    @FXML
    private TableView<Appointment> monthappointmentstable;

    @FXML
    private TableColumn<Appointment, Timestamp> start1;

    @FXML
    private TableColumn<Appointment, Timestamp> start2;

    @FXML
    private TableColumn<Appointment, Timestamp> start3;

    @FXML
    private TableColumn<Appointment, String> title1;

    @FXML
    private TableColumn<Appointment, String> title2;

    @FXML
    private TableColumn<Appointment, String> title3;

    @FXML
    private TableColumn<Appointment, String> type1;

    @FXML
    private TableColumn<Appointment, String> type2;

    @FXML
    private TableColumn<Appointment, String> type3;

    @FXML
    private TableColumn<Appointment, Integer> userid1;

    @FXML
    private TableColumn<Appointment, Integer> userid2;

    @FXML
    private TableColumn<Appointment, Integer> userid3;

    @FXML
    private Label totalid;

    @FXML
    private Label totalid3;

    @FXML
    private Label totalcontactappointments;

    @FXML
    private ComboBox<String> typecomboid;

    @FXML
    private ComboBox<String> monthcomboid;

    @FXML
    private ComboBox<String> locationcomboid;

    /** This event handler takes the user entry to filter down the appointments to those of the same month and type
     * as the choices of the user. */
    @FXML
    public void submitButton1(ActionEvent event) throws IOException, SQLException {
        String type = typecomboid.getValue();
        String month = monthcomboid.getValue();

        ObservableList<Appointment> monthTypeAppointments = ReportQuery.totalAppointmentsByTypeMonth(type, month);

        if(monthTypeAppointments.size() > 0) {
            monthappointmentstable.setItems(monthTypeAppointments);
            totalid.setText(String.valueOf(monthTypeAppointments.size()));
        }
        else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("There are no matches for your search. Please try again.");
            noSelectionAlert.showAndWait();
            monthappointmentstable.setItems(Inventory.getAllAppointments());
            totalid.setText(String.valueOf(Inventory.getAllAppointments().size()));
        }

    }

    /** This event handler filters appointments down to show only the appointments of the contact the user selects. */
    @FXML
    public void submitButton2(ActionEvent event) throws IOException, SQLException {
        Contact contact = contactstable.getSelectionModel().getSelectedItem();

        int contactId = contact.getContactId();
        ObservableList<Appointment> contactAppointments = ReportQuery.totalAppointmentsByContact(contactId);
        if(contactAppointments.size() > 0) {
            contactappointmentstable.setItems(contactAppointments);
            totalcontactappointments.setText(String.valueOf(contactAppointments.size()));
        }
        else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("There are no matches for your search. Please try again.");
            noSelectionAlert.showAndWait();
            contactappointmentstable.setItems(Inventory.getAllAppointments());
            totalcontactappointments.setText(String.valueOf(Inventory.getAllAppointments().size()));
        }

    }


    /** This event handler filters appointments down to show those that have locations matching the location the user chose. */
    @FXML
    public void submitButton3(ActionEvent event) throws IOException, SQLException {
        String location = locationcomboid.getValue();


        ObservableList<Appointment> locationAppointments = ReportQuery.totalAppointmentsByLocation(location);

        if(locationAppointments.size() > 0) {
            locationappointmentstable.setItems(locationAppointments);
            totalid3.setText(String.valueOf(locationAppointments.size()));
        }
        else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("There are no matches for your search. Please try again.");
            noSelectionAlert.showAndWait();
            locationappointmentstable.setItems(Inventory.getAllAppointments());
            totalid3.setText(String.valueOf(Inventory.getAllAppointments().size()));
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

    /** This initializes the page and populates all of the tables/combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ReportQuery.viewAllContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        monthcomboid.setItems(months);
        try {
            typecomboid.setItems(ReportQuery.viewAppointmentTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthappointmentstable.setItems(Inventory.getAllAppointments());
        appointmentid1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title1.setCellValueFactory(new PropertyValueFactory<>("title"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        location1.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact1.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        start1.setCellValueFactory(new PropertyValueFactory<>("start"));
        end1.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userid1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalid.setText(String.valueOf(Inventory.getAllAppointments().size()));

        contactstable.setItems(Inventory.getAllContacts());

        contactid.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactname.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactemail.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));


        contactappointmentstable.setItems(Inventory.getAllAppointments());
        appointmentid2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        description2.setCellValueFactory(new PropertyValueFactory<>("description"));
        location2.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact2.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type2.setCellValueFactory(new PropertyValueFactory<>("type"));
        start2.setCellValueFactory(new PropertyValueFactory<>("start"));
        end2.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userid2.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalcontactappointments.setText(String.valueOf(Inventory.getAllAppointments().size()));


        locationappointmentstable.setItems(Inventory.getAllAppointments());
        appointmentid3.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title3.setCellValueFactory(new PropertyValueFactory<>("title"));
        description3.setCellValueFactory(new PropertyValueFactory<>("description"));
        location3.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact3.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type3.setCellValueFactory(new PropertyValueFactory<>("type"));
        start3.setCellValueFactory(new PropertyValueFactory<>("start"));
        end3.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid3.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userid3.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalid3.setText(String.valueOf(Inventory.getAllAppointments().size()));

        try {
            locationcomboid.setItems(ReportQuery.viewAppointmentLocations());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
