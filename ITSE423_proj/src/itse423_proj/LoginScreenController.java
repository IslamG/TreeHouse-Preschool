/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author New
 */
public class LoginScreenController implements Initializable {
    
    
   

    @FXML
    private void login(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
            //Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
            scene.setCursor(new ImageCursor(image));
            

            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            //Stage stage2 = (Stage) login.getScene().getWindow();
            //stage2.close();
            Node source = (Node) event.getSource();
            Stage stage2 = (Stage) source.getScene().getWindow();
            stage2.close();
            
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
