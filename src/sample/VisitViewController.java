package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Patient;

import java.io.IOException;

public class VisitViewController {

    @FXML
    private Button backToMenu;
    @FXML
    private Button details;
    @FXML
    private AnchorPane visitView;

    @FXML
    public void backToMenu() throws IOException {
        backToMenu.setDisable(true);
        AnchorPane detailsPane = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientDetails.fxml"));
        visitView.getChildren().setAll(detailsPane);
    }

    @FXML
    public void goToDetails() throws IOException {
        details.setDisable(true);
        AnchorPane visitDetails = FXMLLoader.load(getClass().getResource("../fxmlFiles/PatientView.fxml"));
        visitView.getChildren().setAll(visitDetails);
    }
}
