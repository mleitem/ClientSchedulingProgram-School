package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Customer, String> addresscol;

    @FXML
    private ToggleGroup appointmentgroup;

    @FXML
    private RadioButton monthbuttonid;

    @FXML
    private RadioButton weekbuttonid;

    @FXML
    private RadioButton allbuttonid;

    @FXML
    private TableColumn<Appointment, Integer> appointmentidcol;

    @FXML
    private TableView<Appointment> appointmentstableview;

    @FXML
    private TableColumn<Appointment, String> contactcol;

    @FXML
    private TableColumn<Appointment, Integer> customeridcol;

    @FXML
    private TableColumn<Customer, Integer> customeridcol1;

    @FXML
    private TableView<Customer> customertableview;

    @FXML
    private TableColumn<Appointment, String> descriptioncol;

    @FXML
    private TableColumn<Customer, Integer> dividcol;

    @FXML
    private TableColumn<Appointment, Timestamp> endcol;

    @FXML
    private TableColumn<Appointment, String> locationcol;

    @FXML
    private TableColumn<Customer, String> namecol;

    @FXML
    private TableColumn<Customer, String> phonecol;

    @FXML
    private TableColumn<Customer, String> postalcodecol;

    @FXML
    private TableColumn<Appointment, Timestamp> startcol;

    @FXML
    private TableColumn<Appointment, String> titlecol;

    @FXML
    private TableColumn<Appointment, String> typecol;

    @FXML
    private TableColumn<Appointment, Integer> useridcol;


    /** This is the listener for the radio buttons - filters appointments down by week or month. **/
    public void radioSelect(ActionEvent event) throws SQLException {
        if (monthbuttonid.isSelected()) {
            ObservableList<Appointment> monthAppointments = AppointmentQuery.viewThisMonthAppointments();
            appointmentstableview.setItems(monthAppointments);
        }
        if (weekbuttonid.isSelected()) {
            ObservableList<Appointment> weekAppointments = AppointmentQuery.viewThisWeekAppointments();
            appointmentstableview.setItems(weekAppointments);
        }
        if (allbuttonid.isSelected()) {
            appointmentstableview.setItems(Inventory.getAllAppointments());
        }
    }

    /** This event handler takes the user to the "Add Appointment" page. */
    @FXML
    public void addAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_appointment.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler takes the user to the "Update Appointment" page to update the appointment selected from the table. */
    @FXML
    public void updateAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_appointment.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();

        UpdateAppointmentController controller = loader.getController();
        Appointment appointment = appointmentstableview.getSelectionModel().getSelectedItem();
        controller.setAppointment(appointment);
    }

    /** This event handler deletes the selected appointment, after confirmation from the user. */
    @FXML
    public void deleteAppointment(ActionEvent event) throws SQLException {
        if (appointmentstableview.getSelectionModel().isEmpty()) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("Please choose an appointment to delete.");
            noSelectionAlert.showAndWait();
        } else {
            Appointment appointment = appointmentstableview.getSelectionModel().getSelectedItem();
            int id = appointment.getAppointmentId();
            String type = appointment.getType();
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wand to delete Appointment ID: " + id + " - " + type);
            confirmation.setTitle("Delete Confirmation");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentQuery.deleteAppointment(id);
                Inventory.deleteAppointment(appointment);

                Alert deleteConfirmation = new Alert(Alert.AlertType.INFORMATION);
                deleteConfirmation.setTitle("Confirmation");
                deleteConfirmation.setContentText("Appointment ID: " + id + "    |    " + "Type: " + type + " deleted.");
                deleteConfirmation.showAndWait();

                if (allbuttonid.isSelected()) {
                    appointmentstableview.setItems(Inventory.getAllAppointments());
                }
                if (monthbuttonid.isSelected()) {
                    appointmentstableview.setItems(AppointmentQuery.viewThisMonthAppointments());
                }
                if (weekbuttonid.isSelected()) {
                    appointmentstableview.setItems(AppointmentQuery.viewThisWeekAppointments());
                }
            }

        }
    }

    /** This event handler takes the user to the "Add Customer" page. */
    @FXML
    public void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_customer.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler takes the user to the "Update Customer" page to update the customer selected from the table.*/
    @FXML
    public void updateCustomer(ActionEvent event) throws IOException, SQLException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_customer.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();

        UpdateCustomerController controller = loader.getController();
        Customer customer = customertableview.getSelectionModel().getSelectedItem();
        controller.setCustomer(customer);
    }

    /** This event handler deletes the customer's appointments and then the customer, after an initial confirmation from the user. */
    @FXML
    public void deleteCustomer(ActionEvent event) throws SQLException, IOException {

        if (customertableview.getSelectionModel().isEmpty()) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("Please choose a customer to delete.");
            noSelectionAlert.showAndWait();
        }

        else {
            Customer customer = customertableview.getSelectionModel().getSelectedItem();
            int id = customer.getId();
            String name = customer.getName();
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wand to delete Customer ID: " + id + " - " + name);
            confirmation.setTitle("Delete Confirmation");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomerQuery.deleteCustomerAppointments(id);
                CustomerQuery.deleteCustomer(id);

                Alert deleteConfirmation = new Alert(Alert.AlertType.INFORMATION);
                deleteConfirmation.setTitle("Confirmation");
                deleteConfirmation.setContentText("Customer ID: " + id + "   /   " + "Name: " + name + " deleted.");
                deleteConfirmation.showAndWait();
            }
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler takes the user to the "Reports" page. */
    @FXML
    public void viewReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Reports.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This initializes the page and sets the content of the tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CustomerQuery.allCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            AppointmentQuery.allAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        customertableview.setItems(Inventory.getAllCustomers());
        customeridcol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalcodecol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dividcol.setCellValueFactory(new PropertyValueFactory<>("divId"));

        appointmentstableview.setItems(Inventory.getAllAppointments());
        appointmentidcol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationcol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactcol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startcol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endcol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customeridcol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        useridcol.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}
