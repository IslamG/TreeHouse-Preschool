/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private static int num=0;
    
    //initialize with warning text string from insttantation
    GenWarning(String s){
        warning.setText(s);
        ImageView img=new ImageView(new Image(this.getClass().getResourceAsStream("small-red-x-mark-md.png")));
        img.setFitHeight(20);
        img.setFitWidth(20);
        remove.setGraphic(img);
        remove.setOnAction(e->removeNotification(e));
        note= new HBox(20);
        note.setStyle("-fx-border-color:rgb(75,75,75);-fx-padding:2px;");
        note.setFillHeight(true);
        note.setAlignment(Pos.CENTER_RIGHT);
        note.getChildren().addAll(warning, remove);
        num++;
    }
    //return warning as HBox
    public HBox getWarning(){
        return note;
    }
    public int getWarningCount(){
        return num;
    }
    private void removeNotification(ActionEvent e){
        VBox v=(VBox) this.getWarning().getParent();
        Hyperlink h= (Hyperlink) e.getTarget();
        v.getChildren().remove(h.getParent());
        num--;
    }
}
