/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author New
 */
public class UpdateSelfController implements Initializable {

    @FXML
    private Accordion selfAccordion;
    @FXML
    private TitledPane userPane;
    @FXML
    private TextField newNameField;
    @FXML
    private PasswordField confirmPassField;
    @FXML
    private Button saveNameButton;
    @FXML
    private TitledPane passPane;
    @FXML
    private TextField newPasswordField;
    @FXML
    private PasswordField confirmNewPassword;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private TitledPane otherPane;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ssnField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField fatherNameField;
    @FXML
    private TextField motherNameField;
    @FXML
    private RadioButton mRadioButton;
    @FXML
    private ToggleGroup genderRadio;
    @FXML
    private RadioButton fRadioButton;
    @FXML
    private ComboBox<String> departmentCombo;
    @FXML
    private DatePicker hireDatePicker;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField firstNameField;
    
    private static String userPassword, userName,sql; 
    private static DatabaseConfig dbc=new DatabaseConfig();
    private static Connection conn;
    private static ResultSet rs;
    private static PreparedStatement ps;
    private Alert alert=new Alert(AlertType.ERROR);
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up logged in information
        conn=(Connection)dbc.connect();
        AdminPageController apc=new AdminPageController();
        userPassword=apc.userPassword;
        userName=apc.userName;
    }

    @FXML
    private void saveNewName(ActionEvent event) throws SQLException {
        //check if input password matches logged in user password
        if (!confirmPassField.getText().equals(userPassword)){
            alert.setTitle("ERROR");
            alert.setHeaderText("Password mismatch");
            alert.setContentText("check field and try again");
            alert.showAndWait();
        }
        else{
            //set a user name in database
            sql="update user set user_name=? where fname=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,newNameField.getText());
            ps.setString(2,userName);
            ps.execute();
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Information updated successfully");
            alert.setContentText("Search again to view changes");
            alert.showAndWait();
            dbc.disconnect();
        }
        
    }

    @FXML
    private void savePassword(ActionEvent event) throws SQLException {
        //confirm input password match
        if (!oldPasswordField.getText().equals(userPassword)){
            alert.setTitle("ERROR");
            alert.setHeaderText("Incorrect password");
            alert.setContentText("check field and try again");
            alert.showAndWait();
            oldPasswordField.setStyle("-fx-border-color:red;");
        }
        //confirm both fields have new password correctly
        else if(!newPasswordField.getText().equals(confirmNewPassword.getText())){
            oldPasswordField.setStyle("-fx-border-color:none;");
            alert.setTitle("ERROR");
            alert.setHeaderText("Password mismatch");
            alert.setContentText("Both fields must contain same value");
            alert.showAndWait();
            newPasswordField.setStyle("-fx-border-color:red;");
            confirmNewPassword.setStyle("-fx-border-color:red;");
        }
        else{
            //update logged in user passowrd in database
            oldPasswordField.setStyle("-fx-border-color:none;");
            newPasswordField.setStyle("-fx-border-color:none;");
            confirmNewPassword.setStyle("-fx-border-color:none;");
            sql="update user set pass=? where fname=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,newPasswordField.getText());
            ps.setString(2,userName);
            ps.execute();
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Information updated successfully");
            alert.setContentText("Search again to view changes");
            alert.showAndWait();
            dbc.disconnect();
        }
    }

    @FXML
    private void saveNewInfo(ActionEvent event) throws SQLException {
        //update info based on not blank fields only
            sql="update user set ";
            if (!firstNameField.getText().equals("")){
                sql+="fname='"+firstNameField.getText()+"',";
            }
            if (!lastNameField.getText().equals("")){
                sql+="lname='"+lastNameField.getText()+"',";
            }
            if (!fatherNameField.getText().equals("")){
                sql+="sname='"+fatherNameField.getText()+"',";
            }
            if (!motherNameField.getText().equals("")){
                sql+="mname='"+motherNameField.getText()+"',";
            }
            if (!ssnField.getText().equals("")){
                sql+="ssn='"+ssnField.getText()+"',";
            }
            if (!addressField.getText().equals("")){
                sql+="addres='"+addressField.getText()+"',";
            }
            if (hireDatePicker.getValue()!=null){
                sql+="hiring_date= '"+Date.valueOf(hireDatePicker.getValue())+"', ";
            }
            if(departmentCombo.getValue()!=null&&departmentCombo.getValue().equals("Arts")){
                sql+="dep='Arts',";
            }
            else if (departmentCombo.getValue()!=null&&departmentCombo.getValue().equals("Arabic")){
                sql+="dep='Arabic',";
            }
            else if (departmentCombo.getValue()!=null&&departmentCombo.getValue().equals("Maths")){
                sql+="dep='Maths',";
            }
            if (genderRadio.getSelectedToggle()!=mRadioButton){
                sql+="gender='female' ";
            }
            else{
                sql+="gender='male' ";
            }
            sql+="where fname=?";
            ps= conn.prepareStatement(sql);
            ps.setString(1, userName);
            if(ps.execute()){
                alert.setTitle("ERROR");
                alert.setHeaderText("Something Went Wrong");
                alert.setContentText("Check name and try again");
                alert.showAndWait();
            }
            else{
               alert.setAlertType(Alert.AlertType.CONFIRMATION);
               alert.setTitle("SUCCESS");
               alert.setHeaderText("Information updated successfully");
               alert.setContentText("Login again to view changes");
               alert.showAndWait();
            }
            dbc.disconnect();    
     }
    

    @FXML
    //clear text in text fields
    private void cancel(ActionEvent event) {
        lastNameField.setText(""); ssnField.setText("");
        fatherNameField.setText("");motherNameField.setText(""); addressField.setText("");
        departmentCombo.setValue(null);genderRadio.selectToggle(mRadioButton);
        hireDatePicker.setValue(null);
    }
    
}
