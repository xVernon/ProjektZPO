package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.*;
import service.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddVisitController {

    @FXML
    private Button backToMenu;
    @FXML
    private AnchorPane addVisitPane;
    @FXML
    private Label patientName;
    @FXML
    private Label dateTimetable;
    @FXML
    private TextArea description;

    static Connection con = DatabaseConnection.getConnection();

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane detailsPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
        addVisitPane.getChildren().setAll(detailsPane);
    }

    public void addVisit(Patient patient){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        patientName.setText(patient.getName()+" "+patient.getSurname());
        dateTimetable.setText((dtf.format(now)));
    }

    @FXML
    public void addToDataBase() throws SQLException, IOException {

        String str = patientName.getText();
        String[] parts = str.split(" ", 2);
        String string1 = parts[0];
        String string2 = parts[1];
        LocalDate now = LocalDate.now();

        int id = 0;

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM list_of_patient WHERE name='" + string1 + "' AND surname='" + string2 + "'");
        while (resultSet.next())
        {
            id = resultSet.getInt("id");
        }

        if(description.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
        else
        {
            Visit emp = new Visit(id,now,description.getText());

            VisitDaoImp empDao = new VisitDaoImp();

            // add
            empDao.add(emp);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Dodano pomyślnie");
            alert.showAndWait();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
            addVisitPane.getChildren().setAll(pane);
        }
    }
}
