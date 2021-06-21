package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Worker;
import model.WorkerDaoImp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class WorkerDetailsController implements Initializable {

    @FXML
    private Button backToMenu;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textSurname;
    @FXML
    private TextField textRole;
    @FXML
    private TextField textLogin;
    @FXML
    private ChoiceBox workerRole;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane viewPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/WorkerView.fxml"));
        detailsPane.getChildren().setAll(viewPane);
    }

    @FXML
    public void updateButton() throws SQLException {


        String id = textId.getText();

        WorkerDaoImp worDao  = new WorkerDaoImp();
        Worker tempWorker = worDao.getWorker(Integer.parseInt(id));

        Example e = new Example();
        if(textName.getText().isEmpty() || textSurname.getText().isEmpty() || textLogin.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd danych!");
            alert.showAndWait();
        }
        else if(e.check(textName.getText()) && e.check(textSurname.getText()))
        {
            tempWorker.setName(textName.getText());
            tempWorker.setSurname(textSurname.getText());
            tempWorker.setRole((String) workerRole.getValue());
            tempWorker.setLogin(textLogin.getText());

            worDao.update(tempWorker);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Czy zapisać dane?");
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

    public void setWorker(Worker worker){

        String check = null;
        textId.setText(String.valueOf(worker.getId()));
        textName.setText(worker.getName());
        textSurname.setText(worker.getSurname());
        textRole.setText(worker.getRole());
        textLogin.setText(worker.getLogin());

        check = worker.getRole();

        if(check.equals("Admin"))
        {
            workerRole.setValue("Admin");
        }
        else if(check.equals("Pielęgniarka"))
        {
            workerRole.setValue("Pielęgniarka");
        }
        else if(check.equals("Doktor"))
        {
            workerRole.setValue("Doktor");
        }
    }
}