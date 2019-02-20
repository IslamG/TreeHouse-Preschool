package itse423_proj;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPageController {

    @FXML
    private Pane header;
    @FXML
    private AnchorPane staffInfoAnch;
    @FXML
    private AnchorPane content;
    @FXML
    private TabPane staffTab;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button addUser;
    @FXML
    private Button removeUser;
    @FXML
    private Button editTeacher;
    @FXML
    private Button removeTeahcer;
    @FXML
    private ComboBox<String> subjectChoice;
    @FXML
    private ComboBox<String> studentChoice;
    @FXML
    private TabPane teacherTab;
   
    @FXML
    private void manageEmployee(ActionEvent event)
    {
        try 
        {
            Pane newLoadedPane;
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {

                case "addUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    //System.out.println(content.getHeight()+" "+content.getWidth());
                    //staffInfoAnch=(AnchorPane)newLoadedPane.lookup("#staffInfoAnch");
                    //System.out.println(staffInfoAnch.getPrefWidth()+" "+ staffInfoAnch.getPrefHeight());
                    //content.setPrefSize(staffInfoAnch.getPrefWidth(), staffInfoAnch.getPrefHeight());
                    //System.out.println(content.getHeight()+" "+content.getWidth());
                    content.getChildren().add(newLoadedPane);
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                case "removeUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    staffTab=(TabPane)newLoadedPane.getChildren().get(0);
                    staffTab.getSelectionModel().selectLast();
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void manageTeacher(ActionEvent event)
    {
        try 
        {
            AnchorPane newLoadedPane;
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {

                case "editTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                case "removeTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    Node nodeOut = newLoadedPane.getChildren().get(0);
                    if(nodeOut instanceof VBox){
                        for(Node nodeIn:((VBox)nodeOut).getChildren()){
                            if(nodeIn instanceof TabPane){
                                teacherTab=(TabPane)nodeIn;
                                teacherTab.getSelectionModel().selectLast();
                            }
                        }
                    }
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    void searchBy(ActionEvent event) throws IOException {
        Stage stage;
        ComboBox switcher=(ComboBox) event.getSource();
        System.out.println(switcher);
        switch(switcher.getId())
        {   
            case "studentChoice":
                studentChoice.getValue();
                System.out.println("Search by "+studentChoice.getValue());
                Pane newLoadedPane;
                newLoadedPane =  FXMLLoader.load(getClass().getResource("StudentInfo.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
            case "subjectChoice":
                subjectChoice.getValue();
                System.out.println("Search by "+subjectChoice.getValue());
                newLoadedPane =  FXMLLoader.load(getClass().getResource("MarkSearch.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                System.out.println(newLoadedPane);
                content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                stage = (Stage) content.getScene().getWindow();
                stage.sizeToScene();
                break;
        }
    }

    @FXML
    void initialize() {

    }
}
