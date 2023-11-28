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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import models.Person;

public class AccessFBView {

    @FXML
    private Button readButton;
    @FXML
    private TextArea outputField;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("WebContainer.fxml");
    }

    public void readFirebase() {
        outputField.clear(); // Clear previous content

        // Asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("Registration").get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                for (QueryDocumentSnapshot document : documents) {
                    String firstName = String.valueOf(document.getData().get("FirstName"));
                    String lastName = String.valueOf(document.getData().get("LastName"));
                    String major = String.valueOf(document.getData().get("Major"));
                    int age = Integer.parseInt(String.valueOf(document.getData().get("Age")));
                    String email = String.valueOf(document.getData().get("Email"));
                    String password = String.valueOf(document.getData().get("Password"));

                    outputField.appendText(firstName + " " + lastName + ", Major: "
                            + major + ", Age: " + age + "\n");

                    person = new Person(firstName, lastName, age, major, email, password);
                    listOfUsers.add(person);
                }
            } else {
                outputField.setText("No data");
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
