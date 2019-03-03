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
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author New
 */
public class MarkSearchController implements Initializable {

    @FXML
    private TableColumn<ResultSet, String> stdNo;
    @FXML
    private TableColumn<String, String> stdName;
    @FXML
    private TableColumn<String, String> stdGrade;
    @FXML
    private Label subjectNameField;
    @FXML
    private Label teacherNameField;
    @FXML
    private AnchorPane root;
    
    private static String searchSubById, searchSubByTeach;
    

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
            String sql=null, getCourseName=null, getProfId=null,getProfName=null, getCourseId=null;
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
                sql="Select * from marks where prof_id="+getProfId;
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
                sql="Select * from marks where course_id="+searchSubById;
                
            }
            //set header values
            subjectNameField.setText(getCourseName);
            teacherNameField.setText(getProfName);
            
            ps=conn.prepareStatement(sql);
            //Get subject info
            rs=ps.executeQuery();
            if (rs.next()){
                System.out.println("TODO populate list");
                //ObservableList<String> data=FXCollections.observableArrayList();
                //data.addAll((Collection<String>)rs.getArray("std_id"));
                stdNo.setCellValueFactory(
                    new PropertyValueFactory<ResultSet, String>("std_id"));
                //System.out.println(stdNo.);

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
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        setUp();
    }
    
    
}
