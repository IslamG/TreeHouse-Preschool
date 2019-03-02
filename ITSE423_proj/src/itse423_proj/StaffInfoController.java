/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author New
 */
public class StaffInfoController implements Initializable {
    @FXML 
    private TextField newName, newPass, confirmPass, searchName, confirmName;
    @FXML
    private Label removeNameField;
    @FXML
    private ChoiceBox positionChoice;
    @FXML
    private Button addButton, cancelButton, confirmButton, cancelRemove, goButton;
    @FXML
    private VBox hideBox;
    
    private Alert alert= new Alert(Alert.AlertType.ERROR);

    @FXML
    private void addUser () throws SQLException{
        System.out.println (newName.getText()+" "+newPass.getText()+" "+confirmPass.getText());
        System.out.println ("added Successfuly");
        alert.setTitle("ADD FAILED");
        //check if add field is empty
        if (newName.getText().equals("")|| newPass.getText().equals("") || confirmPass.getText().equals("")||positionChoice.getValue()==null) {
            alert.setHeaderText ("Something went wrong while adding new user");
            alert.setContentText("All fields are required");
            alert.showAndWait();
        }
        //check if password is correct
        else if (newPass.getText().equals(confirmPass.getText())){
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection)dbc.connect();
            String sql="Insert into user(fname, pass, job) values (?,?,?)";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, newName.getText());
            ps.setString(2, newPass.getText());
            ps.setString(3, positionChoice.getValue().toString());
            if(!ps.execute()){
                alert.setHeaderText ("Something went wrong while adding new user");
                alert.setContentText("Check information and try again");
                alert.showAndWait();
            }
            else{
                alert.alertTypeProperty().set(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("New user added successfully");
                alert.setContentText("Login with new credentials to see changes");
                alert.showAndWait();
            }
            dbc.disconnect();
        }
        else{
            confirmPass.setStyle("-fx-border-color:red;");
            alert.setHeaderText ("Something went wrong while adding new user");
            alert.setContentText("Password mismatch");
            alert.showAndWait();
            confirmPass.setStyle("-fx-border-color:none;");
        }
    }
    @FXML
    //clear all fields
    private void cancel (){
        newName.setText(""); 
        newPass.setText(""); 
        confirmPass.setText("");
        searchName.setText("");
        confirmName.setText("");
        positionChoice.setValue(null);
        removeNameField.setText("\"Type emp name in field above\"");
        hideBox.setVisible(false);
    }
    @FXML
    private void removeUser () throws SQLException{
        String name=searchName.getText();
        String password=confirmName.getText();
        DatabaseConfig dbc=new DatabaseConfig();
        Connection conn=dbc.connect();
        //get logged in user's password
        AdminPageController apc=new AdminPageController();
        String userPass=apc.userPassword;
        if (!password.equals(userPass)){    
            alert.setTitle("REMOVE FAILED");
            alert.setHeaderText ("Something went wrong when trying to remove user");
            alert.setContentText("Check your password and/or emp name and try again");
            alert.showAndWait();
                
            } 
        else {
            String sql="delete from user where fname=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            if (ps.execute()){
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("User removed successfully");
                alert.showAndWait();
                dbc.disconnect();
            }
            else{
                alert.setTitle("REMOVE FAILED");
                alert.setHeaderText ("Something went wrong when trying to remove user");
                alert.setContentText("Check your password and/or emp name and try again");
                alert.showAndWait();
            }
        }
    }
    @FXML
    //allow user to input confirmation password
    private void yesRemove (){
        if (searchName.getText().equals("")){
            alert.setHeaderText ("Something went wrong while trying to remove user");
            alert.setContentText("Please type name of user you wish to remove");
            alert.showAndWait();
            searchName.setStyle("-fx-border-color:red;");
        }
        else
            hideBox.setVisible(true);
    }
    @FXML
    //update visuals on type
    private void typeName (){
        removeNameField.setText(searchName.getText());
        if (removeNameField.getText().equals("")){
            removeNameField.setText("\"Type emp name in field above\"");
        }
        searchName.setStyle("-fx-border-color:none;");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
