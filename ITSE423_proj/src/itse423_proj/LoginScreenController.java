/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import com.mysql.jdbc.Connection;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Pane content;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMsg, capsMsg, lockImg;
    @FXML
    private CheckBox eye;
    @FXML
    private TextField passwordText;
    
    Random random = new Random();
    
    @FXML
    //when mouse is clicked turn blue
    private void mouseActive(){
        Scene scene = content.getScene();
        Image image = new Image(this.getClass().getResourceAsStream("pencil2.png"));  //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }
    @FXML
    //when mouse is released turn back to yellow
    private void mouseInactive(){
        Scene scene = content.getScene();
        Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }
    //initiate falling letters animation
    public void start() {
        //create text nodes to fall
        Text c[] = new Text[300];
        String s;
        Character sue;
        System.out.println("here");
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+=*÷×";
        //loop through text node array and assign random values, colors, sizes
        for (int i = 0; i <200; i++) {
            sue=alphabet.charAt(random.nextInt(alphabet.length()));
            s=Character.toString(sue);
            c[i]=new Text(s);
            Color color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            c[i].setStrokeWidth(3);
            c[i].setStroke(color);
            c[i].setFont(Font.font(random.nextInt(50)+10));
            //add to main pane and animate
            content.getChildren().add(c[i]);
            Raining(c[i]);
        }
        
    }
 
    public void Raining(Text tx) {
        //random position and falling speed
        tx.relocate(random.nextInt(557), -200);
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
        DatabaseConfig dbc= new DatabaseConfig();
        try {
            String name = userNameField.getText();
            String password = passwordField.getText();

            String sql = "SELECT pass from user where fname= ?";
            Connection conn=(Connection) dbc.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            //if passowrd is not null and matches database
            if ((!rs.next()) ||(rs.getString("pass") == null ? password != null : !rs.getString("pass").equals(password))) {
                Alert alert= new Alert(AlertType.ERROR);
                alert.setTitle("LOGING FAILED");
                alert.setHeaderText ("Something went wrong while loggin you in");
                alert.setContentText("Check your login credentials and try again");
                errorMsg.setText("Error in credentials check username or password");
                alert.showAndWait();
                
            } 
            else {
                Parent root1=null;
                sql="select job from user where fname= ?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, name);
                rs=ps.executeQuery();
                //initiate Username and Password in new window, load correct screen depending on positon
                if(rs.next())
                {
                    AdminPageController apc=new AdminPageController();
                    apc.initialize(name, password);
                    root1= FXMLLoader.load(getClass().getResource(
                            rs.getString("job").equalsIgnoreCase("employee") ? "HomeEmployee.fxml" : "AdminPage.fxml"));
                }
                
                Scene scene = new Scene(root1);
                Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
                scene.setCursor(new ImageCursor(image));
            
                Stage stage=new Stage();
                stage.setScene(scene);
                //add event listener onto newly loaded scene (same as mosue active and mouse inactive)
                scene.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
                    Scene scene1 = content.getScene();
                    Image image1 = new Image(AdminPageController.class.getResourceAsStream("pencil2.png"));
                    scene1.setCursor(new ImageCursor(image1));
                    //System.out.println("Hi " + image1);
                } 
                );
                scene.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event1) -> {
                    Scene scene1 = content.getScene();
                    Image image1 = new Image(AdminPageController.class.getResourceAsStream("pencil.png"));
                    scene1.setCursor(new ImageCursor(image1));
                    //System.out.println("Bye " + image1);
                });
                stage.show();
                Node source = (Node) event.getSource();
                Stage stage2 = (Stage) source.getScene().getWindow();
                stage2.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            dbc.disconnect();
        }

}
    private boolean isOn=Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);;
    @FXML
    private void removeMsg(javafx.scene.input.KeyEvent event){
        if (!(errorMsg.getText().equals(""))){
            errorMsg.setText("");
        }
        String image;
        String m="CAPSLOCK is on, password is case sensitive\n";
        if(event.getCode().toString().equals("CAPS"))
            isOn=!isOn;
        //System.out.println(Toolkit.getDefaultToolkit().getLockingKeyState(20)+" "+isOn);
            if(isOn){
                capsMsg.setText(m);
                image = LoginScreenController.class.getResource("capsOn2.png").toExternalForm();
                lockImg.setStyle("-fx-background-image:url('"+image+"')");
            }
            else{
                capsMsg.setText("");
                image = LoginScreenController.class.getResource("capsOff.png").toExternalForm();
                lockImg.setStyle("-fx-background-image:url('"+image+"')");
        }
    }
    @FXML
    private void close(MouseEvent event){
        ImageView img=(ImageView)event.getTarget();
        Stage stage=(Stage)img.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // if showing-eye checkbox is checked show textfield with password value
        passwordField.textProperty().bindBidirectional(passwordText.textProperty());
        passwordField.visibleProperty().bind(eye.selectedProperty().not());
        passwordText.visibleProperty().bind(eye.selectedProperty());
        start();
    }    
 
}
