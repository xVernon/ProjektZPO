package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Patient;
import model.PatientDaoImp;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewPatientController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField peselField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField houseNumberField;
    @FXML
    private TextField localNumberField;
    @FXML
    private TextField telephoneNumberField;
    @FXML
    private ChoiceBox sexField;
    @FXML
    private Button backToMenu;
    @FXML
    private Button addToDataBase;
    @FXML
    private AnchorPane newPatientPane;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    @FXML
    public void addToDataBase() throws SQLException, IOException {

        Example e = new Example();
        if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() || peselField.getText().isEmpty() ||
                cityField.getText().isEmpty() || sexField.getValue() == null || dateField.getValue() == null ||
                streetField.getText().isEmpty() || telephoneNumberField.getText().isEmpty() || localNumberField.getText().isEmpty() ||
                houseNumberField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
        else if(e.check(nameField.getText()) && e.check(surnameField.getText()) && e.check(cityField.getText()) && isNaN(peselField.getText()) && isNaN(telephoneNumberField.getText()) && telephoneNumberField.getText().length() == 9 && peselField.getText().length() == 11)
        {
            Patient emp = new Patient(nameField.getText(), surnameField.getText(),dateField.getValue(), (String) sexField.getValue(), peselField.getText(), cityField.getText()
                    , streetField.getText(), houseNumberField.getText(),  localNumberField.getText(), telephoneNumberField.getText());

            PatientDaoImp empDao = new PatientDaoImp();

            // add
            empDao.add(emp);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Dodano pomyślnie");
            alert.showAndWait();

            addToDataBase.setDisable(true);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/AddPatient.fxml"));
            newPatientPane.getChildren().setAll(pane);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
    }

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/Menu.fxml"));
        newPatientPane.getChildren().setAll(pane);
    }

    public static boolean isNaN(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
