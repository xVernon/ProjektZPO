package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.*;
import service.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class AddTimetableController {

    @FXML
    private Label patientLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private ChoiceBox doctorsChoiceBox;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField idField;
    @FXML
    private TextField doctorsId;
    @FXML
    private Button backToMenu;
    @FXML
    private Button updateButton;
    @FXML
    private AnchorPane addTimetablePane;

    static Connection con = DatabaseConnection.getConnection();

        @FXML
        public void backToMenu() throws IOException
        {
            backToMenu.setDisable(true);
            AnchorPane detailsPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
            addTimetablePane.getChildren().setAll(detailsPane);
        }

        public void addTimetable(Patient patient) throws SQLException {

            String name = null;
            String surname = null;
            int id = 0;
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, surname FROM list_of_workers WHERE role='Doktor'");

            while (resultSet.next())
            {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                surname = resultSet.getString("surname");
                doctorsChoiceBox.getItems().add(name+" "+surname);
            }
            idField.setText(String.valueOf(patient.getId()));
            doctorsId.setText(String.valueOf(doctorsChoiceBox.getValue()));
            dateField.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
                }
            });
            birthdayLabel.setText(String.valueOf(patient.getBirthday()));
            patientLabel.setText(patient.getName()+" "+patient.getSurname());
            }

        @FXML
        public void updateButton() throws SQLException, IOException {

            if(doctorsChoiceBox.getValue() == null || dateField.getValue() == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Błąd danych!");
                alert.showAndWait();
            }
            else
            {
                String str = (String)doctorsChoiceBox.getValue();
                String[] parts = str.split(" ", 2);
                String string1 = parts[0];
                String string2 = parts[1];
                int id = 0;

                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id FROM list_of_workers WHERE name='" + string1 + "' AND surname='" + string2 + "'");
                while (resultSet.next())
                {
                    id = resultSet.getInt("id");
                }
                Timetable emp = new Timetable(dateField.getValue(),Integer.parseInt(idField.getText()),id);
                TimetableDaoImp empDao = new TimetableDaoImp();

                // add
                empDao.add(emp);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Dodano pomyślnie");
                alert.showAndWait();

                AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
                addTimetablePane.getChildren().setAll(pane);
            }
        }
}


