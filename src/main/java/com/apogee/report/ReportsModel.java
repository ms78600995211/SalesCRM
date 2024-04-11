/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.report;

import com.apogee.bean.EnquiryBean;
import com.apogee.model.EnquiryModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author admin
 */
public class ReportsModel {

    private static Connection connection;
    private final String COLOR_OK = "green";
    private final String COLOR_ERROR = "red";
    static private String message, messageBGColor = "#a2a220";

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.registartioncummodel.LoginModel.setConnection(): " + e);
        }
    }

    public String setDataforAPI(Map<String, String> map, Iterator itr, Iterator itr2, String report_name, String report_type) {
        org.codehaus.jettison.json.JSONObject inputJsonObj = new org.codehaus.jettison.json.JSONObject();
        String msg = "";
        try {
            String file_name = "", file_data = "", byte_arr = "";
            JSONObject obj = new JSONObject();
            JSONArray jarr = new JSONArray();

            String encodedString = "", image_path = "";
            byte[] arr = null;

            WriteImage("1", itr2, file_name);
            while (itr.hasNext()) {
                JSONObject obj2 = new JSONObject();
                FileItem item = (FileItem) itr.next();
                try {
                    if (!item.isFormField()) {
                        String uploadPath = "C:\\ssadvt_repository\\upload_data\\";
                        file_name = item.getName();
                        image_path = uploadPath + file_name;
                        file_name = item.getName();
                        file_data = item.getString();

                        File file = new File(image_path);
                        InputStream finput = new FileInputStream(file);
                        byte[] imageBytes = new byte[(int) file.length()];
                        finput.read(imageBytes, 0, imageBytes.length);
                        finput.close();
                        byte_arr = Base64.encodeBase64String(imageBytes);

                    }
                } catch (Exception e) {
                    System.out.println("com.apogee.model.UploadDocumentsModel.setDataforAPI()" + e);
                }

            }
            obj.put("report_name", report_name);
            obj.put("report_type", report_type);
//            obj.put("text_file_url", "http://120.138.10.146:8080/EncryptedTokenModule/webAPI/service/getTextFileForReport");
            obj.put("text_file_url", "http://192.168.1.39:8080/SalesCrm/getTextFileForReport");
            obj.put("sample_report_file_name", file_name);
            obj.put("sample_report_file_bytes", byte_arr);
            msg = callAPI(obj);
            message = msg;
        } catch (Exception e) {
            System.out.println("com.apogee.model.UploadDocumentsModel.setDataforAPI()" + e);
        }
        return msg;
    }

    public void WriteImage(String image_id, Iterator itr, String file_name) {
        try {
            String uploadPath = "C:\\ssadvt_repository\\upload_data\\";
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(uploadPath);
                try {
                    if (!item.isFormField()) {
                        if (item.getSize() > 0) {
                            String image = item.getName();
                            String image_path = uploadPath + image;
                            File savedFile = new File(uploadPath + image);
                            item.write(savedFile);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Image upload error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }

    public String callAPI(JSONObject obj) {
        String result = "";
        try {
          //  URL url = new URL("http://120.138.10.146:8080/ReportModule/webAPI/report/reportGenerationRequest");
               URL url = new URL("http://192.168.1.11:8088/ReportModule/webAPI/report/reportGenerationRequest");
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(obj);
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            StringBuffer response = new StringBuffer();
            while ((l = br.readLine()) != null) {
                response.append(l);
            }
            br.close();
            result = response.toString();
        } catch (Exception e) {
            System.out.println("com.apogee.report.ReportsModel.callAPI()" + e);
        }
        return result;
    }

//    public String sendData(String loggedUser, String logged_key_person_id, String enquiry_source, String status) {
//        String result = "";
//        JSONObject obj = new JSONObject();
//        JSONObject obj1 = new JSONObject();
//        JSONArray arr = new JSONArray();
//        EnquiryModel model = new EnquiryModel();
//        List<EnquiryBean> list;
//        try {
//            if (loggedUser.equals("Sales Executive")) {
//
//                list = model.getAllAssignedEnquiries(logged_key_person_id, enquiry_source, status);
//            } else {
//                list = model.getAllEnquiries(enquiry_source, status);
//            }
//            if (list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    String sender_name = list.get(i).getSender_name();
//                    String sender_mob = list.get(i).getSender_mob();
//                    String sender_email = list.get(i).getSender_email();
//                    String product_name = list.get(i).getProduct_name();
//                    String city = list.get(i).getEnquiry_city();
//                    String state = list.get(i).getEnquiry_state();
//                    String enq_status = list.get(i).getStatus();
//                    String assigned_to = list.get(i).getAssigned_to();
//                    if (assigned_to == null) {
//                        assigned_to = "";
//                    }
//                    String date_time = list.get(i).getEnquiry_date_time();
//                    String remark = list.get(i).getAssigned_remark();
//                    String enquiry_message = list.get(i).getEnquiry_message();
//                    obj.put("sender_name", sender_name);
//                    obj.put("enquiry_city", city);
//                    obj.put("enquiry_state", state);
//                    obj.put("sender_mob", sender_mob);
//                    obj.put("sender_email", sender_email);
//                    obj.put("enquiry_date_time", date_time);
//                    obj.put("product_name", product_name);
//                    obj.put("remark", remark);
//                    obj.put("status", status);
//                    arr.put(obj);
//                }
//            }
//            obj1.put("report_name", "SalesCRM Report");
//            obj1.put("data", arr);
//
//            result = sendAllData(obj1);
//        } catch (Exception e) {
//            System.out.println("com.apogee.report.ReportsModel.showData()" + e);
//        }
//        return result;
//    }

    public String sendAllData(JSONObject obj) {
        String result = "";
        try {
//            URL url = new URL("http://120.138.10.146:8080/ReportModule/webAPI/report/getAllDataForGeneratingReport");
            URL url = new URL("http://localhost:8080/ReportModule/webAPI/report/getAllDataForGeneratingReport");

            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(obj);
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            StringBuffer response = new StringBuffer();

            while ((l = br.readLine()) != null) {
                response.append(l);
            }
            br.close();
            result = response.toString();
        } catch (Exception e) {
            System.out.println("com.apogee.report.ReportsModel.sendAllData()" + e);
        }
        return result;
    }
}
