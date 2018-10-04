package com.wbcoding.main.Login;

import com.wbcoding.main.User.UserModel;
import com.wbcoding.main.db.H2DB;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class LoginController {

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Label errorMessage;


    public void initialize(){
        checkIfDatabaseExists();
    }

    @FXML
    private void hideErrorMsg() {
        errorMessage.setText("");
    }

    @FXML
    private void goToMainScreen(Event event) {

        try {
            LoginDAO loginDAO = new LoginDAO();
            boolean userExist = loginDAO.isLoginCorrect(username.getText(), password.getText());

            if (userExist) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/wbcoding/main/Home/homeView.fxml"));

                Parent home = loader.load();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(home, 1200, 570));
                window.setTitle("Olá, " + UserModel.getName() + "!");
                window.setResizable(false);
                window.setFullScreen(false);
                window.show();
                window.centerOnScreen();
            } else {
                this.errorMessage.setText("Usuário não encontrado ou senha incorreta!");
            }
        } catch (Exception e) {
            System.out.println("LoginController:goToMainScreen => " + e.getMessage());
        }

    }

    @FXML
    private void openNewUserScreen(Event event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/wbcoding/main/User/UserView.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(new Scene(parent, 400, 400));
            window.setTitle("Novo Usuário");
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("LogingController:openNewUserScreen => " + e.getMessage());
        }

    }

    private void checkIfDatabaseExists(){
        H2DB.createDBSchema();
    }
}


