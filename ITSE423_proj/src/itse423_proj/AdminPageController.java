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
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPageController {

    @FXML
    private Pane header;
    
    @FXML
    private Pane content;
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
            
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {

                case "addUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    //content.setPrefSize(newLoadedPane.getPrefWidth(), newLoadedPane.getPrefHeight());
                    System.out.println(content.getHeight()+" "+content.getWidth());
                    content.getChildren().add(newLoadedPane);
                    //Node source = (Node) event.getSource();
                    AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
                    AnchorPane.setTopAnchor(newLoadedPane, 0.0);
                    AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
                    AnchorPane.setRightAnchor(newLoadedPane, 0.0);
                    //System.out.println(newLoadedPane.getPrefWidth()+" "+ newLoadedPane.getPrefHeight());
                    borderPane.prefHeightProperty().bind(borderPane.getScene().heightProperty());
                    borderPane.prefWidthProperty().bind(borderPane.getScene().widthProperty());
                    Stage stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    //AnchorPane.setRightAnchor((Node) content.getChildren().get(0), content.getWidth());
                    break;
                case "removeUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    staffTab=(TabPane)newLoadedPane.getChildren().get(0);
                    staffTab.getSelectionModel().selectLast();
                    content.setPrefSize(600, 600);
                    //AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
                    //AnchorPane.setTopAnchor(newLoadedPane, 0.0);
                    //AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
                    //AnchorPane.setRightAnchor(newLoadedPane, content.getWidth());
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
                content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                break;
            case "subjectChoice":
                subjectChoice.getValue();
                System.out.println("Search by "+subjectChoice.getValue());
                newLoadedPane =  FXMLLoader.load(getClass().getResource("MarkSearch.fxml"));
                content.getChildren().clear();
                content.getChildren().add(newLoadedPane);
                System.out.println(newLoadedPane);
                content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                break;
        }
    }

    @FXML
    void initialize() {
        //Image image = new Image(input); 
   
        // create a image View 
        //ImageView imageview = new ImageView(image); 
   
        // create Label 
         //Label label = new Label("", imageview); 
            
        //ObservableList<String> availableChoices = FXCollections.observableArrayList("Search by Name", "Search by ID"); 
        //studentChoice.setItems(availableChoices);
        //System.out.println(studentChoice);
        //studentChoice.getItems().clear();
        //studentChoice.getItems().addAll("Search by Name", "Search by ID");
       //studentChoice.getSelectionModel().select("Search by ID");

    }
}
