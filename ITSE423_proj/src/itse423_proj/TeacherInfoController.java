/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author New
 */
public class TeacherInfoController implements Initializable {

    @FXML
    private TextField searchField, lastNameField, ssnField,fatherNameField,
            motherNameField, addressField;
    @FXML
    private Button searchButton,saveButton, cancelButton, okRemoveButton;
    @FXML
    private TabPane teacherTab;
    @FXML
    private RadioButton mRadioButton, fRadioButton;
    @FXML
    private ToggleGroup genderRadio;
    @FXML
    private ComboBox<String> departmentCombo;
    @FXML
    private DatePicker hireDatePicker;
    @FXML
    private ListView<String> currentInfoList;
    @FXML
    private PasswordField passConfrimField;
    private static String name;
    private Alert alert=new Alert(Alert.AlertType.ERROR);
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchForTeacher() throws SQLException {
        name=searchField.getText();
        //if search field blank
        if (name.equals("")){
            mHead="Field Cannot be empty";
            mCont="Type in teacher name and search again";
            showAlert(1);
            searchField.setStyle("-fx-border-color:red;");
        }
        else{
            searchField.setStyle("-fx-border-color:none;");
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn= (Connection)dbc.connect();
            String sql= "select * from user where fname= ?";
            PreparedStatement pr= conn.prepareStatement(sql);
            pr.setString(1, name);
            ResultSet rs=pr.executeQuery();
            if (!rs.next()){
                mHead="Something went wrong";
                mCont="Check name and try again";
                showAlert(1);
            }
            else{
                //fill remove teacher window with data
                ObservableList<String> data=FXCollections.observableArrayList();
                data.add("ID: "+rs.getString("user_id"));
                data.add("First Name: "+rs.getString("fname"));
                data.add("Last Name: "+rs.getString("lname"));
                data.add("Father Name: "+rs.getString("sname"));
                data.add("Mother Name: "+rs.getString("mname"));
                data.add("SSN: "+rs.getString("ssn"));
                data.add("Gender: "+rs.getString("gender"));
                data.add("Address: "+rs.getString("addres"));
                data.add("Hiring Date: "+rs.getString("hiring_date"));
                data.add("Department: "+rs.getString("dep"));
                data.add("Postion: "+rs.getString("job"));
                data.add("Password: "+rs.getString("pass"));
                currentInfoList.setItems(data);
            }
            dbc.disconnect();
        }
    }

    @FXML
    private void saveNewInfo() throws SQLException {
        name=searchField.getText();
        if (name.equals("")){
            mHead="Field Cannot be empty";
            mCont="Type in teacher name and search again";
            showAlert(1);
            searchField.setStyle("-fx-border-color:red;");
        }
        else{
            //update info based on not blank fields only
            String sql="update user set ";
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection)dbc.connect();
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
            PreparedStatement pr= conn.prepareStatement(sql);
            pr.setString(1, name);
            if(pr.executeUpdate()>1){
                mHead="Something went wrong";
                mCont="Check name and try again";
                showAlert(1);
            }
            else{
               mHead="Information updated successfully";
               mCont="Search again to view changes";
               showAlert(2);
            }
            dbc.disconnect();
        }
    }

    @FXML
    //clear all fields
    private void cancel() {
        searchField.setText(""); lastNameField.setText(""); ssnField.setText("");
        fatherNameField.setText("");motherNameField.setText(""); addressField.setText("");
        passConfrimField.setText("");
        departmentCombo.setValue(null);genderRadio.selectToggle(mRadioButton);
        hireDatePicker.setValue(null);currentInfoList.setUserData(null);
    }

    @FXML
    //remove all accessable data
    private void confirmClear() throws SQLException {
        name=searchField.getText();
        if (name.equals("")){
            mHead="Field Cannot be empty";
            mCont="Type in teacher name and search again";
            showAlert(1);
            searchField.setStyle("-fx-border-color:red;");
        }
        else {
            //confirm input password matches logged in user
            String password=passConfrimField.getText();
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection)dbc.connect();
            AdminPageController apc=new AdminPageController();
            String userPassword=apc.userPassword;
            if (!password.equals(userPassword))
                {
                mHead="Something went wrong";
                mCont="Check your passowrd and try again";
                showAlert(1);
             }
            else{
               String sql="update user set lname=null,sname=null,addres=null,hiring_date=null,ssn=null,mname=null,dep=null,gender='male' where fname=?";
               PreparedStatement ps=conn.prepareStatement(sql);
               ps.setString(1, name);
               if (ps.executeUpdate()>0){
                    mHead="Information cleared successfully";
                    mCont="Search again to view changes";
                    showAlert(2);
               }
               else{
                mHead="Something went wrong";
                mCont="Data not updated";
                showAlert(1);
             }
            }
            dbc.disconnect();
        } 
    }
    
}
