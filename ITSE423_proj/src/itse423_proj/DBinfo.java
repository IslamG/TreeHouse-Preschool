
package itse423_project2;

import java.util.Date;

public class DBinfo {
    //user table
    private int userid;
    private String Ufirstname;
    private String Usecoundname;
    private String Ulastname;
    private String Umomname;
    private String Uaddress;
    private int Ussn;
    private String hirringdate;
    private String dep;
    private String job;
    private String Ugender;
    
    //class table
    private int classid;
    private String classname;
    private String plase;
    
    //course table
    private int courseid;
    private String coursename;
    private String des;
    
    //schedulae table
    private String day;
    
    // student table
    private int studentid;
    private String Sfirstname;
    private String Ssecoundname;
    private String Slastname;
    private String Smomname;
    private String Saddress;
    private Date birthdate;
    private String plasebirth;
    private Date storydate;
    private int fhaterPhonNumber;
    private int Sssn;
    private String Sgender;
    //private img;
    private String notion;
    
   
    public DBinfo(){
        super();
    }
    
    public  DBinfo(int userid,String Ufirstname,String Usecoundname,String Ulastname,String Umomname,String Uaddress,
            int Ussn, String hirringdate, String dep, String job,String Ugender)
    {
        super();
        this.userid=userid;
        this.Ufirstname=Ufirstname;
        this.Usecoundname=Usecoundname;
        this.Ulastname=Ulastname;
        this.Umomname=Umomname;
        this.Uaddress=Uaddress;
        this.Ussn=Ussn;
        this.hirringdate=hirringdate;
        this.dep=dep;
        this.job=job;
        this.Ugender=Ugender;
    }

    public  DBinfo(int userid,String Ufirstname, String job)
    {
        super();
        this.userid=userid;
        this.Ufirstname=Ufirstname;
        this.job=job;
    }
    
    public int getuserid(){
        return userid;
    }
    public void setuserid(int id){
        this.userid=id;
    }
        
    public String getUfirstname(){
        return Ufirstname;
    }
    public void setUfirstname(String name){
        this.Ufirstname=name;
    }
        
    public String getUsecoundname(){
        return Usecoundname;
    }
    public void setUsecoundname(String name){
        this.Usecoundname=name;
    }
        
    public String getUlastname(){
        return Ulastname;
    }
    public void setUlastname(String name){
        this.Ulastname=name;
    }    
        
    public String getUmomname(){
        return Umomname;
    }
    public void setUmomname(String name){
        this.Umomname=name;
    }
    
    public String getUaddress(){
        return Uaddress;
    }
    public void setUaddress(String name){
        this.Uaddress=name;
    }
    
    public int getUssn(){
        return Ussn;
    }
    public void setUssn(int id){
        this.Ussn=id;
    }

    public String gethirringdate(){
        return hirringdate;
    }
    public void sethirringdate(String name){
        this.hirringdate=name;
    }
    
    public String getdep(){
        return dep;
    }
    public void setdep(String name){
        this.dep=name;
    }
    
    public String getjob(){
        return job;
    }
    public void setjob(String name){
        this.job=name;
    }
    
    public String getUgender(){
        return Ugender;
    }
    public void setUgender(String name){
        this.Ugender=name;
    }   
}