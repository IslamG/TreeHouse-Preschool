/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author New
 */
public class StudentInfoController implements Initializable {

    @FXML
    private ImageView idImage;
    @FXML
    private Label fnameField;
    @FXML
    private Label lnameField;
    @FXML
    private Label genderField;
    @FXML
    private Label classField;
    @FXML
    private Label addressField;
    @FXML
    private Label birthplaceField;
    @FXML
    private Label birthdayField;
    @FXML
    private Label addDateField;
    @FXML
    private Label fatherNumField;
    @FXML
    private Button saveInfo;
    @FXML
    private VBox root;
    
    private static String searchStdByName, searchStdById;
    private Alert alert=new Alert(AlertType.ERROR);
    private String mHead, mCont;
    
    private void showAlert(int i){
        String url;
        alert.setHeaderText(mHead);
        alert.setContentText(mCont);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                       getClass().getResource("alertStyles.css").toExternalForm());
        if(i==1){
            alert.setTitle("ERROR");
            dialogPane.getStyleClass().add("error");
            url="cross.png";
        }
        else{
            alert.setTitle("SUCCESS");
            dialogPane.getStyleClass().add("success");
            url="check.png";
        }
        ImageView img=new ImageView(new Image(this.getClass().getResourceAsStream(url)));
        img.setFitHeight(50);
        img.setFitWidth(50);
        dialogPane.setGraphic(img);
        alert.showAndWait();
    }
    @FXML
    void initialize(String a, String b) throws IOException{
        //initilize with search values
        StudentInfoController.searchStdById=a;
        StudentInfoController.searchStdByName=b;
    }
    public String getStuff(){
        return this.fnameField.toString();
    }
    @FXML
    //send data to printer (Save or Print)
    private void saveInfo (){
            PrinterJob job = PrinterJob.createPrinterJob();
            if(job != null){
            job.showPrintDialog(root.getScene().getWindow()); 
            //create a copy of the scene (so you can remove buttons from output image)
            Pane printable=new Pane();
            printable.getChildren().addAll(root.getChildren());
            printable.lookup(".button").setVisible(false);
            job.printPage(printable);
            job.endJob();
            printable.lookup(".button").setVisible(true);
            root.getChildren().addAll(printable.getChildren());
            }
    }

    private void setUp(){
        try {
            // get student data and fill fields
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection) dbc.connect();
            String sql=null;
            if (searchStdById==null){
                sql="Select * from student where fname='"+searchStdByName+"'";
                System.out.println(searchStdByName+" "+sql);
            }
            else{
                sql="Select * from student where student_id="+searchStdById;
                System.out.println(searchStdById+" "+sql);
            }
            PreparedStatement ps=conn.prepareStatement(sql);
            System.out.println(ps);
            //Get student name or ID value from search field
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                System.out.println("doing "+rs.getString("fname"));
                fnameField.setText(rs.getString("fname"));
                lnameField.setText(rs.getString("lname"));
                genderField.setText(rs.getString("gender"));
                classField.setText(rs.getString("class_id"));
                addressField.setText(rs.getString("addres"));
                birthplaceField.setText(rs.getString("birthplase"));
                birthdayField.setText(rs.getString("birthdate"));
                addDateField.setText(rs.getString("storydate"));
                fatherNumField.setText(rs.getString("father_phone_num"));
                //set picture for male or female default
                if(genderField.getText().equalsIgnoreCase("male")){
                    idImage.setImage(new Image(this.getClass().getResourceAsStream("generic-user.png")));
                }
                else{
                   idImage.setImage(new Image(this.getClass().getResourceAsStream("generic-user-female.png")));
                }
            }
            else{
                //display error message in place of usual children
                root.getChildren().clear();
                String m="Oops something went wrong, can't find student with ";
                if(searchStdById==null){
                    m+="name: "+searchStdByName;
                }
                else{
                    m+="ID: "+searchStdById;
                }
                Label l=new Label(m);
                l.getStyleClass().addAll("utilityLabel", "errorPane");
                VBox h=new VBox(l);
                h.alignmentProperty().set(Pos.CENTER);
                h.fillWidthProperty().set(true);
                h.getStyleClass().addAll("errorBox");
                VBox.setVgrow(h, Priority.ALWAYS);
                searchStdById="";
                searchStdByName="";
                root.getChildren().add(h);
            }
            dbc.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        setUp();
    }

}
