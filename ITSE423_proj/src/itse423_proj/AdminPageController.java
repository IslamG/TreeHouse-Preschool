package itse423_proj;
//Islam Omar Ghretlli
//215185139
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminPageController {

    @FXML
    private Pane aniPane;
    @FXML
    private HBox header;
    @FXML
    private VBox content, notificationPane;
    @FXML
    private TabPane staffTab;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button addUser, removeUser, editTeacher, removeTeahcer, changePassword, updateInfo;
    @FXML
    private TabPane teacherTab;
    @FXML
    private Accordion selfAccordion, sideAccordion;
    @FXML
    private TitledPane userPane, passPane, otherPane, warningPane, mPersonal,mTeach,mEmp,mStd,mSub;
    @FXML
    private MenuButton studentChoice, subjectChoice;
    @FXML
    private ImageView adminImage;
    @FXML
    private TextField studentSearchField, subjectSearchField;
    @FXML
    private Label welcomeLabel, qLabel, nameLabel, titleLabel;
    @FXML
    private Polygon drawpath;
     
    public final String userName, userPassword;
    private static String uName, uPass;
    private int warningNum=0;
    private String mHead, mCont;
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
    private void manageSelf(ActionEvent event){
        try 
        {
            Accordion newLoadedPane;
            
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {
                //load UpdateSelf with username pane open 
                case "updateInfo":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    newLoadedPane.setExpandedPane(newLoadedPane.getPanes().get(0));
                    break;
                //load UpdateSelf with password pane open 
                case "changePassword":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("UpdateSelf.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    newLoadedPane.setExpandedPane(newLoadedPane.getPanes().get(1));
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
            TabPane newLoadedPane;
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {
                //load StaffInfo with add user pane open 
                case "addUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    break;
                //load StaffInfo with add user pane open 
                case "removeUser":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffInfo.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    newLoadedPane.getSelectionModel().selectLast();
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
            VBox newLoadedPane;
            Stage stage;
            Button tempButton = (Button)event.getSource();
            switch(tempButton.getId())
            {
                //load TeacherInfo with edit teacher pane open 
                case "editTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    break;
                //load TeacherInfo with remove teacher pane open 
                case "removeTeacher":
                    newLoadedPane =  FXMLLoader.load(getClass().getResource("TeacherInfo.fxml"));
                    borderPane.setCenter(newLoadedPane);
                    VBox v=(VBox)newLoadedPane.getChildren().get(2);
                    teacherTab=(TabPane)v.getChildren().get(1);
                    teacherTab.getSelectionModel().selectLast();
                    break;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    void searchBy(ActionEvent event, String switcher) throws IOException {
        Stage stage;
        Boolean allGood=false;
        MenuItem src=(MenuItem)event.getSource();
        String searchType=src.getText();
        System.out.println(switcher);
        Pane newLoadedPane;
        switch(switcher)
        {   
            //load StudentInfo using name value to search 
            case "studentChoice":
                if(studentSearchField.getText().equals("")){
                    mHead="Field Cannot Be Empty";
                    mCont="Please input student Name or ID";
                    showAlert(1);
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
                            mHead="Search Type Mismatch";
                            mCont="Check search type and input value and try again";
                            showAlert(1);
                            studentSearchField.setStyle("-fx-border-color:red;");
                        }
                    
                    }
                    else{
                        if (!isParsable(studentSearchField.getText())){
                        searchStdByName=studentSearchField.getText();
                        allGood=true;
                        }
                        else{
                            mHead="Search Type Mismatch";
                            mCont="Check search type and input value and try again";
                            showAlert(1);
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
                        borderPane.setCenter(newLoadedPane);
                    }
                }
                break;
            //load SubjectInfo using subject ID to search
                case "subjectChoice":
                if(subjectSearchField.getText().equals("")){
                    mHead="Field Cannot Be Empty";
                    mCont="Please input Subject ID or Teacher Name";
                    showAlert(1);
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
                            mHead="Search Type Mismatch";
                            mCont="Check search type and input value and try again";
                            showAlert(1);
                            subjectSearchField.setStyle("-fx-border-color:red;");
                        }
                    
                    }
                    else{
                        if (!isParsable(subjectSearchField.getText())){
                        searchSubByTeach=subjectSearchField.getText();
                        allGood=true;
                        }
                        else{
                            mHead="Search Type Mismatch";
                            mCont="Check search type and input value and try again";
                            showAlert(1);
                            subjectSearchField.setStyle("-fx-border-color:red;");
                        }
                    }
                    if(allGood){
                        //create instance of the controller and initiate variables
                        MarkSearchController msc=(MarkSearchController)new MarkSearchController();
                        msc.initialize(searchSubById, searchSubByTeach,userName);
                        SubjectEditController sec=(SubjectEditController)new SubjectEditController();
                        sec.initialize(searchSubById, searchSubByTeach,userName);
                        String job=titleLabel.getText();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                                job.equalsIgnoreCase("admin") ? "MarkSearch.fxml" : "SubjectEdit.fxml"));
                        fxmlLoader.setController(job.equalsIgnoreCase("admin") ? msc : sec);
                        newLoadedPane = fxmlLoader.load();
                        borderPane.setCenter(newLoadedPane);
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
            setUp();
    }
    private void setUp(){
        try {
            DatabaseConfig dbc= new DatabaseConfig();
            Connection conn=(Connection) dbc.connect();
            String sql="Select * from user where fname=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,userName);
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){
                mHead="Something went wrong";
                mCont="Could not load data";
                showAlert(1);
            }
            else{
                String gen=rs.getString("gender");
                nameLabel.setText(userName.toUpperCase());
                titleLabel.setText(rs.getString("job").toUpperCase());
                if (gen.equalsIgnoreCase("male")){
                    Image img=new Image(this.getClass().getResourceAsStream("images5NA3WNBL.png"));
                    adminImage.setImage(img);
                }
                else{
                    Image img=new Image(this.getClass().getResourceAsStream("User_Profile_Page_008.png"));
                    adminImage.setImage(img);
                }
                //loop through personal information checking for null values
                for (int i=4;i<=10;i++){
                    if (rs.getObject(i)==null){
                        //create warning object and add to warning pane
                        HBox w1=new GenWarning("Your personal information is incomplete").getWarning();
                        w1.getChildren().get(1).addEventFilter(ActionEvent.ACTION, e->removedWarning());
                        warningNum++;
                        notificationPane.getChildren().addAll(w1);
                        warningPane.setText(warningNum+" unread notifications");
                        warningPane.setManaged(true);
                        break;
                    }
                }
                //check if passowrd was changed from default
                if (rs.getString("pass").equals("123")){
                    //create warning object and add to warning pane
                    HBox w2=new GenWarning("Caution: change default password").getWarning();
                    w2.getChildren().get(1).addEventFilter(ActionEvent.ACTION, e->removedWarning());
                    warningNum++;
                    notificationPane.getChildren().addAll(w2);
                    warningPane.setText(warningNum+" unread notifications");
                    warningPane.setManaged(true);
                }
                if(rs.getString("job").equalsIgnoreCase("teacher")){
                    sideAccordion.getPanes().get(1).setManaged(false);
                    sideAccordion.getPanes().get(2).setManaged(false);
                    sideAccordion.getPanes().get(3).setManaged(false);
                    sideAccordion.getPanes().get(1).setVisible(false);
                    sideAccordion.getPanes().get(2).setVisible(false);
                    sideAccordion.getPanes().get(3).setVisible(false);
                }
                else{
                    sideAccordion.getPanes().get(1).setManaged(true);
                    sideAccordion.getPanes().get(2).setManaged(true);
                    sideAccordion.getPanes().get(3).setManaged(true);
                    sideAccordion.getPanes().get(1).setVisible(true);
                    sideAccordion.getPanes().get(2).setVisible(true);
                    sideAccordion.getPanes().get(3).setVisible(true);
                }
            }
            dbc.disconnect();
            //add effects to welcom label
            Bloom bl=new Bloom();
            bl.setThreshold(0.0);
            DropShadow ds = new DropShadow();
            ds.setOffsetY(10);
            ds.setOffsetX(10);
            ds.setSpread(0.2);
            ds.setColor(new Color(0,0,0,0.5));
            ds.setBlurType(BlurType.GAUSSIAN);
            InnerShadow is = new InnerShadow();
            is.setRadius(2);
            is.setColor(Color.BLUEVIOLET);
            is.setOffsetX(2);
            is.setOffsetY(0.8);
            is.setChoke(0.2);
            is.setBlurType(BlurType.TWO_PASS_BOX);
            Reflection r=new Reflection();
            r.setTopOffset(1);
            r.setTopOpacity(1);
            r.setBottomOpacity(0.5);
            
            ds.setInput(bl);
            welcomeLabel.setEffect(ds);
            DropShadow ds2= new DropShadow(BlurType.GAUSSIAN,Color.BLACK,3,0.9,1,1);
            ds.setInput(ds2);
            nameLabel.setEffect(ds);
            r.setInput(is);
            qLabel.setEffect(r);
            mPersonal.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("PIM2.png"))));
            mPersonal.getGraphic().setTranslateX(125);
            mTeach.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("inventory_management2.png"))));
            mTeach.getGraphic().setTranslateX(115);
            mEmp.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("software2.png"))));
            mEmp.getGraphic().setTranslateX(120);
            mStd.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("Student-Search2.png"))));
            mStd.getGraphic().setTranslateX(125);
            mSub.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("schoolmodule2.png"))));
            mSub.getGraphic().setTranslateX(125);
            
            studentChoice.getItems().get(0).setOnAction(e-> {
                try {
                    searchBy(e, "studentChoice");
                } catch (IOException ex) {
                    Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            studentChoice.getItems().get(1).setOnAction(e-> {
                try {
                    searchBy(e, "studentChoice");
                } catch (IOException ex) {
                    Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            subjectChoice.getItems().get(0).setOnAction(e-> {
                try {
                    searchBy(e, "subjectChoice");
                } catch (IOException ex) {
                    Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            subjectChoice.getItems().get(1).setOnAction(e-> {
                try {
                    searchBy(e, "subjectChoice");
                } catch (IOException ex) {
                    Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            animatedBackground();
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void animatedBackground(){
        Rectangle r1=new Rectangle(100,100);
        r1.relocate(0, 0);
        Timeline t=new Timeline();
        KeyValue kv1=new KeyValue(r1.arcHeightProperty(),100);
        KeyValue kv2=new KeyValue(r1.arcWidthProperty(),100);
        KeyFrame kf1= new KeyFrame(Duration.seconds(3),kv1,kv2);
        Polygon p=new Polygon();
        PathTransition pathTransition=new PathTransition();
        pathTransition.setDuration(Duration.millis(15000));
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setNode(r1);
        pathTransition.setPath(p);
        aniPane.heightProperty().addListener((obs, oldVal, newVal)->{
            Double w=aniPane.getWidth()-70, h=aniPane.getHeight()-50;
            p.getPoints().clear();
            p.getPoints().addAll((2*w)/3,h, (2*w)/3,50.0, w,h/3, 70.0,h/3, 
                                        w/3,50.0, w/3,h, 70.0,(2*h)/3, w,(2*h)/3);
            pathTransition.playFromStart();
        });
        FillTransition ft=new FillTransition(Duration.seconds(5),r1);
        ft.setFromValue(Color.CORAL);
        ft.setToValue(Color.CORNFLOWERBLUE);
        ft.setCycleCount(FillTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
        aniPane.getChildren().addAll(r1);
        r1.toBack();
        t.getKeyFrames().addAll(kf1);
        t.setAutoReverse(true);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }
    //update titled pane with new number of warnings
    private void removedWarning(){
        warningNum--;
        warningPane.setText(warningNum+" unread notifications");
        if (warningNum==0){
            warningPane.setManaged(false);
            warningPane.setVisible(false);
        }
    }
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
}
