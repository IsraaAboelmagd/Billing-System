/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.cdr;

import com.iti.billing.Billing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DB {
    PreparedStatement preparedStatement=null;
    String sql = new String("insert into CDR (imsi,msisdn,start_call,end_call,duration,destination,service) values (?,?,?,?,?,?,?)");
     public DB(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/postgres","postgres","15987");
            preparedStatement = con.prepareStatement(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(Billing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void insertCDR(List<CDR> cdrs){
          for(CDR cdr:cdrs){
              
              try {
                  preparedStatement.setString(1, cdr.getIMSI());
                  preparedStatement.setString(2, cdr.getMSISDN());
                  preparedStatement.setString(3, cdr.getStart());
                  preparedStatement.setString(4, cdr.getEnd());
                   LocalTime time_start = LocalTime.parse(cdr.getStart());
                    long start = time_start.toSecondOfDay();
                     LocalTime time_end = LocalTime.parse(cdr.getEnd());
                    long end = time_end.toSecondOfDay();
                    long totalSEconds = end-start;
                  preparedStatement.setLong(5, totalSEconds);
                  preparedStatement.setString(6, cdr.getDistenation());
                  preparedStatement.setString(7, cdr.getService());
                  preparedStatement.executeUpdate();
              } catch (SQLException ex) {
                  Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
    
    }
}
