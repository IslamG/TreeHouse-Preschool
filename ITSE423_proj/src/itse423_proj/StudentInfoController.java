/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

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
    
    @FXML
    //send data to printer (Save or Print)
    private void saveInfo (){
            PrinterJob job = PrinterJob.createPrinterJob();
            if(job != null){
            job.showPrintDialog(root.getScene().getWindow()); 
            job.printPage(root);
            job.endJob();
            }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // get student data and fill fields
            databaseConfig dbc=new databaseConfig();
            Connection conn=(Connection) dbc.connect();
            String sql="Select * from student where fname=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            //TODO get student name or ID value from search field
            ps.setString(1,"Moez");
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
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
            dbc.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
