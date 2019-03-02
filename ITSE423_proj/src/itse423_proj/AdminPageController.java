package itse423_proj;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    private ImageView adminImage;
    @FXML
    private TextField studentSearchField, subjectSearchField;
    @FXML
    private Label welcomeLabel;
     
    public final String userName, userPassword;
    private static String uName, uPass;
    public String searchStdByName, searchStdById, searchSubById, searchSubByTeach;
    private Alert alert=new Alert(AlertType.ERROR);
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
                case "updateInfo":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    selfAccordion=(Accordion)newLoadedPane.getChildren().get(0);
                    selfAccordion.setExpandedPane(selfAccordion.getPanes().get(0));
                    stage = (Stage) content.getScene().getWindow();
                    stage.sizeToScene();
                    break;
                //load UpdateSelf with password pane open 
                case "changePassword":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(newLoadedPane);
                    selfAccordion=(Accordion)newLoadedPane.getChildren().get(0);
                    selfAccordion.setExpandedPane(selfAccordion.getPanes().get(1));
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
        Boolean allGood=false;
        String searchType;
        Pane newLoadedPane;
        switch(switcher.getId())
        {   
            //load StudentInfo using name value to search 
            case "studentChoice":
                searchType=studentChoice.getValue();
                System.out.println("Search by "+studentChoice.getValue());
                if(studentSearchField.getText().equals("")){
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Field Cannot Be Empty");
                    alert.setContentText("Please input student name or ID");
                    alert.showAndWait();
                    studentSearchField.setStyle("-fx-border-color:red;");
                }
                else{
                    studentSearchField.setStyle("-fx-border-color:none;");
                    if(searchType.equalsIgnoreCase("search by ID")){

                        if (isParsable(studentSearchField.getText())){
                            searchStdById=studentSearchField.getText();
                            allGood=true;
                        }
                        else{
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Search Type Mismatch");
                            alert.setContentText("Check search type and input value and try again");
                            alert.showAndWait();
                            studentSearchField.setStyle("-fx-border-color:red;");
                        }
                    
                    }
                    else{
                        if (!isParsable(studentSearchField.getText())){
                        searchStdByName=studentSearchField.getText();
                        allGood=true;
                        }
                        else{
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Search Type Mismatch");
                            alert.setContentText("Check search type and input value and try again");
                            alert.showAndWait();
                            studentSearchField.setStyle("-fx-border-color:red;");
                        }
                    }
                    if(allGood){
                        //create instance of the controller and initiate variables
                        StudentInfoController stc=(StudentInfoController)new StudentInfoController();
                        stc.initialize(searchStdById, searchStdByName);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentInfo.fxml"));
                        fxmlLoader.setController(stc);
                        newLoadedPane = fxmlLoader.load();
                        content.getChildren().clear();
                        content.getChildren().add(newLoadedPane);
                        content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                        stage = (Stage) content.getScene().getWindow();
                        stage.sizeToScene();
                    }
                }
                break;
            //load SubjectInfo using subject ID to search
            case "subjectChoice":
                searchType=subjectChoice.getValue();
                if(subjectSearchField.getText().equals("")){
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Field Cannot Be Empty");
                    alert.setContentText("Please input Subject ID or Teacher Name");
                    alert.showAndWait();
                    subjectSearchField.setStyle("-fx-border-color:red;");
                }
                else{
                    subjectSearchField.setStyle("-fx-border-color:none;");
                    if(searchType.equalsIgnoreCase("subject code")){

                        if (isParsable(subjectSearchField.getText())){
                            searchSubById=subjectSearchField.getText();
                            allGood=true;
                        }
                        else{
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Search Type Mismatch");
                            alert.setContentText("Check search type and input value and try again");
                            alert.showAndWait();
                            subjectSearchField.setStyle("-fx-border-color:red;");
                        }
                    
                    }
                    else{
                        if (!isParsable(subjectSearchField.getText())){
                        searchSubByTeach=subjectSearchField.getText();
                        allGood=true;
                        }
                        else{
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Search Type Mismatch");
                            alert.setContentText("Check search type and input value and try again");
                            alert.showAndWait();
                            subjectSearchField.setStyle("-fx-border-color:red;");
                        }
                    }
                    if(allGood){
                        //create instance of the controller and initiate variables
                        MarkSearchController msc=(MarkSearchController)new MarkSearchController();
                        msc.initialize(searchSubById, searchSubByTeach);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MarkSearch.fxml"));
                        fxmlLoader.setController(msc);
                        newLoadedPane = fxmlLoader.load();
                        content.getChildren().clear();
                        content.getChildren().add(newLoadedPane);
                        content.setPrefSize(newLoadedPane.getWidth(), newLoadedPane.getHeight());
                        stage = (Stage) content.getScene().getWindow();
                        stage.sizeToScene();
                    }
                    
                break;
            }
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
    private boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(Exception e){
            parsable = false;
        }
        return parsable;
    }
    public void initialize (){
        try {
            setUp();
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setUp() throws SQLException{
        DatabaseConfig dbc= new DatabaseConfig();
        Connection conn=(Connection) dbc.connect();
        String sql="Select * from user where fname=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,userName);
        ResultSet rs=ps.executeQuery();
        if(!rs.next()){
            alert.setTitle("ERROR");
            alert.setHeaderText("Something went wrong");
            alert.showAndWait();
        }
        else{
            String gen=rs.getString("gender");
            if (gen.equalsIgnoreCase("male")){
                Image img=new Image(this.getClass().getResourceAsStream("images5NA3WNBL.png"));
                adminImage.setImage(img);
            }
            else{
                Image img=new Image(this.getClass().getResourceAsStream("User_Profile_Page_008.png"));
                adminImage.setImage(img);
            }
        }
        dbc.disconnect();
    }
    /*public String getSearchForStudent(){
        System.out.println(studentSearchField.getText());
        return studentSearchField.getText();
    }
    public String getSearchForSubject(){
        return subjectSearchField.getText();
    }*/
}
