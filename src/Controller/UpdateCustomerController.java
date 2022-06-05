package Controller;

import Model.Customer;
import Model.CustomerQuery;
import javafx.collections.FXCollections;
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
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
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

    /*public void regionFilter(ActionEvent event) throws SQLException {
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

    }*/

    public void setCustomer(Customer customer) throws SQLException {

        customerid.setText(String.valueOf(customer.getId()));
        nameid.setText(customer.getName());
        addressid.setText(customer.getAddress());
        postalcodeid.setText(customer.getPostalCode());
        phoneid.setText(customer.getPhone());
        int divId = customer.getDivId();
        ObservableList<String> firstLevel = CustomerQuery.getFirstLevel(divId);
        String customerLevel = firstLevel.get(0);
        stateid.setValue(customerLevel);

        //PICK UP HERE! Isolated country ID, need to do the same for div ID
        ObservableList<String> countries = (CustomerQuery.getCountry(customer.getDivId()));
        String countryId = countries.get(0);
        if(countryId.contains("1")){
            ObservableList<String> usRegions = CustomerQuery.viewUSRegions();
            stateid.setItems(usRegions);
        }
        if(countryId.contains("2")){
            ObservableList<String> ukRegions = CustomerQuery.viewUKRegions();
            stateid.setItems(ukRegions);
        }
        if(countryId.contains("3")){
            ObservableList<String> canadaRegions = CustomerQuery.viewCanadaRegions();
            stateid.setItems(canadaRegions);
        }
        String id = String.valueOf(countryId);
        countryid.setValue(id);


    }

    public void regionFilter(ActionEvent event) throws SQLException {

            countryid.setOnAction(event1 -> {
                String id = countryid.getValue();
                if(id.contains("1")){
                    ObservableList<String> usRegions = null;
                    try {
                        usRegions = CustomerQuery.viewUSRegions();
                        stateid.setItems(usRegions);
                        stateid.setValue("1: Alabama");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if(id.contains("2")) {
                    ObservableList<String> ukRegions = null;
                    try {
                        ukRegions = CustomerQuery.viewUKRegions();
                        stateid.setItems(ukRegions);
                        stateid.setValue("101: England");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
                if(id.contains("3")){
                    ObservableList<String> canadaRegions = null;
                    try {
                        canadaRegions = CustomerQuery.viewCanadaRegions();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    stateid.setItems(canadaRegions);
                    stateid.setValue("60: Northwest Territories");
                }

            });


            }

    public void saveButton(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(customerid.getText());
        String name = nameid.getText();
        String address = addressid.getText();
        String postal = postalcodeid.getText();
        String phone = phoneid.getText();
        String divId = stateid.getValue();
        divId = divId.split(":")[0];
        int newId = Integer.parseInt(divId);

        CustomerQuery.updateCustomer(id, name, address, postal, phone, newId);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> allCountries = null;
        try {
            allCountries = CustomerQuery.viewAllCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        countryid.setItems(allCountries);
    }
}
