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
import com.fx.apd_workshop6_7.databases.InventoryDAO;
import com.fx.apd_workshop6_7.utils.InventoryData;
import com.fx.apd_workshop6_7.model.Inventory;
import com.fx.apd_workshop6_7.model.Part;
import com.fx.apd_workshop6_7.model.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {


    public Button cancelSearchPart;
    @FXML private TextField partSearchField;
    @FXML private TextField productSearchField;
    @FXML private TableView<Part> partTable;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;

    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TableColumn<Product, Integer> productStockColumn;


    @FXML
    public void initialize() {

        // Setup Part Table columns
        partIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        partStockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());

        // Setup Product Table columns
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        productPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        productStockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());

        productPriceColumn.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });

        partPriceColumn.setCellFactory(column -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));  // formats with 2 decimals and $ sign
                }
            }
        });

        // Load data
        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());
    }

    @FXML
    private void handlePartSearch() {
        String query = partSearchField.getText().trim();
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        try {
            int id = Integer.parseInt(query);
            Part part = Inventory.lookupPart(id);
            if (part != null) {
                searchResults.add(part);
            }
        } catch (NumberFormatException e) {
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(part);
                }
            }
        }

        if (searchResults.isEmpty()) {
            showAlert("No Results", "No matching parts found.");
        } else {
            partTable.setItems(searchResults);
        }
    }

    @FXML
    private void handleProductSearch() {
        String query = productSearchField.getText().trim();
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        try {
            int id = Integer.parseInt(query);
            Product product = Inventory.lookupProduct(id);
            if (product != null) {
                searchResults.add(product);
            }
        } catch (NumberFormatException e) {
            for (Product product : Inventory.getAllProducts()) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(product);
                }
            }
        }

        if (searchResults.isEmpty()) {
            showAlert("No Results", "No matching products found.");
        } else {
            productTable.setItems(searchResults);
        }
    }

    @FXML
    private void handleAddPart() {
        loadStage("/com/fx/apd_workshop6_7/AddPart.fxml");
    }

    @FXML
    private void handleModifyPart() {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a part to modify.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fx/apd_workshop6_7/ModifyPart.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            ModifyPartController controller = loader.getController();
            controller.setPartToModify(selected);
            stage.show();

            partTable.refresh();
        } catch (IOException e) {
            showAlert("Error", "Failed to load Modify Part screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeletePart() {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a part to delete.");
            return;
        }

        if (confirmDialog("Delete Part", "Are you sure you want to delete this part?")) {
            Inventory.deletePart(selected);
            Inventory.refreshIDCounters();
        }
    }

    @FXML
    private void handleAddProduct() {
        loadStage("/com/fx/apd_workshop6_7/AddProduct.fxml");
    }

    @FXML
    private void handleModifyProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a product to modify.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fx/apd_workshop6_7/ModifyProduct.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            ModifyProductController controller = loader.getController();
            controller.setProductToModify(selected);
            stage.show();
            productTable.refresh();


        } catch (IOException e) {
            showAlert("Error", "Failed to load Modify Product screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a product to delete.");
            return;
        }

        if (!selected.getAssociatedParts().isEmpty()) {
            showAlert("Delete Error", "Cannot delete a product with associated parts.");
            return;
        }

        if (confirmDialog("Delete Product", "Are you sure you want to delete this product?")) {
            Inventory.deleteProduct(selected);
            Inventory.refreshIDCounters();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void loadStage(String fxmlPath) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlPath))));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load screen: " + fxmlPath);
            e.printStackTrace();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    @FXML
    private void handleCancelSearchPart() {
        partTable.setItems(Inventory.getAllParts());
        partSearchField.clear();
    }

    @FXML
    private void handleCancelSearchProduct() {
        productTable.setItems(Inventory.getAllProducts());
        productSearchField.clear();
    }

    @FXML
    private void handleSaveToDB() {
        InventoryDAO.saveInventoryToDatabase();
        showInfo("Success", "Data saved to database.");
    }
    @FXML
    private void handleLoadFromDB() throws SQLException {
        InventoryDAO.loadInventoryFromDatabase();
        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());
        showInfo("Success", "Data loaded from database.");
    }
    @FXML
    private void handleInitializeToDB() {
        InventoryDAO.initializeDatabaseFromFile("src/main/resources/com/fx/apd_workshop6_7/initialize.sql");

    }


    @FXML
    private void handleSaveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            List<Product> productList = new ArrayList<>(Inventory.getAllProducts());
            List<Part> partList = new ArrayList<>(Inventory.getAllParts());

            InventoryData data = new InventoryData(partList, productList);

            oos.writeObject(data);
            showInfo("Success", "Data successfully saved to file.");

        } catch (IOException e) {
            showError("File Save Error", e.getMessage());
        }
    }



    @FXML
    private void handleLoadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"))) {
            InventoryData data = (InventoryData) ois.readObject();

            Inventory.getAllParts().clear();
            Inventory.getAllParts().addAll(data.getParts());

            Inventory.getAllProducts().clear();
            Inventory.getAllProducts().addAll(data.getProducts());
            Inventory.refreshIDCounters();

            showInfo("Success", "Data successfully loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            showError("File Load Error", e.getMessage());
        }
    }



    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





}
