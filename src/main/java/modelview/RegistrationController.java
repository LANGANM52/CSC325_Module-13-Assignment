package modelview;

import com.mycompany.mvvmexample.App;
import viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import models.Person;

public class RegistrationController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button writeButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextArea outputField;
    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    void initialize() {
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        firstNameField.textProperty().bindBidirectional(accessDataViewModel.firstNameProperty());
        lastNameField.textProperty().bindBidirectional(accessDataViewModel.lastNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        ageField.textProperty().bindBidirectional(accessDataViewModel.userAgeProperty(), new NumberStringConverter());
        emailField.textProperty().bindBidirectional(accessDataViewModel.userEmailProperty());
        passwordField.textProperty().bindBidirectional(accessDataViewModel.userPasswordProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
    }

    @FXML
    private void addRecord(ActionEvent event) throws IOException {
        addData();
    }

    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login.fxml");
    }

    public void addData() throws IOException {
        DocumentReference docRef = App.fstore.collection("Registration").document(UUID.randomUUID().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("FirstName", firstNameField.getText());
        data.put("LastName", lastNameField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        data.put("Major", majorField.getText());
        data.put("Email", emailField.getText());
        data.put("Password", passwordField.getText());

        ApiFuture<WriteResult> result = docRef.set(data);

        // Show a pop-up
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Registration Successful!");
        alert.showAndWait();
        switchToLogin();
    }

    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
