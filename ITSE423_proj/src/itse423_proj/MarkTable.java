/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;

/**
 *
 * @author New
 */

//Generate a row containing data
//Custom class for filling TableView in MarkSearchController initialize method
public class MarkTable {
    private int index;
    private String stdName;
    private String mark;

//initialize values from creation
    MarkTable(int n, String a, String b){
        this.index=n;
        this.stdName=a;
        this.mark=b;
    }
    //return set index
    public String getIndex(){
        return String.valueOf(this.index);
    }
    //return set name
    public String getName(){
        return this.stdName;
    }
    //return set mark
    public String getMark(){
        return this.mark;
    }
}
