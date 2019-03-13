package itse423_project2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane content;

    @FXML
    private CheckBox eye;

    @FXML
    private Button loginb;

    @FXML
    private Label msg;    

    @FXML
    private TextField name;

    @FXML
    private TextField passT;

    @FXML
    private PasswordField passF;

    Random random = new Random();
    
    //initiate falling letters animation
    public void start() {
        //create text nodes to fall
        Text c[] = new Text[300];
        String s;
        Character sue;
        String alphabet = "0123456789abdeghmnqrtuyABCDEFGHIJKLMNOPQRSTUVWXYZ+=÷×";
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
    void clickBottonLogin(ActionEvent event)throws SQLException, IOException {
        List <DBinfo> list=DBcon.getloginfo(); // from database
        Map<String,String> map=new HashMap<>();
        for(DBinfo a:list){
            map.put(a.getUfirstname(),a.getjob());
        }
        if("".equals(passT.getText())&"".equals(name.getText()))
        {
            msg.setText("inter your user name and password");
        }else if("".equals(passT.getText()))
        {
           msg.setText("inter your password");
        }else if("".equals(name.getText()))
        {
            msg.setText("inter your user name");
        }else if(map.containsKey(name.getText()))
        {
            String val=map.get(name.getText());
            if(val.equals(passT.getText()))
            {
                try{
                    if("Shahed".equals(name.getText())){
                        DBcon.getonnection();
                        Stage primaryStage= new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("HomeEmployee.fxml"));
                        Scene scene= new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        Node source = (Node) event.getSource();
                        Stage stage2 = (Stage) source.getScene().getWindow();
                        stage2.close(); 
                    }else if("islam".equals(name.getText())){
                        DBcon.getonnection();
                        Stage primaryStage= new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("HomeEmployee.fxml"));
                        Scene scene= new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        Node source = (Node) event.getSource();
                        Stage stage2 = (Stage) source.getScene().getWindow();
                        stage2.close();
                    }else if("malak".equals(name.getText())){
                        DBcon.getonnection();
                        Stage primaryStage= new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("HomeEmployee.fxml"));
                        Scene scene= new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        Node source = (Node) event.getSource();
                        Stage stage2 = (Stage) source.getScene().getWindow();
                        stage2.close();
                    }else
                    {
                        msg.setText("Failed try again");
                    }
                }catch (IOException ex) {Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);}
            }
            else
            {
            msg.setText("Failed try again");
            }
        }else{
            msg.setText("Failed try again");        
        }
    }       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // if showing-eye checkbox is checked show textfield with password value
        passF.textProperty().bindBidirectional(passT.textProperty());
        passF.visibleProperty().bind(eye.selectedProperty().not());
        passT.visibleProperty().bind(eye.selectedProperty());
        start();
    }
}