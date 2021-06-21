package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import service.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    @FXML
    private Button wyswietlPacjenta;
    @FXML
    private Button dodajPacjenta;
    @FXML
    private Button dodajPracownika;
    @FXML
    private Button wyswietlPracownika;
    @FXML
    private Button timetable;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField textRole;
    @FXML
    private Button logOut;
    @FXML
    private Pane adminTools;

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        String rola = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try
        {
            String query = "SELECT role FROM logged_person WHERE id=1 ";
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                rola = resultSet.getString("role");
            }
            if(rola.equals("Admin"))
            {
                adminTools.setVisible(true);
                adminTools.setDisable(false);
            }
        }
        catch (SQLException throwables)
        {
                throwables.printStackTrace();
        }
    }

    @FXML
    public void addPatient() throws IOException {
        dodajPacjenta.setDisable(true);
        AnchorPane newPatientPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/AddPatient.fxml"));
        pane.getChildren().setAll(newPatientPane);
    }

    @FXML
    public void viewWorker() throws IOException{
        wyswietlPracownika.setDisable(true);
        AnchorPane viewPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/WorkerView.fxml"));
        pane.getChildren().setAll(viewPane);
    }

    @FXML
    public void addWorker() throws IOException{
        dodajPracownika.setDisable(true);
        AnchorPane newWorkerPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/AddWorker.fxml"));
        pane.getChildren().setAll(newWorkerPane);
    }

    @FXML
    public void viewPatient() throws IOException {
        wyswietlPacjenta.setDisable(true);
        AnchorPane viewPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
        pane.getChildren().setAll(viewPane);
    }

    @FXML
    public void goToTimetable() throws IOException {
        timetable.setDisable(true);
        AnchorPane timetablePane = FXMLLoader.load(getClass().getResource("../fxmlFiles/TimeTableList.fxml"));
        pane.getChildren().setAll(timetablePane);
    }

    @FXML
    public void logOut() throws IOException {
        logOut.setDisable(true);
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/sample.fxml"));
        pane.getChildren().setAll(rootPane);
    }
}