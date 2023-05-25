/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iti.billing;

import com.iti.cdr.CDR;
import com.iti.cdr.DB;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Billing {
    
    
    public static void main(String[] args) {
        
            CDR cdr = new CDR();
            List<CDR> c=cdr.readCDR();
            DB db = new DB();
            db.insertCDR(c);
            
        
    }
    
   
}
