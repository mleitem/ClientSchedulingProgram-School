package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private TextField appointmentid;

    @FXML
    private ChoiceBox<?> contactid;

    @FXML
    private ChoiceBox<?> customerid;

    @FXML
    private TextField descriptionid;

    @FXML
    private DatePicker enddateid;

    @FXML
    private ChoiceBox<?> endtimeid;

    @FXML
    private TextField locationid;

    @FXML
    private DatePicker startdateid;

    @FXML
    private ChoiceBox<?> starttimeid;

    @FXML
    private TextField titleid;

    @FXML
    private TextField typeid;

    @FXML
    private ChoiceBox<?> userid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
