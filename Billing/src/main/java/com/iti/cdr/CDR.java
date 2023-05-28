package com.iti.cdr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CDR {

    private String IMSI;
    private String MSISDN;
    private String start;
    private String end;
    private String distenation;
    private String service;

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public String getIMSI() {
        return IMSI;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDistenation() {
        return distenation;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public CDR() {
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDistenation(String distenation) {
        this.distenation = distenation;
    }

    public CDR(String IMSI, String MSISDN, String start, String end, String distenation, String service) {
        this.IMSI = IMSI;
        this.MSISDN = MSISDN;
        this.start = start;
        this.end = end;
        this.distenation = distenation;
        this.service = service;
    }

    @Override
    public String toString() {
        return IMSI + MSISDN + start + end + distenation + service;
    }

    public static List<CDR> readCDR() {
        String line;
        List<CDR> cdrSearch = new ArrayList<CDR>();
        try {
//       BufferedReader reader = new BufferedReader(new FileReader("D:\\ITI\\Billing\\CDR.csv"));

            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\hp\\Desktop\\Billing\\Billing-System\\CDR.csv"));
            while ((line = reader.readLine()) != null) {
                String[] cdrSplit = line.split(",");
//           for(var token:line){
//               System.out.println(token);
//               
//           }
                CDR cdrFind = createOneCDR(cdrSplit);
                cdrSearch.add(cdrFind);

            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return cdrSearch;
    }

    private static CDR createOneCDR(String[] metadata) {
//        String imsi=metadata[0];
        return new CDR(metadata[0], metadata[1], metadata[2], metadata[3], metadata[4], metadata[5]);
    }
}
