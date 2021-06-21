package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import service.DatabaseConnection;
import java.sql.*;
import java.io.IOException;
import java.util.logging.Logger;
import static sample.Utils.encryptThisString;

public class Controller {

  private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

  @FXML
  private TextField loginField;
  @FXML
  private TextField passwordField;
  @FXML
  private AnchorPane rootPane;
  @FXML
  private Button loadSecond;
  @FXML
  private Button turnOff;
  @FXML
  private Button backToLogin;
  @FXML
  private AnchorPane errorPane;

  static Connection con = DatabaseConnection.getConnection();

  @FXML
  public void loadSecond() throws IOException, SQLException {

    Statement statement = con.createStatement();
    Statement statement1 = con.createStatement();

    String login1 = loginField.getText();
    String password1 = passwordField.getText();
    String hashPSW = encryptThisString(password1);

    int isLogged = 0;
    String rola = null;
    int id = 0;

    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM list_of_workers WHERE login='" + login1 + "' AND password='" + hashPSW + "'");
    ResultSet resultSet1 = statement1.executeQuery("SELECT id, role FROM list_of_workers WHERE login='" + login1 + "' AND password='" + hashPSW + "'");

    while (resultSet.next()) {
      isLogged = resultSet.getInt("count");
    }

    while (resultSet1.next()) {
      rola = resultSet1.getString("role");
      id = resultSet1.getInt("id");
    }

    if (isLogged == 1) {
      LOGGER.info("Zalogowano");

      String query = "update logged_person set role=?, id_person=? where id = 1";
      PreparedStatement ps = con.prepareStatement(query);
      ps.setString(1, rola);
      ps.setInt(2, id);
      ps.executeUpdate();
      AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxmlFiles/Menu.fxml"));
      rootPane.getChildren().setAll(pane);
    }
    else
    {

      if (login1.isEmpty() || password1.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Prosze uzupełnić wszystkie dane!");
        LOGGER.warning("Brak wszystkich danych logowania");
        alert.showAndWait();
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Niepoprawny login lub hasło!");
        LOGGER.warning("Złe dane logowania");
        alert.showAndWait();
      }
      loginField.clear();
      passwordField.clear();
    }
  }

  @FXML
  public void exit(){
    Platform.exit();
  }
}

