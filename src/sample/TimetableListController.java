package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.*;
import service.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class TimetableListController implements Initializable {

    @FXML
    private Button backToMenu;
    @FXML
    private AnchorPane timetableListPane;
    @FXML
    private TableView tableView;
    private ObservableList<ObservableList> dataa;
    static Connection con = DatabaseConnection.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle rb){

        dataa = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT list_of_timetable.data, list_of_patient.name, list_of_patient.surname, list_of_workers.name, list_of_workers.surname FROM list_of_timetable \n" +
                    "INNER JOIN list_of_patient ON list_of_timetable.id_pacjenta = list_of_patient.id\n" +
                    "INNER JOIN list_of_workers ON list_of_timetable.id_lekarza = list_of_workers.id ";
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
        }
    }

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane menu = FXMLLoader.load(getClass().getResource("../fxmlFiles/Menu.fxml"));
        timetableListPane.getChildren().setAll(menu);
    }
}
