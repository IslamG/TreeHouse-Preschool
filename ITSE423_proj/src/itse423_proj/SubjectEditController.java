/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author New
 */
public class SubjectEditController implements Initializable {
    
    @FXML
    private VBox root;
    @FXML
    private Label subjectIdField;
    @FXML
    private TableView<MarkTable> markTable;
    @FXML
    private TableColumn<MarkTable, String> numCol;
    @FXML
    private TableColumn<MarkTable, String> nameCol;
    @FXML
    private TableColumn<String, String> mid1Col;
    @FXML
    private TableColumn<String, String> mid2Col;
    @FXML
    private TableColumn<String, String> finalCol;
    @FXML
    private TableColumn<MarkTable, String> totalCol;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private static String searchSubById, searchSubByTeach, teacherName;
    private ArrayList<String> myList = new ArrayList<>();
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
    
    @FXML
    void initialize(String a, String b, String c) throws IOException{
        //initilize with search values
        this.searchSubById=a;
        this.searchSubByTeach=b;
        this.teacherName=c;
    }

    private void setUp(){
        try {
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection)dbc.connect();
            if (searchSubById!=null){
                ResultSet rs2=conn.createStatement().executeQuery("select fname from user, marks where user.user_id=marks.prof_id and marks.course_id='"
                        +searchSubById+"'");
                if(rs2.next()){
                    searchSubByTeach=rs2.getString("fname");
                    System.out.println(searchSubByTeach);
                }
                else{
                    displayError();
                    mHead="Something's not right";
                    mCont="You're not authorized to view this data";
                    showAlert(1);
                }
            }
            if (!teacherName.equalsIgnoreCase(searchSubByTeach)){
                   //isn't teacher 
                   displayError();
                   mHead="Something's not right";
                   mCont="You're not authorized to view this data";
                   showAlert(1);
                }
            else{
                //is teacher
                String sql="select u.user_id as tId,u.fname as tName, s.fname as stdName,s.lname as stdLast, s.student_id as stId, "
                        + "m.mark, course_id as cId from user u inner join marks m on u.user_id=m.prof_id "
                        + "inner join student s on m.std_id=s.student_id where u.fname=? ";
                PreparedStatement ps= conn.prepareStatement(sql);
                ps.setString(1,teacherName);
                ResultSet rs=ps.executeQuery();
                if(!rs.next()){
                    //no results
                    displayError();
                    mHead="Something's not right";
                    mCont="Couldn't find any results";
                    showAlert(1);
                }
                else{
                    //put retrieved table data in list to be viewed
                    ObservableList<MarkTable> data=FXCollections.observableArrayList();
                    while (rs.next()){
                        //get student full name
                        String name= rs.getString("stdName")+" "+rs.getString("stdLast");
                        //create a row (custome class) with data
                        MarkTable row=new MarkTable(parseInt(rs.getString("stId")),name,rs.getString("mark"));
                        //setting row values in columns (filling horizontally then vertically) 
                        numCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getIndex()));
                        nameCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
                        totalCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMark()));
                        data.add(row);
                        markTable.setItems(data);
                        }
                    markTable.getColumns().removeAll(numCol,nameCol,totalCol);
                    markTable.getColumns().add(0, numCol);
                    markTable.getColumns().add(1, nameCol);
                    markTable.getColumns().add(5, totalCol);
                    mid1Col.setCellFactory( TextFieldTableCell.<String>forTableColumn());
                    mid2Col.setCellFactory( TextFieldTableCell.<String>forTableColumn());
                    finalCol.setCellFactory( TextFieldTableCell.<String>forTableColumn());
                    mid1Col.setOnEditCommit( t -> {
                            Object o=t.getTableView().getItems().get(t.getTablePosition().getRow());
                            MarkTable m= (MarkTable)o;
                            m.setMid1(t.getNewValue());
                            sumVal(m);
                            //((MarkTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
                    });
                    mid2Col.setOnEditCommit( t -> {
                            Object o=t.getTableView().getItems().get(t.getTablePosition().getRow());
                            MarkTable m= (MarkTable)o;
                            m.setMid2(t.getNewValue());
                            sumVal(m);
                    });
                    finalCol.setOnEditCommit( t -> {
                            Object o=t.getTableView().getItems().get(t.getTablePosition().getRow());
                            MarkTable m= (MarkTable)o;
                            m.setFinalExam(t.getNewValue());
                            sumVal(m);
                    });
                }     
             }
            dbc.disconnect();
            }
                catch (SQLException ex) {
                    Logger.getLogger(SubjectEditController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    private void sumVal(MarkTable mt){
        String m1=mt.getMid1(), m2=mt.getMid2(),m3=mt.getFinalExam();
        if (m1==null)
            m1="0";
        if (m2==null)
            m2="0";
        if (m3==null)
            m3="0";
        mt.setMark(String.valueOf(parseInt(m1)+parseInt(m2)+parseInt(m3)));
        mt.setMid1(m1);
        mt.setMid2(m2);
        mt.setFinalExam(m3);
        System.out.println(mt.getMark());
        markTable.getColumns().remove(5);
        markTable.getColumns().add(5,totalCol);
        //totalCol.setCellValueFactory(c-> new SimpleStringProperty(mt.getMark()));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUp();
    }    

    @FXML
    private void save(ActionEvent event) {
        String id, newMark;
        DatabaseConfig dbc=new DatabaseConfig();
        Connection conn=(Connection) dbc.connect();
        String sql=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        for(int i=0;i<markTable.getItems().size();i++){
            try {
                id=numCol.getCellObservableValue(i).getValue();
                newMark=totalCol.getCellObservableValue(i).getValue();
                System.out.println(id+" "+newMark);
                sql="update marks set mark=? where std_id=?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, newMark);
                ps.setString(2, id);
                if(ps.executeUpdate()>0){
                    displayError();
                    mHead="Complete";
                    mCont="Data updated successfully";
                    showAlert(2);
                }
                else{
                    displayError();
                    mHead="Something's not right";
                    mCont="Data didn't update";
                   showAlert(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SubjectEditController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    @FXML
    private void cancel(ActionEvent event) {
            mid1Col.setCellValueFactory(c-> new SimpleStringProperty("0"));
            mid2Col.setCellValueFactory(c-> new SimpleStringProperty("0"));
            finalCol.setCellValueFactory(c-> new SimpleStringProperty("0"));
            markTable.getColumns().remove(5);
            markTable.getColumns().add(5,totalCol);
    }
    private void displayError(){
        System.out.println("error");
        //root.getChildren().clear();
        String m="Oops something went wrong, can't find student with ";
        if(searchSubById==null){
            m+="name: "+searchSubByTeach;
            }
        else{
            m+="ID: "+searchSubById;
            }
        Label l=new Label(m);
        l.getStyleClass().addAll("utilityLabel", "errorPane");
        VBox h=new VBox(l);
        h.alignmentProperty().set(Pos.CENTER);
        h.fillWidthProperty().set(true);
        h.getStyleClass().addAll("errorBox");
        VBox.setVgrow(h, Priority.ALWAYS);
        //searchSubById="";
        //searchSubByTeach="";
        //root.getChildren().add(h);
        StackPane s=new StackPane();
        s.getChildren().addAll(root.getChildren());
        s.getChildren().add(h);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(s);
    }
}
