package Controller;

import Model.AppointmentQuery;
import Model.CustomerQuery;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    private TextField nameid;

    @FXML
    private TextField phoneid;

    @FXML
    private TextField postalcodeid;

    @FXML
    private ComboBox<String> stateid;

    /** This event handler filters the selected country down to the first-level divisions.*/
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

    /** This event handler takes the user back to the dashboard page.*/
    @FXML
    public void back(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    /** This event handler gets the data entered by the user and creates a new customer */
    public void submitCustomer(ActionEvent event) throws SQLException, IOException {

        String name = nameid.getText();
        String address = addressid.getText();
        String postalCode = postalcodeid.getText();
        String phone = phoneid.getText();
        String divId = stateid.getValue();
        divId = divId.split(":")[0];
        int newid = Integer.parseInt(divId);

        CustomerQuery.addCustomer(name, address, postalCode, phone, newid);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }



    /** This initializes the page - allCountries is created to populate the combo box. */
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
