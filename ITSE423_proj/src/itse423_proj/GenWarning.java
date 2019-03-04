/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author New
 */

//create a warning HBox
//Custom Class for dynamically generating warning in AdminPageController on initialize
public class GenWarning {
    
    private Hyperlink remove=new Hyperlink();
    private HBox note=new HBox();
    private Label warning=new Label();
    
    //initialize with warning text string from insttantation
    GenWarning(String s){
        warning.setText(s);
        remove.setText("x");
        //remove.setOnAction(e->removeNotification());
        remove.setStyle("-fx-background-color:red;-fx-text-fill:white;-fx-background-radius:50%;"+
                "-fx-text-decoration:none;-fx-padding:1px 4px;");
        note= new HBox(20);
        note.setStyle("-fx-border-color:rgb(75,75,75);-fx-padding:2px;");
        note.setFillHeight(true);
        note.setAlignment(Pos.CENTER_RIGHT);
        note.getChildren().addAll(warning, remove);
    }
    //return warning as HBox
    public HBox getWarning(){
        return note;
    }
}
