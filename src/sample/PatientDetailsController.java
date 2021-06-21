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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PatientDetailsController  implements Initializable {

    @FXML
    private Button backToMenu;
    @FXML
    private Button addVisit;
    @FXML
    private Button viewVisit;
    @FXML
    private Button addTimetable;
    @FXML
    private Button updateButton;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textSurname;
    @FXML
    private DatePicker dateBirthday;
    @FXML
    private TextField textPesel;
    @FXML
    private TextField textStreet;
    @FXML
    private TextField textHomeNumber;
    @FXML
    private TextField textLocalNumber;
    @FXML
    private TextField textCity;
    @FXML
    private TextField textPhone;
    @FXML
    private ChoiceBox patientSex;
    @FXML
    private AnchorPane addTimetablePane;
    public List<Patient> listPatients;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    @FXML
    public void backToMenu() throws IOException {
       backToMenu.setDisable(true);
       AnchorPane viewPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
       detailsPane.getChildren().setAll(viewPane);
    }

    @FXML
    public void updateButton() throws SQLException {

        Example e = new Example();
        if(textName.getText().isEmpty() || textSurname.getText().isEmpty() || textCity.getText().isEmpty() ||
                patientSex.getValue() == null || dateBirthday.getValue() == null || textLocalNumber.getText().isEmpty() ||
                textHomeNumber.getText().isEmpty() || textPhone.getText().isEmpty() || textStreet.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
        else if(e.check(textName.getText()) && e.check(textSurname.getText()) && e.check(textCity.getText()) && isNaN(textPhone.getText()) && textPhone.getText().length() == 9)
        {
            String id = textId.getText();
            PatientDaoImp patDao  = new PatientDaoImp();
            Patient tempPatient = patDao.getPatient(Integer.parseInt(id));

            tempPatient.setName(textName.getText());
            tempPatient.setSurname(textSurname.getText());
            tempPatient.setBirthday(dateBirthday.getValue());
            tempPatient.setPesel(textPesel.getText());
            tempPatient.setCity(textCity.getText());
            tempPatient.setTelephoneNumber(textPhone.getText());
            tempPatient.setStreet(textStreet.getText());
            tempPatient.setHouseNumber(textHomeNumber.getText());
            tempPatient.setLocalNumber(textLocalNumber.getText());
            patDao.update(tempPatient);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Zapisano dane.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
    }

    public void setPatient(Patient patient){

        textId.setText(String.valueOf(patient.getId()));
        textName.setText(patient.getName());
        textSurname.setText(patient.getSurname());
        patientSex.setValue(patient.getSex());
        dateBirthday.setValue(patient.getBirthday());
        textPesel.setText(patient.getPesel());
        textStreet.setText(patient.getStreet());
        textHomeNumber.setText(String.valueOf(patient.getHouseNumber()));
        textLocalNumber.setText(String.valueOf(patient.getLocalNumber()));
        textCity.setText(patient.getCity());
        textPhone.setText(patient.getTelephoneNumber());
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