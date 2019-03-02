/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private AnchorPane root;
    
    private static String searchStdByName, searchStdById;
    

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
            AnchorPane printable=root;
            printable.getChildren().remove(printable.lookup(".button"));
            job.printPage(printable);
            job.endJob();
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
                root.getChildren().clear();//remove(root.lookup("#notFound"));
                String m="Oops something went wrong, can't find student with ";
                if(searchStdById==null){
                    m+="name: "+searchStdByName;
                }
                else{
                    m+="ID: "+searchStdById;
                }
                Label l=new Label(m);
                l.setId("notFound");
                l.setAlignment(Pos.CENTER);
                l.setTranslateY(90);
                l.setTranslateX(20);
                l.setStyle("-fx-font-size:15px;");
                searchStdById=null;
                searchStdByName=null;
                root.getChildren().add(l);
                
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
