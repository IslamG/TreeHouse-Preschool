/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author New
 */
public class MarkSearchController implements Initializable {
    
    @FXML
    private TableView<MarkTable> markTable;
    @FXML
    private TableColumn<MarkTable, String> stdNo;
    @FXML
    private TableColumn<MarkTable, String> stdName;
    @FXML
    private TableColumn<MarkTable, String> stdGrade;
    @FXML
    private Label subjectNameField;
    @FXML
    private Label teacherNameField;
    @FXML
    private BorderPane root;
    @FXML
    private VBox overlay;
    @FXML
    private HBox outerLay;
    
    private static String searchSubById, searchSubByTeach, teacherName;
    private ArrayList<String> myList = new ArrayList<>();
    PieChart chart=new PieChart();
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
            // get subject data and fill fields
            DatabaseConfig dbc=new DatabaseConfig();
            Connection conn=(Connection) dbc.connect();
            String sql=null, getCourseName=null, getProfId=null,getProfName=searchSubByTeach, 
                    getCourseId=searchSubById, stdId, name=null;
            ResultSet rs;
            PreparedStatement ps=null;
            if (searchSubById==null){
                //sequencial queries instead of tying tables
                //get prof_id using name search value
                sql="Select user_id from user where fname='"+searchSubByTeach+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getProfId=rs.getString(1);
                //get course_id name using prof_id
                sql="Select course_id from marks where prof_id='"+getProfId+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getCourseId=rs.getString(1);
                //get course_name using course_id
                sql="Select course_name from course where course_id='"+getCourseId+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getCourseName=rs.getString(1);
                //search for info data
                sql="Select * from marks inner join student on marks.std_id=student.student_id where prof_id="+getProfId;
                getProfName=searchSubByTeach;
                
            }
            else{
                //sequencial queries instead of tying tables
                //get course_name using course_id from search
                sql="Select course_name from course where course_id='"+searchSubById+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getCourseName=rs.getString(1);
                //get prof_id using course_id
                sql="Select prof_id from marks where course_id='"+searchSubById+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getProfId=rs.getString(1);
                //get prof_name using prof_id
                sql="Select fname from user where user_id='"+getProfId+"'";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                if (rs.next())
                    getProfName=rs.getString(1);
                //search for info data
                sql="Select * from marks inner join student on marks.std_id=student.student_id where marks.course_id="+searchSubById;
                
            }
            //set header values
            subjectNameField.setText(getCourseName);
            teacherNameField.setText(getProfName);
            
            ps=conn.prepareStatement(sql);
            //Get subject info
            rs=ps.executeQuery();
            if (rs.next()){
                int i=0;
                //put retrieved table data in list to be viewed
                ObservableList<MarkTable> data=FXCollections.observableArrayList();
                ObservableList<PieChart.Data>list=FXCollections.observableArrayList();
                while (rs.next()){
                    //get student full name
                    name= rs.getString("fname")+" "+rs.getString("lname");
                    i++;
                    //create a row (custome class) with data
                    MarkTable row=new MarkTable(i,name,rs.getString("mark"));
                    //setting row values in columns (filling horizontally then vertically) 
                    stdNo.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getIndex()));
                    stdName.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
                    stdGrade.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMark()));
                    
                    data.add(row);
                    markTable.setItems(data);
                    //create pie chart from data
                    list.add(new PieChart.Data(row.getName(),Double.valueOf(row.getMark())));
                } 
                //add to table
                markTable.getColumns().clear();
                markTable.getColumns().addAll(stdNo,stdName,stdGrade);
                chart.setTitle(getCourseName);
                chart.setStartAngle(180);
                chart.setData(list);
            }
            else{
                //display error message in place of usual children
                mHead="Something went wrong";
                mCont="No data found";
                showAlert(1);
                root.getChildren().clear();
                String m="Oops something went wrong, can't find subject with ";
                if(searchSubById==null){
                    m+="Prof. name: "+searchSubByTeach;
                }
                else{
                    m+="Subject ID: "+searchSubById;
                }
                Label l=new Label(m);
                l.getStyleClass().addAll("utilityLabel", "errorPane");
                VBox h=new VBox(l);
                h.alignmentProperty().set(Pos.CENTER);
                h.fillWidthProperty().set(true);
                h.getStyleClass().addAll("errorBox");
                VBox.setVgrow(h, Priority.ALWAYS);
                searchSubById="";
                searchSubByTeach="";
                root.setCenter(h);
                
            }
            dbc.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    //dynamically create chart screen with data and buttnos
    private void getChart(){
        Button btn= new Button("Save");
        btn.setOnAction(e-> saveInfo());
        btn.getStyleClass().add("utilityButton");
        Button btn2= new Button("Back");
        btn2.setOnAction(e-> back());
        btn2.getStyleClass().add("utilityButton");
        
        overlay.getChildren().addAll(chart,btn);
        outerLay.getChildren().add(0,btn2);
        outerLay.setVisible(true);
    }
    //output chart screen to PDF or Printer
    private void saveInfo(){
        PrinterJob job = PrinterJob.createPrinterJob();
        //System.out.println("Doing");
        if(job != null){
            job.showPrintDialog(root.getScene().getWindow()); 
            Pane frame=new Pane(chart);
            job.printPage(frame);
            job.endJob();
            overlay.getChildren().add(0,chart);
        }
    }
    //hide and disable chart screen
    private void back(){
        overlay.getChildren().removeAll(overlay.getChildren());
        outerLay.getChildren().removeAll(outerLay.lookup(".button"));
        outerLay.setVisible(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        setUp();
    }
    
    
}
