/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iti.billing;

import com.iti.cdr.CDR;
import com.iti.cdr.DB;
import com.iti.rating.Rating;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Billing {
    
    
    public static void main(String[] args) {
        
//            CDR cdr = new CDR();
//            List<CDR> c=cdr.readCDR();
//            DB db = new DB();
//            db.insertCDR(c);
//        new Rating().put_service_duration(123456789);
           Rating rt=new Rating();
           rt.setImsi(123456789);
           rt.put_service_duration();
           System.out.println(rt.getDuration());
           System.out.println(rt.getSn_code());
           System.out.println(rt.get_tmcode());
           rt.get_free_extra_nuit();
           System.out.println(rt.getFreeunit()+"   "+rt.getExtraunit());
           rt.updata_rtx();
           
        
    }
    
   
}