package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Worker;
import model.WorkerDaoImp;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.Utils.encryptThisString;

public class NewWorkerController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private ChoiceBox roleField;
    @FXML
    private Button backToMenu;
    @FXML
    private Button addToDataBase;
    @FXML
    private AnchorPane newWorkerPane;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    public void addToDataBase() throws SQLException, IOException {

        Example e = new Example();
        if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty() || roleField.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
        else if(e.check(nameField.getText()) && e.check(surnameField.getText()))
        {
            Worker emp = new Worker(nameField.getText(), surnameField.getText(),(String) roleField.getValue(),loginField.getText(), encryptThisString(passwordField.getText()));
            WorkerDaoImp worDao = new WorkerDaoImp();

            // add
            worDao.add(emp);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Dodano pomyślnie");
            alert.showAndWait();

            addToDataBase.setDisable(true);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/AddWorker.fxml"));
            newWorkerPane.getChildren().setAll(pane);
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
        newWorkerPane.getChildren().setAll(pane);
    }
}
