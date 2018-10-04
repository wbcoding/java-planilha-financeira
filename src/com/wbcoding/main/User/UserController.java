package com.wbcoding.main.User;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private Button saveBtn;

    @FXML
    private TextField name, username;

    @FXML
    private PasswordField password, repeatPassword;

    @FXML
    private Label nameLabel, usernameLabel, passwordLabel;


    @FXML
    private void closeWindow(Event event) {
        goToLogin(event);
    }

    @FXML
    private void saveAndCloseWindow(Event event) {
        UserDAO user = new UserDAO();
        user.createNewUser(name.getText(), username.getText(), password.getText());
        goToLogin(event);
    }

    @FXML
    private void checkFields() {
        saveBtn.setDisable(true);
        if (name.getText().length() < 4) {
            nameLabel.setText("Deve conter ao menos 4 caracteres.");
        } else {
            nameLabel.setText("");
            if (username.getText().length() < 5) {
                usernameLabel.setText("Deve conter ao menos 4 caracteres.");
            } else {
                usernameLabel.setText("");
                if (password.getText().length() < 6) {
                    passwordLabel.setText("A senha deve conter ao menos 6 caracteres.");
                } else {
                    passwordLabel.setText("");
                    if (!password.getText().equals(repeatPassword.getText())) {
                        passwordLabel.setText("A senha e a confirmação devem ser iguais.");
                    } else {
                        passwordLabel.setText("");
                        if (UserDAO.checkUsernameExists(username.getText())) {
                            usernameLabel.setText("Nome de usuário já existe.");
                        } else {
                            usernameLabel.setText("");
                            saveBtn.setDisable(false);
                        }
                    }
                }
            }
        }
    }

    private void goToLogin(Event event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/wbcoding/main/Login/LoginView.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(parent, 400, 300));
            window.setTitle("Login");
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("UserController.goToLogin => " + e.getMessage());
        }

    }

}
