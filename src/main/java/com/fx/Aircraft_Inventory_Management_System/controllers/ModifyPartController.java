

package com.fx.Aircraft_Inventory_Management_System.controllers;

import com.fx.Aircraft_Inventory_Management_System.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class ModifyPartController {

    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private ToggleGroup partTypeToggle;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineCompanyField;

    @FXML private Label machineCompanyLabel;

    private Part selectedPart;
    private int selectedIndex;

    public void setPartToModify(Part part) {
        this.selectedPart = part;
        this.selectedIndex = Inventory.getAllParts().indexOf(part);

        idField.setText(String.valueOf(part.getId()));
        idField.setEditable(false);
        nameField.setText(part.getName());
        invField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            machineCompanyLabel.setText("Machine ID:");
            machineCompanyField.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            machineCompanyLabel.setText("Company Name:");
            machineCompanyField.setText(((Outsourced) part).getCompanyName());
        }
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
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

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
                    showAlert("Validation Error", "Company Name cannot be empty.");
                    return;
                }
                newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            }

            Inventory.updatePart(selectedIndex, newPart);
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        if (confirmDialog("Cancel", "Are you sure you want to cancel changes?")) {
            closeWindow();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean confirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
