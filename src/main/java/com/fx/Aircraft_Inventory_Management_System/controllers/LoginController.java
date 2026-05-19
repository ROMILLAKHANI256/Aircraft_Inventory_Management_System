

package com.fx.Aircraft_Inventory_Management_System.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final Map<String, String> users = new HashMap<>() {{
        put("admin", "admin123");
        put("user", "user123");
    }};

    @FXML
    private void onLoginClicked() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (users.containsKey(user) && users.get(user).equals(pass)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fx/Aircraft_Inventory_Management_System/main.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Inventory Management System");
                stage.show();
            } catch (IOException e) {
                showAlert("Error", "Unable to load the main screen.");
                e.printStackTrace();
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void onCancelClicked() {
        System.exit(0);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
