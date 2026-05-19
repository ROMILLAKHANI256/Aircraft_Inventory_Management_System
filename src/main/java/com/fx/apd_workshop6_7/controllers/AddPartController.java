/**********************************************
 Workshop # 6 and 7
 Course: APD545
 Semester: 5
 Last Name: Lakhani
 First Name: Romil
 Student ID: 171612229
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Romil Lakhani
 Date: 01-08-2025
 **********************************************/

package com.fx.apd_workshop6_7.controllers;

import com.fx.apd_workshop6_7.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class AddPartController {

    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private ToggleGroup partTypeGroup;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineCompanyField;

    @FXML private Label machineCompanyLabel;

    @FXML
    public void initialize() {
        // Set default type to In-House and disable ID input
        inHouseRadio.setToggleGroup(partTypeGroup);
        outsourcedRadio.setToggleGroup(partTypeGroup);
        inHouseRadio.setSelected(true);
        idField.setText(String.valueOf(Inventory.peekNextPartID()));
        idField.setDisable(true);
    }

    @FXML
    public void handleInHouse() {
        machineCompanyLabel.setText("Machine ID:");
    }

    @FXML
    public void handleOutsourced() {
        machineCompanyLabel.setText("Company Name:");
    }

    @FXML
    public void handleSave() {
        try {
            int id = Inventory.consumeNextPartID();
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int stock = Integer.parseInt(invField.getText().trim());
            int min = Integer.parseInt(minField.getText().trim());
            int max = Integer.parseInt(maxField.getText().trim());

            if (name.isEmpty()) {
                showAlert("Validation Error", "Name cannot be empty.");
                return;
            }

            if (min > max) {
                showAlert("Validation Error", "Min cannot be greater than Max.");
                return;
            }

            if (stock < min || stock > max) {
                showAlert("Validation Error", "Inventory must be between Min and Max.");
                return;
            }

            Part newPart;
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(machineCompanyField.getText().trim());
                newPart = new InHouse(id, name, price, stock, min, max, machineId);
            } else {
                String companyName = machineCompanyField.getText().trim();
                if (companyName.isEmpty()) {
                    showAlert("Validation Error", "Company name cannot be empty.");
                    return;
                }
                newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            }

            Inventory.addPart(newPart);
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        if (confirmDialog("Cancel", "Are you sure you want to cancel?")) {
            closeWindow();
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean confirmDialog(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
