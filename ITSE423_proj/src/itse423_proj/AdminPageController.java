package itse423_proj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AdminPageController {

    @FXML
    private Pane header;
    
    @FXML
    private Pane content;
    @FXML
    private TabPane staffTab;
    
    @FXML
    private Button addU;
    @FXML
    private Button removeU;
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

            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {

                case "addU":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    break;
                case "removeU":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    staffTab=(TabPane)newLoadedPane.getChildren().get(0);
                    staffTab.getSelectionModel().selectLast();
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
            Pane newLoadedPane;

            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {

                case "editTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
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
                    break;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    void searchBy(ActionEvent event) throws IOException {
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
                break;
            case "subjectChoice":
                subjectChoice.getValue();
                System.out.println("Search by "+subjectChoice.getValue());
                newLoadedPane =  FXMLLoader.load(getClass().getResource("SubjectEdit.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                break;
        }
    }

    @FXML
    void initialize() {
        
        //ObservableList<String> availableChoices = FXCollections.observableArrayList("Search by Name", "Search by ID"); 
        //studentChoice.setItems(availableChoices);
        //System.out.println(studentChoice);
        //studentChoice.getItems().clear();
        //studentChoice.getItems().addAll("Search by Name", "Search by ID");
       //studentChoice.getSelectionModel().select("Search by ID");

    }
}
