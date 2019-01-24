/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author New
 */
public class ITSE423_proj extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
        
        Scene scene = new Scene(root);
        Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
        stage.show();
        //System.out.println(getClass().getClassLoader().getResource("itse423_proj/AdminPage.fxml"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
