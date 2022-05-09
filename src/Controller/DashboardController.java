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
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Appointment> appointmentInventory = FXCollections.observableArrayList();
    private ObservableList<Customer> customerInventory = FXCollections.observableArrayList();

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
    private TableColumn<Appointment, Date> endcol;

    @FXML
    private TableColumn<Appointment, String> locationcol;

    @FXML
    private TableColumn<Customer, String> namecol;

    @FXML
    private TableColumn<Customer, String> phonecol;

    @FXML
    private TableColumn<Customer, String> postalcodecol;

    @FXML
    private TableColumn<Appointment, Date> startcol;

    @FXML
    private TableColumn<Appointment, String> titlecol;

    @FXML
    private TableColumn<Appointment, String> typecol;

    @FXML
    private TableColumn<Appointment, Integer> useridcol;


    /** This is the listener for the radio buttons **/
    public void radioSelect(ActionEvent event) throws SQLException {
        if(monthbuttonid.isSelected()){
            AppointmentQuery.viewThisMonthAppointments();
            ObservableList<Appointment> monthAppointments = AppointmentQuery.viewThisMonthAppointments();
            appointmentstableview.setItems(monthAppointments);
        }
        if(weekbuttonid.isSelected()){
            AppointmentQuery.viewThisWeekAppointments();
            ObservableList<Appointment> weekAppointments = AppointmentQuery.viewThisWeekAppointments();
            appointmentstableview.setItems(weekAppointments);
        }
        if(allbuttonid.isSelected()){
            appointmentstableview.setItems(Customer.getAllAppointments());
        }
    }

    @FXML
    public void addAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_appointment.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This method generates parts table. */
    private void generateAppointmentsTable() {
        appointmentstableview.refresh();

        appointmentInventory.setAll(Inventory.getAllAppointments());

        appointmentstableview.setItems(appointmentInventory);

    }

    /** This method generates products table. */
    /*private void generateCustomersTable() {
        productInventory.setAll(inv.getAllProducts());

        productsTable.setItems(productInventory);
        productsTable.refresh();

    }*/

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


        //generateAppointmentsTable();

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
