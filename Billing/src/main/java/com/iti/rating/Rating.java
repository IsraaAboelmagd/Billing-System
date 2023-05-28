/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.rating;

import com.iti.cdr.Connnectiontodatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class Rating {
    int imsi;
    Integer duration;
    String sn_code;
    int tmcode;
    int freeunit;
    int extraunit;
    Connection con=new Connnectiontodatabase().connect();

    public int getFreeunit() {
        return freeunit;
    }

    public void setFreeunit(int freeunit) {
        this.freeunit = freeunit;
    }

    public int getExtraunit() {
        return extraunit;
    }

    public void setExtraunit(int extraunit) {
        this.extraunit = extraunit;
    }

    public int getTmcode() {
        return tmcode;
    }

    public void setTmcode(int tmcode) {
        this.tmcode = tmcode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSn_code() {
        return sn_code;
    }

    public void setSn_code(String sn_code) {
        this.sn_code = sn_code;
    }

    public void setImsi(int imsi) {
        this.imsi = imsi;
    }
    
    public void put_service_duration(){
    Statement st;
    String sql="select sum(duration),sncode from cdr GROUP BY imsi HAVING imsi='"+this.imsi+"';";
    
        try {
            st=con.createStatement();
            ResultSet re = st.executeQuery(sql);
            if(re.next()){
                setDuration(Integer.valueOf(re.getInt("sum")));
                setSn_code(re.getString("sncode"));
            }
//            System.out.println(getDuration()+"     "+getSn_code());
            
        } catch (SQLException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int get_tmcode(){
    Statement st;
    String sql="select tmcode from rtx where imsi='"+this.imsi+"';";
        try {
            st=con.createStatement();
            ResultSet re = st.executeQuery(sql);
            if(re.next()){
                setTmcode(Integer.valueOf(re.getInt("tmcode")));
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return getTmcode();
    }
    
    public void get_free_extra_nuit(){
    Statement st;
    String sql="select freeunits,extraunits from services where tmcode="+get_tmcode()+";";
    
        try {
            st=con.createStatement();
            ResultSet re = st.executeQuery(sql);
            if(re.next()){
                setFreeunit(Integer.valueOf(re.getInt("freeunits")));
                setExtraunit(Integer.valueOf(re.getInt("extraunits")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public void updata_rtx(){
    int voic_used=getFreeunit()-getDuration();
    Statement st;
    String sql="update rtx set voiceusage="+voic_used+" where imsi='"+this.imsi+"';";
    
        try {
            st=con.createStatement();
            int re = st.executeUpdate(sql);
            if(re !=0){
                System.out.println("updataed");
            }
        } catch (SQLException ex) {
            System.out.println("falld to updata");
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}