package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.scene.control.*;
import service.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VisitsListController implements Initializable{

    private static final String PACIENT_NOT_FOUND = "Pacjent nie znaleziony";
    @FXML
    private Button visitDetails;
    @FXML
    private Button backToMenu;
    @FXML
    private AnchorPane visitsListPane;
    @FXML
    private TableView tableView;

    static Connection con = DatabaseConnection.getConnection();
    private ObservableList<ObservableList> dataa;

    @Override
    public void initialize(URL url, ResourceBundle rb){

        dataa = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT list_of_patient.name, list_of_patient.surname, list_of_visits.data, list_of_visits.id_pacjenta FROM list_of_visits INNER JOIN list_of_patient ON list_of_visits.id_pacjenta = list_of_patient.id";
            ResultSet resultSet = con.createStatement().executeQuery(SQL);
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }
            while(resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                dataa.add(row);
            }
            tableView.setItems(dataa);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane detailsPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
        visitsListPane.getChildren().setAll(detailsPane);
    }

    @FXML
    public void visitDetails() throws IOException, SQLException {

            Object selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            String name = selectedItems.toString().split(",")[0].substring(1);
            String surname = selectedItems.toString().split(",")[1].substring(1);
            String person = name+" "+surname;
            String date = selectedItems.toString().split(",")[2].substring(1);
            String id = selectedItems.toString().split(",")[3];
            id = id.substring(1,id.length()-1);
            LocalDate date1 = LocalDate.parse(date);
            VisitPatient patient = new VisitPatient(person,date1,Integer.parseInt(id));
            visitDetails.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/VisitDetails.fxml"));
            AnchorPane asd = (AnchorPane)loader.load();
            DetailsViewController controller = loader.getController();
            controller.checkDetails(patient);
            visitsListPane.getChildren().setAll(asd);
    }

    public class VisitPatient {
        private String name;
        private LocalDate date;
        private int id_patient;

        public VisitPatient(String name, LocalDate date, int id_patient) {
            this.name = name;
            this.date = date;
            this.id_patient = id_patient;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public int getId_patient() {
            return id_patient;
        }

        public void setId_patient(int id_patient) {
            this.id_patient = id_patient;
        }
    }
}
