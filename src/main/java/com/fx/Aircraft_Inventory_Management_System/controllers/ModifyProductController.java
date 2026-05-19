

package com.fx.Aircraft_Inventory_Management_System.controllers;


import com.fx.Aircraft_Inventory_Management_System.model.Inventory;
import com.fx.Aircraft_Inventory_Management_System.model.Part;
import com.fx.Aircraft_Inventory_Management_System.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class ModifyProductController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField searchField;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partPriceCol;

    @FXML private TableColumn<Part, Integer> assocPartIdCol;
    @FXML private TableColumn<Part, String> assocPartNameCol;
    @FXML private TableColumn<Part, Integer> assocPartInvCol;
    @FXML private TableColumn<Part, Double> assocPartPriceCol;

    @FXML private TableView<Part> partsTable;
    @FXML private TableView<Part> associatedPartsTable;

    private Product originalProduct;
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public void setProductToModify(Product product) {
        this.originalProduct = product;

        idField.setText(String.valueOf(product.getId()));
        idField.setEditable(false);
        nameField.setText(product.getName());
        invField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.format("%.2f", product.getPrice()));
        maxField.setText(String.valueOf(product.getMax()));
        minField.setText(String.valueOf(product.getMin()));

        associatedParts.setAll(product.getAssociatedParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        partsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(associatedParts);
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().trim();
        ObservableList<Part> results = FXCollections.observableArrayList();

        try {
            int id = Integer.parseInt(query);
            Part part = Inventory.lookupPart(id);
            if (part != null) results.add(part);
        } catch (NumberFormatException e) {
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().toLowerCase().contains(query.toLowerCase())) {
                    results.add(part);
                }
            }
        }

        if (results.isEmpty()) {
            showAlert("No Results", "No matching parts found.");
        } else {
            partsTable.setItems(results);
        }
    }

    @FXML
    public void handleAddPart() {
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if (selected != null && !associatedParts.contains(selected)) {
            associatedParts.add(selected);
        } else {
            showAlert("Selection Error", "Please select a valid, unassociated part.");
        }
    }

    @FXML
    public void handleDeletePart() {
        Part selected = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (confirmDialog("Delete Part", "Remove selected part from this product?")) {
                associatedParts.remove(selected);
            }
        } else {
            showAlert("No Selection", "Please select a part to remove.");
        }
    }

    @FXML
    public void handleSave() {
        try {
            int id = originalProduct.getId(); // keep same ID
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

            if (associatedParts.isEmpty()) {
                showAlert("Validation Error", "Product must have at least one associated part.");
                return;
            }

            double totalPartCost = associatedParts.stream().mapToDouble(Part::getPrice).sum();
            if (price < totalPartCost) {
                showAlert("Validation Error", "Product price cannot be less than total cost of associated parts.");
                return;
            }

            Product modifiedProduct = new Product(id, name, price, stock, min, max);
            associatedParts.forEach(modifiedProduct::addAssociatedPart);

            int index = Inventory.getAllProducts().indexOf(originalProduct);
            if (index >= 0) {
                Inventory.getAllProducts().set(index, modifiedProduct);
            }

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
