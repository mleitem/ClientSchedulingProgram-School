package Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
    }

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

        appointmentstableview.setItems(Customer.getAllAppointments());
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
