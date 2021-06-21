package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Patient;
import model.PatientDaoImp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;


public class PatientViewController  implements Initializable {

    private static final String PACIENT_NOT_FOUND = "Pacjent nie znaleziony";

    @FXML
    private Button backToMenu;
    @FXML
    private Button goToDetails;
    @FXML
    private AnchorPane viewPane;
    @FXML
    private TextField nameField;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn birthdayColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private Button visitHistory;
    @FXML
    private AnchorPane visitsListPane;

    public List<Patient> listPatients;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        PatientDaoImp emp = new PatientDaoImp();
        listPatients = null;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        try {
            listPatients = emp.getPatients();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (listPatients != null)
        {
            listPatients.forEach(patient ->
                    tableView.getItems().add(patient));
        }

            ObservableList<Patient> masterData = FXCollections.observableArrayList(listPatients);
            FilteredList<Patient> filteredData = new FilteredList<>(masterData, p -> true);
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(patient -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (patient.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (patient.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else
                        return false;
                });
            });

            SortedList<Patient> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);

    }


    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/Menu.fxml"));
        viewPane.getChildren().setAll(pane);
    }
    @FXML
    void goToDetails() throws IOException {

        goToDetails.setDisable(true);

        if(listPatients != null && listPatients.size() != 0)
        {
            Object selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            String id = selectedItems.toString().split(",")[0].substring(11);
            Patient patient = listPatients.stream().filter(p -> p.getId() == Integer.parseInt(id)).findFirst().orElseThrow(() -> new RuntimeException(PACIENT_NOT_FOUND));
            goToDetails.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/PatientDetails.fxml"));
            AnchorPane asd = (AnchorPane)loader.load();
            PatientDetailsController controller = loader.getController();
            controller.setPatient(patient);
            viewPane.getChildren().setAll(asd);
        }
    }

    @FXML
    void addVisit() throws IOException {
        if(listPatients != null && listPatients.size() != 0)
        {
            Object selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            String id = selectedItems.toString().split(",")[0].substring(11);
            Patient patient = listPatients.stream().filter(p -> p.getId() == Integer.parseInt(id)).findFirst().orElseThrow(() -> new RuntimeException(PACIENT_NOT_FOUND));
            goToDetails.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/AddVisit.fxml"));
            AnchorPane asd = (AnchorPane)loader.load();
            AddVisitController controller = loader.getController();
            controller.addVisit(patient);
            viewPane.getChildren().setAll(asd);
        }
    }

    @FXML
    void goToAddTimetable() throws IOException, SQLException {
        if(listPatients != null && listPatients.size() != 0)
        {
            Object selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            String id = selectedItems.toString().split(",")[0].substring(11);
            Patient patient = listPatients.stream().filter(p -> p.getId() == Integer.parseInt(id)).findFirst().orElseThrow(() -> new RuntimeException(PACIENT_NOT_FOUND));
            goToDetails.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/AddTimetable.fxml"));
            AnchorPane asd = (AnchorPane)loader.load();
            AddTimetableController controller = loader.getController();
            controller.addTimetable(patient);
            viewPane.getChildren().setAll(asd);
        }
    }

    @FXML
    void checkHistory() throws IOException {
        if(listPatients != null && listPatients.size() != 0)
        {
            visitHistory.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/HistoryVisit.fxml"));
            AnchorPane asd = (AnchorPane)loader.load();
            viewPane.getChildren().setAll(asd);
        }
    }
}
