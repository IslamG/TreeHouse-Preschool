package itse423_proj;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPageController {

    @FXML
    private Pane header;
    @FXML
    private AnchorPane staffInfoAnch, content;
    @FXML
    private TabPane staffTab;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button addUser, removeUser, editTeacher, removeTeahcer, changePassword, updateInfo;
    @FXML
    private TabPane teacherTab;
    @FXML
    private Accordion selfAccordion;
    @FXML
    private TitledPane userPane, passPane, otherPane;
    @FXML
    private ComboBox <String> studentChoice, subjectChoice;
     
    public final String userName, userPassword;
    private static String uName, uPass;
    
    //initiate username and passowrd(cannot be changed later)
    public AdminPageController() {
        this.userName = uName;
        this.userPassword = uPass;
        print();
    }
    private void print(){
        System.out.println(userName+" "+userPassword);
    }
    @FXML
    private void mouseActive(){
        Scene scene = content.getScene();
        Image image = new Image(this.getClass().getResourceAsStream("pencil2.png"));  //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }
    @FXML
    private void mouseInactive(){
        Scene scene = content.getScene();
        Image image = new Image(this.getClass().getResourceAsStream("pencil.png"));  //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }
    
    @FXML
    private void manageSelf(ActionEvent event){
        try 
        {
            AnchorPane newLoadedPane;
            
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {
                //load UpdateSelf with username pane open 
                //Still doesn't work
                case "updateInfo":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    selfAccordion=(Accordion)newLoadedPane.getChildren().get(0);
                    
                    userPane=(TitledPane) newLoadedPane.lookup("#userPane");
                    selfAccordion.setExpandedPane(userPane);
                    //userPane.setExpanded(true);
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                //load UpdateSelf with password pane open 
                //Still doesn't work
                case "changePassword":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    selfAccordion=(Accordion)newLoadedPane.getChildren().get(0);
                    selfAccordion.setExpandedPane(passPane);
                    //passPane.setExpanded(true);
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
    private void manageEmployee(ActionEvent event)
    {
        try 
        {
            Pane newLoadedPane;
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {
                //load StaffInfo with add user pane open 
                case "addUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                //load StaffInfo with add user pane open 
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
                //load TeacherInfo with edit teacher pane open 
                case "editTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                //load TeacherInfo with remove teacher pane open 
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
            //load StudentInfo using name value to search 
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
            //load StudentInfor using subject ID to search
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
    private void logOut(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        Node source = (Node) event.getSource();
        Stage stage2 = (Stage) source.getScene().getWindow();
        stage2.close();
    }
    @FXML
    //source value of username and password
    void initialize (String a, String b) {
        this.uName = a;
        this.uPass = b;
    }
}
