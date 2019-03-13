package itse423_project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class DBcon {  
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn = null;
       
    public static Connection getonnection(){
        try{
            Class.forName(JDBC_DRIVER);
            String DB_URL = "jdbc:mysql://localhost/treehouseschool";          
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected");    
        }
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBcon.class.getName()).log(Level.SEVERE, null, ex);
        }
    return conn; 
    }
    
    public static List<DBinfo> getloginfo(){
        List<DBinfo> list=new ArrayList<DBinfo>();
        try{
            String sql="select user_id, fname, job from user;";
            Connection con =DBcon.getonnection();
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
                DBinfo loginfo= new DBinfo();
                loginfo.setuserid(rs.getInt(1));
                loginfo.setUfirstname(rs.getString(2));
                loginfo.setjob(rs.getString(3));
                list.add(loginfo);
            }
            con.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(DBcon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            getonnection();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
    public static List<DBinfo> getUserInfo(int id){
        List<DBinfo> list=new ArrayList<DBinfo>();
        try {
            String sql="Select * from user where user_id=?";
            getonnection();
            PreparedStatement prestmt=(PreparedStatement) conn.prepareStatement(sql);
            prestmt.setInt(id, id);
            ResultSet rs= prestmt.executeQuery();
            if(rs.next()){
                DBinfo userinfo=new DBinfo(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                list.add(userinfo);
            }
            prestmt.close();
            conn.close();   
        } catch (SQLException ex) {
            Logger.getLogger(DBcon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;     
    }
}