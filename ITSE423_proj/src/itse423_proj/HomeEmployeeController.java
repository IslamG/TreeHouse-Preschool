package itse423_project2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeEmployeeController implements Initializable {
    @FXML
    private Label nameviwe;

    @FXML
    private Accordion base;

    @FXML
    private Button addstudent, editstudent, searchstudent,addcourse, showcourse, addclass, showclass, showschedule,editUserInfo;

    @FXML
    private Pane content;
    
    @FXML
    private TextField studentssn;

    @FXML
    private RadioButton genderm,genderf;

    @FXML
    private TextField studentadress;

    @FXML
    private TextField story;

    @FXML
    private TextField nat;

    @FXML
    private TextField birth;

    @FXML
    private TextField fatherphon;

    @FXML
    private TextField momname;

    @FXML
    private TextField studentname;
            
    @FXML
    private TextField name, email,adress,ssn,dep;
    
    @FXML
    void clickBottonexit(ActionEvent event) throws SQLException, IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("LogInForm.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image(this.getClass().getResourceAsStream("pin.png"));
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void alart(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Massega");
        alert.setHeaderText("You have msg not read it");
        alert.setContentText("you hane 4 massega");
        alert.show();
    }
    
    @FXML
    public void Home(ActionEvent actionEvent) throws IOException {
        Stage primaryStage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("HomeEmployee.fxml"));
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Node source = (Node) actionEvent.getSource();
        Stage stage2 = (Stage) source.getScene().getWindow();
        stage2.close();
    }
    
    @FXML
    private void studentManager(ActionEvent event)throws SQLException, IOException{  
        AnchorPane newLoadedPane;
        Button tempButton = (Button)event.getSource();
        Stage stage;
        switch(tempButton.getId()){
            case"addstudent":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("addstudent.fxml")); 
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break; 
            case"editstudent":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("editstudent.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
            case"searchstudent":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("searchstudent.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
        }
    }
    
    @FXML
    void addStudent(ActionEvent event) throws SQLException, ClassNotFoundException {
        studentname.getText();
       
        nat.getText();
        genderm.getId();
        genderf.getId();
        String addStudentStmt ="INSERT INTO `treehouseschool`.`student` (`student_id`, `fname`, `sname`, `lname`, `mname`, `addres`, `ssn`, `birthdate`, `storydate`, `father_phone_num`, `class_id`, `gender`, `nation`) VALUES ('2', '"+studentname.getText()+"', 'h', 'esa', '"+momname.getText()+"', '"+studentadress.getText()+"', '"+studentssn.getText()+"', '"+birth.getText()+"', '"+story.getText()+"', '"+fatherphon.getText()+"', '2', 'male', 'ly');";
        try {
            DBcon.dbExecuteUpdate(addStudentStmt);
        } catch (SQLException e) {
            throw e;
        }
        
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Massega");
        alert.setHeaderText("New Student");
        alert.setContentText("Student Saved Successfully");
        alert.show();
    }


    @FXML
    void cancelStudent(ActionEvent event) {
        studentname.setText("");
        momname.setText("");
        fatherphon.setText("");
        nat.setText("");
        studentadress.setText("");
        studentssn.setText("");
        birth.setText("");
        story.setText("");
        genderm.setSelected(false);
        genderf.setSelected(false);
    }
    
    @FXML
    private void userinfo(ActionEvent event) throws IOException{
//        List <DBinfo> list=DBcon.getUserInfo(1); // from database
//        for(DBinfo a:list){
//            nameviwe.setText(a.getUfirstname()+" "+a.getUlastname());
//            name.setText(a.getUfirstname()+" "+a.getUsecoundname()+" "+a.getUlastname());
//            email.setText("park19996@gamil.com");
//            ssn.setText("");
//            adress.setText(a.getUaddress());
//            dep.setText(a.getdep());
//        }
        AnchorPane newLoadedPane;
        Button tempButton = (Button)event.getSource();
        Stage stage;
        newLoadedPane =  FXMLLoader.load(getClass().getResource("EmployeeInfo.fxml"));
        content.getChildren().clear();
        content.getChildren().add(newLoadedPane);
        base=(Accordion)newLoadedPane.getChildren().get(0);
        base.setExpandedPane(base.getPanes().get(0));
        stage = (Stage) content.getScene().getWindow();
        stage.sizeToScene();
    }
    
    @FXML
    private void coursesManager(ActionEvent event)throws SQLException, IOException{
        AnchorPane newLoadedPane;
        Button tempButton = (Button)event.getSource();
        Stage stage;
        switch(tempButton.getId()){
            case"addcourse":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("addcourse.fxml")); 
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break; 
            case"showcourse":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("showcourse.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
        }
    }
    
    @FXML
    private void classesManager(ActionEvent event)throws SQLException, IOException{
        AnchorPane newLoadedPane;
        Button tempButton = (Button)event.getSource();
        Stage stage;
        switch(tempButton.getId()){
            case"addclass":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("addclass.fxml")); 
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break; 
            case"showclass":
                newLoadedPane =  FXMLLoader.load(getClass().getResource("showclass.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                base=(Accordion)newLoadedPane.getChildren().get(0);
                base.setExpandedPane(base.getPanes().get(0));
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
        }
    }
    
    @FXML
    private void showSchrdule(ActionEvent event)throws SQLException, IOException{
        AnchorPane newLoadedPane;
        Button tempButton = (Button)event.getSource();
        Stage stage;
        newLoadedPane =  FXMLLoader.load(getClass().getResource("showSchrdule.fxml")); 
        content.getChildren().clear();
        content.getChildren().add(newLoadedPane);
        base=(Accordion)newLoadedPane.getChildren().get(0);
        base.setExpandedPane(base.getPanes().get(0));
        stage = (Stage) content.getScene().getWindow();
        stage.sizeToScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}