/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse423_proj;
//Islam Omar Ghretlli
//215185139
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
    private String mid1;
    private String mid2;
    private String finalExam;

//initialize values from creation
    MarkTable(int n, String a, String b){
        this.index=n;
        this.stdName=a;
        this.mark=b;
    }
    MarkTable(int n, String a, String b, String c, String d, String e){
        this.index=n;
        this.stdName=a;
        this.mark=b;
        this.mid1=c;
        this.mid2=d;
        this.finalExam=e;
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
    public String getMid1(){
        return this.mid1;
    }
    public String getMid2(){
        return this.mid2;
    }
    public String getFinalExam(){
        return this.finalExam;
    }
    public void setMark(String s){
        this.mark=s;
    }
    public void setMid1(String s){
        this.mid1=s;
    }
    public void setMid2(String s){
        this.mid2=s;
    }
    public void setFinalExam(String s){
        this.finalExam=s;
    }
}
