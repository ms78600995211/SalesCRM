/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author admin
 */
public class WebServicesModel {

    static private String message;
    static private String msgBgColor;
    static private final String COLOR_OK = "yellow";
    static private final String COLOR_ERROR = "red";
    public static int device_id25;
    public static String reg_no25;
    public static String pass25;
    public static String imei_no25;
    public static String model_no25;
    private static Connection connection;
    public static int device_registration_id;
    public static int serial_no_updated;
    public static String unique_no_global;
    private byte[] IV;
    private static final String key2 = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";
    static private String messageBGColor;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.emergencymodule.EmergencyUserModel.setConnection(): " + e);
        }
    }

    public String addEnquiryFromWebsite(JSONObject inputJsonObj) {
        String responsedata = "0";
        int rowsAffected = 0;
        int enquiry_table_id = 0;
        int enquiry_no = 0;
        String name = "";
        String phone = "";
        String email = "";
        String location = "";
        String message = "";
        int assigned_to_salesperson = 0;
        int rowsAffecteds = 0;
        int count = 0;
        try {

            try {
                name = inputJsonObj.get("name").toString();
                email = inputJsonObj.get("email").toString();
                phone = inputJsonObj.get("phone").toString();
                location = inputJsonObj.get("location").toString();
                message = inputJsonObj.get("message").toString();
            } catch (JSONException ex) {
                Logger.getLogger(WebServicesModel.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "select enquiry_table_id from enquiry_table order by enquiry_table_id desc";
            String lastenquirynoforwebsite = "select * from enquiry_table where created_by='Website' order by enquiry_table_id desc";
            String addquery = "insert into enquiry_table (enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_no,"
                    + "sender_name,sender_mob,enquiry_address,enquiry_message,active,revision_no,enquiry_date_time,created_by,sender_email,"
                    + "sender_company_name,product_name,enquiry_city,enquiry_state,country,enquiry_call_duration,enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description"
                    + ")"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst;
            PreparedStatement pstlast;

            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                enquiry_table_id = rst.getInt("enquiry_table_id");
                pstlast = connection.prepareStatement(lastenquirynoforwebsite);
                ResultSet rsts = pstlast.executeQuery();
                if (rsts.next()) {
                    enquiry_no = rsts.getInt("enquiry_no");
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, 3);
                    psts.setInt(3, 1);
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, name);
                    psts.setString(6, phone);
                    psts.setString(7, location);
                    psts.setString(8, message);
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String currentdatetime = currentDateTime.format(formatter);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Website");
                    psts.setString(13, email);
                    psts.setString(14, "");
                    psts.setString(15, "");
                    psts.setString(16, "");
                    psts.setString(17, "");
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, "");
                    psts.setString(22, "");
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            List<Integer> assigned_to_salesperson_list = new ArrayList<>();
                            assigned_to_salesperson_list.add(99);//Shalu marketing manager
                            assigned_to_salesperson_list.add(115);//ankit marketing manager

                            if (assigned_to_salesperson_list.size() > 0) {
                                for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                                    assigned_to_salesperson = assigned_to_salesperson_list.get(i);

                                    String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                            + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                            + "values(?,?,?,?,?,?,?,?,?,?)";
                                    PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                                    pstss.setInt(1, id);
                                    pstss.setString(2, "11");
                                    pstss.setString(3, "Y");
                                    pstss.setInt(4, count);
                                    pstss.setString(5, "");
                                    pstss.setString(6, "This is manual entry");
                                    pstss.setString(7, "Website");
                                    pstss.setString(8, "88");
                                    pstss.setInt(9, assigned_to_salesperson);
                                    pstss.setString(10, "");
                                    rowsAffecteds = pstss.executeUpdate();
                                    if (rowsAffecteds > 0) {
                                        message = "Record saved successfully.";
                                        msgBgColor = COLOR_OK;
                                        responsedata = "Data Added successfully";
                                    }
                                    count++;
                                }

                            } else {
                                message = "Cannot save the record, some error.";
                                msgBgColor = COLOR_ERROR;
                                responsedata = "Failed to Add data";
                            }
                        }
                    } else {
                        message = "Cannot save the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        responsedata = "Failed to add data";
                    }

                } else {
                    enquiry_no = 0;
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, 3);
                    psts.setInt(3, 1);
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, name);
                    psts.setString(6, phone);
                    psts.setString(7, location);
                    psts.setString(8, message);
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String currentdatetime = currentDateTime.format(formatter);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Website");
                    psts.setString(13, email);
                    psts.setString(14, "");
                    psts.setString(15, "");
                    psts.setString(16, "");
                    psts.setString(17, "");
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, "");
                    psts.setString(22, "");
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            List<Integer> assigned_to_salesperson_list = new ArrayList<>();
                            assigned_to_salesperson_list.add(99);//Shalu marketing manager
                            assigned_to_salesperson_list.add(115);//ankit marketing manager
                            if (assigned_to_salesperson_list.size() > 0) {
                                for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                                    assigned_to_salesperson = assigned_to_salesperson_list.get(i);

                                    String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                            + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                            + "values(?,?,?,?,?,?,?,?,?,?)";
                                    PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                                    pstss.setInt(1, id);
                                    pstss.setString(2, "11");
                                    pstss.setString(3, "Y");
                                    pstss.setInt(4, count);
                                    pstss.setString(5, "");
                                    pstss.setString(6, "This is website entry");
                                    pstss.setString(7, "Website");
                                    pstss.setString(8, "88");
                                    pstss.setInt(9, assigned_to_salesperson);
                                    pstss.setString(10, "");
                                    rowsAffecteds = pstss.executeUpdate();
                                    if (rowsAffecteds > 0) {
                                        message = "Record saved successfully.";
                                        msgBgColor = COLOR_OK;
                                        responsedata = "Data Added successfully";
                                    }
                                    count++;
                                }

                            } else {
                                message = "Cannot save the record, some error.";
                                msgBgColor = COLOR_ERROR;
                                responsedata = "Failed to add data";
                            }
                        }
                    } else {
                        message = "Cannot save the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        responsedata = "Failed to add data";
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebServicesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responsedata;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }
}
