/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    private AnchorPane root;
    @FXML
    private Pane overlay;
    
    private static String searchSubById, searchSubByTeach;
    private ArrayList<String> myList = new ArrayList<>();
    PieChart chart=new PieChart();

    @FXML
    void initialize(String a, String b) throws IOException{
        //initilize with search values
        this.searchSubById=a;
        this.searchSubByTeach=b;
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
                root.getChildren().clear();//.remove(root.lookup("#notFound"));
                String m="Oops something went wrong, can't find subject with ";
                if(searchSubById==null){
                    m+="Prof. name: "+searchSubByTeach;
                }
                else{
                    m+="Subject ID: "+searchSubById;
                }
                Label l=new Label(m);
                l.setId("notFound");
                l.setAlignment(Pos.CENTER);
                l.setTranslateY(90);
                l.setTranslateX(20);
                l.setStyle("-fx-font-size:15px;");
                searchSubById=null;
                searchSubByTeach=null;
                root.getChildren().add(l);
                
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
        btn.setLayoutX(250); 
        btn.setLayoutY(428.0);
        btn.setOnAction(e-> saveInfo());
        btn.setStyle("-fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family:'Times New Roman'; -fx-font-size:15px;");
        Button btn2= new Button("Back");
        btn2.setLayoutX(25); 
        btn2.setLayoutY(25);
        btn2.setOnAction(e-> back());
        btn2.setStyle("-fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family:'Times New Roman'; -fx-font-size:15px;");
        overlay.getChildren().addAll(chart,btn,btn2);
        overlay.setManaged(true);
        overlay.setVisible(true);
    }
    //output chart screen to PDF or Printer
    private void saveInfo(){
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Doing");
        if(job != null){
            job.showPrintDialog(root.getScene().getWindow()); 
            job.printPage(overlay);
            job.endJob();
        }
    }
    //hide and disable chart screen
    private void back(){
        overlay.getChildren().removeAll(overlay.getChildren());
        overlay.setVisible(false);
        overlay.setManaged(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        setUp();
    }
    
    
}
