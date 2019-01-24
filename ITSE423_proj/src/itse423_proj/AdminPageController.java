package itse423_proj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class AdminPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane header;

    @FXML
    void initialize() {
        assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'AdminPage.fxml'.";

    }
}
