package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Worker;
import model.WorkerDaoImp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class WorkerViewController implements Initializable {

    private static final String WORKER_NOT_FOUND = "Pracownik nie znaleziony";

    @FXML
    private Button backToMenu;
    @FXML
    private Button goToDetails;
    @FXML
    private AnchorPane viewPane;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private TableView<Worker> tableView;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn roleColumn;

    private List<Worker> listWorker;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WorkerDaoImp wor = new WorkerDaoImp();
        listWorker = null;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        try {
            listWorker = wor.getWorker();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (listWorker != null) {
            listWorker.forEach(worker -> tableView.getItems().add(worker));
        }
    }

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/Menu.fxml"));
        viewPane.getChildren().setAll(pane);
    }

    @FXML
    void goToDetails() throws IOException {
        if (listWorker != null && listWorker.size() != 0) {
            Worker selectedItems = tableView.getSelectionModel().getSelectedItem();
            int id = selectedItems.getId();
            Worker worker = listWorker.stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new RuntimeException(WORKER_NOT_FOUND));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlFiles/WorkerDetails.fxml"));
            AnchorPane asd = (AnchorPane) loader.load();
            WorkerDetailsController controller = loader.getController();
            controller.setWorker(worker);
            viewPane.getChildren().setAll(asd);

        }
    }
}

