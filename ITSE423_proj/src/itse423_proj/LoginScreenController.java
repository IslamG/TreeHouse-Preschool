/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author New
 */

public class LoginScreenController implements Initializable {
    @FXML
    private Pane root;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    
    Random random = new Random();
 
    public void start() {
        Text c[] = new Text[300];
        String s;
        Character sue;
        System.out.println("here");
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+=*÷×";
        for (int i = 0; i <200; i++) {
            sue=alphabet.charAt(random.nextInt(alphabet.length()));
            s=Character.toString(sue);
            c[i]=new Text(s);
            Color color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            c[i].setStrokeWidth(3);
            c[i].setStroke(color);
            c[i].setFont(Font.font(random.nextInt(50)+10));
            root.getChildren().add(c[i]);
            Raining(c[i]);
        }
        
    }
 
    public void Raining(Text tx) {
        tx.relocate(random.nextInt(557), -200);
        //System.out.println(tx);
        int time = 10 + random.nextInt(20);
        TranslateTransition fall= new TranslateTransition();
        fall.setDuration(Duration.seconds(time));
        fall.setToX(random.nextDouble()*tx.getLayoutX());
        fall.setToY(534+200);
        fall.setNode(tx);
        fall.setCycleCount(TranslateTransition.INDEFINITE);
        fall.setAutoReverse(true);
        fall.play();
    }
   

    @FXML
    private void login(ActionEvent event) throws SQLException {

        try {
            String name = userNameField.getText();
            String password = passwordField.getText();

            String sql = "SELECT pass from user where fname= ?";
            databaseConfig dbc= new databaseConfig();
            Connection conn=(Connection) dbc.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if ((!rs.next()) ||(rs.getString("pass") == null ? password != null : !rs.getString("pass").equals(password))) {
                Alert alert= new Alert(AlertType.ERROR);
                alert.setTitle("LOGING FAILED");
                alert.setHeaderText ("Something went wrong while loggin you in");
                alert.setContentText("Check your login credentials and try again");
                alert.showAndWait();
            } 
            else {
                Parent root1=null;
                sql="select job from user where fname= ?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, name);
                rs=ps.executeQuery();
                if(rs.next())
                {
                root1= FXMLLoader.load(getClass().getResource(rs.getString("job").equalsIgnoreCase("admin") ? "AdminPage.fxml" : "TeacherPage.fxml"));
                }
                Scene scene = new Scene(root1);
                Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
                scene.setCursor(new ImageCursor(image));
            
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();
                Node source = (Node) event.getSource();
                Stage stage2 = (Stage) source.getScene().getWindow();
                stage2.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        start();
    }    
    
}
