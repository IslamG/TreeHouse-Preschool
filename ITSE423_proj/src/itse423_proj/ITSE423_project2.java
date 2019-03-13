package itse423_project2;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ITSE423_project2 extends Application {
 
    @Override
    public void start(Stage stage) throws IOException { 
        Parent root = FXMLLoader.load(getClass().getResource("LogInForm.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image(this.getClass().getResourceAsStream("pin.png"));
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
        stage.show();
    } 
    
    public static void main(String[] args) {
        launch(args);
    }
}