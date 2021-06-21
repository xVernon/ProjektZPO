package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.Visit;
import service.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetailsViewController{

    @FXML
    private Button backToMenu;
    @FXML
    private AnchorPane visitDetailsPane;
    @FXML
    private Label patientName;
    @FXML
    private Label dateVisit;
    @FXML
    private TextArea reasonField;

    static Connection con = DatabaseConnection.getConnection();

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane viewPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/HistoryVisit.fxml"));
        visitDetailsPane.getChildren().setAll(viewPane);
    }

    public void checkDetails(VisitsListController.VisitPatient visit) throws SQLException {

        String reason = null;
        int id = visit.getId_patient();

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT przyczyna FROM list_of_visits WHERE id_pacjenta ='"+ id +"'");
        while (resultSet.next())
        {
            reason = resultSet.getString("przyczyna");
        }
        patientName.setText(visit.getName());
        dateVisit.setText(String.valueOf(visit.getDate()));
        reasonField.setText(reason);
    }
}