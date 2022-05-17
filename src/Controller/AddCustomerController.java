package Controller;

import Model.AppointmentQuery;
import Model.CustomerQuery;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private TextField addressid;

    @FXML
    private ComboBox<String> countryid;

    @FXML
    private TextField customerid;

    @FXML
    private TextField nameid;

    @FXML
    private TextField phoneid;

    @FXML
    private TextField postalcodeid;

    @FXML
    private ComboBox<String> stateid;

    public void regionFilter(ActionEvent event) throws SQLException {
        if(countryid.getValue() != null){
            String id = countryid.getValue();
            if(id.contains("1")){
                ObservableList<String> usRegions = CustomerQuery.viewUSRegions();
                stateid.setItems(usRegions);
                stateid.setValue("1: Alabama");
            }
            if(id.contains("2")){
                ObservableList<String> ukRegions = CustomerQuery.viewUKRegions();
                stateid.setItems(ukRegions);
                stateid.setValue("101: England");
            }
            if(id.contains("3")){
                ObservableList<String> canadaRegions = CustomerQuery.viewCanadaRegions();
                stateid.setItems(canadaRegions);
                stateid.setValue("60: Northwest Territories");
            }

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<String> allCountries = CustomerQuery.viewAllCountries();
            countryid.setItems(allCountries);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
