/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import models.Person;
import viewmodel.AccessDataViewModel;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextArea outputField;
    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    private void initialize() {
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        emailField.textProperty().bindBidirectional(accessDataViewModel.userEmailProperty());
        passwordField.textProperty().bindBidirectional(accessDataViewModel.userPasswordProperty());

    }

    @FXML
    private void handleLogin(ActionEvent event) throws ExecutionException {
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isSuccessful = firebaseAuthentication(email, password);

        if (isSuccessful) {
            // Show a pop-up
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Login Successful!");
            alert.showAndWait();
            try {
                moveToAccessFBView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            displayAlert("User not found. Click register to make an account.");
        }
    }

    private boolean firebaseAuthentication(String email, String password) throws ExecutionException {
        // This part retrieves the document based on the provided email
        Query query = App.fstore.collection("Registration").whereEqualTo("Email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            QuerySnapshot documents = querySnapshot.get();
            for (QueryDocumentSnapshot document : documents) {
                if (Objects.equals(document.getString("Password"), password)) {
                    return true; // User found with matching email and password
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false; // No user found or password didn't match
    }

    @FXML
    private void moveToRegistration() {
        try {
            App.setRoot("Registration.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveToAccessFBView() throws IOException {
        App.setRoot("AccessFBView.fxml");
    }

    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
