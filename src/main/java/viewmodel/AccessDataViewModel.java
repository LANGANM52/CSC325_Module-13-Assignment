package viewmodel;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataViewModel {

    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty userMajor = new SimpleStringProperty();
    private final StringProperty userEmail = new SimpleStringProperty();
    private final StringProperty userPassword = new SimpleStringProperty();
    private final IntegerProperty userAge = new SimpleIntegerProperty();
    private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

    public AccessDataViewModel() {
        writePossible.bind(
            firstName.isNotEmpty().and(
                lastName.isNotEmpty()).and(
                userMajor.isNotEmpty()).and(
                userEmail.isNotEmpty()).and(
                userPassword.isNotEmpty()).and(
                userAge.greaterThan(0))
        );
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty userMajorProperty() {
        return userMajor;
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public IntegerProperty userAgeProperty() {
        return userAge;
    }

    public ReadOnlyBooleanProperty isWritePossibleProperty() {
        return writePossible.getReadOnlyProperty();
    }
}
