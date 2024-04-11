/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import com.apogee.bean.EnquiryBean;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author admin
 */
public class IndiaMartServicesModel {

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

    //indiamartapi///
    public int insertEnquiries(EnquiryBean bean) throws SQLException {
        String query = "INSERT INTO enquiry_table(enquiry_source_table_id,marketing_vertical_id,enquiry_no,sender_name,sender_email,sender_mob, "
                + " sender_company_name,product_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, active,"
                + " revision_no,created_by,description,remark) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        String query2 = " select count(*) as count from enquiry_table where enquiry_no='" + bean.getEnquiry_no() + "' and active='Y' ";
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        String date_time = sdf.format(date);
        int enquiry_source_table_id = 1;
        int marketing_vertical_id = getMarketingVerticalId(bean.getMarketing_vertical_name());

        try {
            connection.setAutoCommit(false);
            int count = 0;
            ResultSet rst = connection.prepareStatement(query2).executeQuery();
            while (rst.next()) {
                count = rst.getInt("count");
            }
            if (count > 0) {
                message = "Cannot save the record, some error.";
                messageBGColor = "";
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, enquiry_source_table_id);
                pstmt.setInt(2, marketing_vertical_id);
                pstmt.setString(3, bean.getEnquiry_no());
                pstmt.setString(4, bean.getSender_name());
                pstmt.setString(5, bean.getSender_email());
                pstmt.setString(6, bean.getSender_mob());
                pstmt.setString(7, bean.getSender_company_name());
                pstmt.setString(8, bean.getProduct_name());
                pstmt.setString(9, bean.getEnquiry_address());
                pstmt.setString(10, bean.getEnquiry_city());
                pstmt.setString(11, bean.getEnquiry_state());
                pstmt.setString(12, bean.getCountry());
                pstmt.setString(13, bean.getEnquiry_message());
                pstmt.setString(14, bean.getDate_time());
                String enquiry_call_duration = "";
                enquiry_call_duration = bean.getEnquiry_call_duration();
                if (enquiry_call_duration == null) {
                    enquiry_call_duration = "";
                }
                pstmt.setString(15, enquiry_call_duration);
                String enquiry_reciever_mob = "";
                enquiry_reciever_mob = bean.getEnquiry_reciever_mob();
                if (enquiry_reciever_mob == null) {
                    enquiry_reciever_mob = "";
                }
                pstmt.setString(16, enquiry_reciever_mob);
                pstmt.setString(17, bean.getSender_alternate_email());
                pstmt.setString(18, bean.getSender_alternate_mob());
                pstmt.setString(19, "Y");
                pstmt.setInt(20, bean.getRevision_no());
                pstmt.setString(21, "");
                String city = bean.getEnquiry_city();
                String district = "";
                district = "Others";
                pstmt.setString(22, district);
                String product_name = "";
                product_name = bean.getProduct_name();
                if (product_name == null) {
                    product_name = "";
                }
                pstmt.setInt(23, 0);
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        String enquiry_table_id = rs.getString(1);
                        assignToSalesPerson(enquiry_table_id);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("webServicesModel insertEnquiries() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = "";
            connection.commit();

        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = "";
            connection.rollback();
        }
        return rowsAffected;
    }

    public int getMarketingVerticalId(String marketing_vertical) {

        String query = " SELECT marketing_vertical_id from marketing_vertical where active='Y' ";
        if (!marketing_vertical.equals("") && marketing_vertical != null) {
            query += " and marketing_vertical_name='" + marketing_vertical + "' ";
        }
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("marketing_vertical_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getMarketingVerticalId Error: " + e);
        }
        return id;
    }

    public static String assignToSalesPerson(String enquiry_table_id) throws SQLException {
        // int revision = getRevisionno(enquiry_table_id);
        int revision = 0;
        int updateRowsAffected = 0;
        boolean status = false;
        String getlastid = "select enquiry_assigned_table_id from enquiry_assigned_table order by enquiry_assigned_table_id desc limit 0,1";
        String query1 = " SELECT max(revision_no) revision_no,enquiry_assigned_table_id,description,enquiry_table_id,enquiry_status_id,active,"
                + "created_at,created_by,assigned_by,assigned_to,remark,updated_date_time "
                + " FROM enquiry_assigned_table WHERE enquiry_table_id = " + enquiry_table_id + "  && active=? ";
        String query2 = " UPDATE enquiry_assigned_table SET active=? WHERE enquiry_assigned_table_id=? and revision_no=?";
        String query3 = " INSERT INTO enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,active,revision_no,"
                + "description,remark, updated_date_time,assigned_by,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            int assigned_to_salesperson = 0;
            List<Integer> assigned_to_salesperson_list = new ArrayList<>();
            assigned_to_salesperson_list.add(99);//Shalu marketing manager
            assigned_to_salesperson_list.add(115);//ankit marketing manager
            int enquiry_assigned_table_ids = 0;
            if (assigned_to_salesperson_list.size() > 0) {
                PreparedStatement lastpstmt = connection.prepareStatement(getlastid);
                ResultSet lastrs = lastpstmt.executeQuery();
                if (lastrs.next()) {
                    enquiry_assigned_table_ids = lastrs.getInt("enquiry_assigned_table_id");
                    PreparedStatement pstmt = connection.prepareStatement(query1);
                    pstmt.setString(1, "Y");
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        revision = rs.getInt("revision_no");
                        String enquiry_assigned_table_id = rs.getString("enquiry_assigned_table_id");
                        if (enquiry_assigned_table_id != null) {
                            String description = rs.getString("description");
                            String enquiry_status_id = rs.getString("enquiry_status_id");
                            String active = rs.getString("active");
                            String assigned_by = rs.getString("assigned_by");
                            String assigned_to = rs.getString("assigned_to");
                            String remark = rs.getString("remark");
                            String updated_date_time = rs.getString("updated_date_time");
                            PreparedStatement pstm = connection.prepareStatement(query2);
                            pstm.setString(1, "n");
                            pstm.setString(2, enquiry_assigned_table_id);
                            pstm.setInt(3, revision);
                            updateRowsAffected = pstm.executeUpdate();
                            if (updateRowsAffected >= 1) {
                                revision = rs.getInt("revision_no") + 1;
                                for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                                    assigned_to_salesperson = assigned_to_salesperson_list.get(i);
                                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                                    psmt.setString(1, enquiry_assigned_table_id);
                                    psmt.setString(2, enquiry_table_id);
                                    psmt.setString(3, enquiry_status_id);
                                    psmt.setString(4, "Y");
                                    psmt.setInt(5, revision);
                                    psmt.setString(6, description);
                                    psmt.setString(7, remark);
                                    psmt.setString(8, updated_date_time);
                                    psmt.setInt(9, 108);
                                    psmt.setInt(10, assigned_to_salesperson);
                                    rowsAffected = psmt.executeUpdate();
                                    if (rowsAffected > 0) {
                                        status = true;
//                                String message = sendTelegramMessage(sender_name, sender_mob, enquiry_city, enquiry_state, enquiry_no, product_name,
//                                        assigned_to_salesperson, "sales", "salesperson");
//                                String message2 = sendMail(sender_name, sender_email, sender_mob, enquiry_city, enquiry_state, enquiry_no,
//                                        product_name, enquiry_table_id, assigned_to_salesperson, "sales", "salesperson");

                                        //  String result = sendNotification(sender_name, sender_email, sender_mob, enquiry_city, enquiry_state, enquiry_no,
                                        //  product_name, enquiry_assigned_table_id, assigned_to_salesperson, "sales", "salesperson");
                                    } else {
                                        status = false;
                                    }
                                    revision++;
                                }
                            }
                        } else {
                            revision = 0;
                            enquiry_assigned_table_ids = enquiry_assigned_table_ids + 1;
                            for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                                assigned_to_salesperson = assigned_to_salesperson_list.get(i);
                                PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                                psmt.setInt(1, enquiry_assigned_table_ids);
                                psmt.setString(2, enquiry_table_id);
                                psmt.setInt(3, 11);
                                psmt.setString(4, "Y");
                                psmt.setInt(5, revision);
                                psmt.setString(6, "data from enquiry table");
                                psmt.setString(7, "0");
                                psmt.setString(8, "");
                                psmt.setInt(9, 108);
                                psmt.setInt(10, assigned_to_salesperson);
                                rowsAffected = psmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    status = true;
                                } else {
                                    status = false;
                                }
                                revision++;

                            }
                        }

                    } else {
                        revision = 0;
                        enquiry_assigned_table_ids = 1;
                        for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                            assigned_to_salesperson = assigned_to_salesperson_list.get(i);
                            PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                            psmt.setInt(1, enquiry_assigned_table_ids);
                            psmt.setString(2, enquiry_table_id);
                            psmt.setInt(3, 11);
                            psmt.setString(4, "Y");
                            psmt.setInt(5, revision);
                            psmt.setString(6, "data from enquiry table");
                            psmt.setString(7, "0");
                            psmt.setString(8, "");
                            psmt.setInt(9, 108);
                            psmt.setInt(10, assigned_to_salesperson);
                            rowsAffected = psmt.executeUpdate();
                            if (rowsAffected > 0) {
                                status = true;
                            } else {
                                status = false;
                            }
                            revision++;
                        }
                    }
                } else {
                    revision = 0;
                    enquiry_assigned_table_ids = 1;
                    for (int i = 0; i < assigned_to_salesperson_list.size(); i++) {
                        assigned_to_salesperson = assigned_to_salesperson_list.get(i);
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                        psmt.setInt(1, enquiry_assigned_table_ids);
                        psmt.setString(2, enquiry_table_id);
                        psmt.setInt(3, 11);
                        psmt.setString(4, "Y");
                        psmt.setInt(5, revision);
                        psmt.setString(6, "data from enquiry table");
                        psmt.setString(7, "0");
                        psmt.setString(8, "");
                        psmt.setInt(9, 108);
                        psmt.setInt(10, assigned_to_salesperson);
                        rowsAffected = psmt.executeUpdate();
                        if (rowsAffected > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                        revision++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = "Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
            connection.commit();
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
            connection.rollback();
        }
        return message;
    }

    public static int getRevisionno(String enquiry_table_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM enquiry_assigned_table WHERE enquiry_table_id =" + enquiry_table_id + "  && active='Y';";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel getRevisionno-" + e);

        }
        return revision;
    }

    public static String sendNotification(String sender_name, String email, String sender_mob, String enquiry_city, String enquiry_state,
            String enquiry_no, String product_name, String enquiry_table_id, int assigned_to_salesperson, String enquiry_type,
            String user_type) {
//        assigned_to_salesperson = 89;
//        if (user_type.equals("dealer")) {
//            assigned_to_salesperson = 60;
//        }

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("C:\\Logs\\apl log/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            logger.info("*************************notification log**********************");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> key_person_data = getKeyPersonData(assigned_to_salesperson);
            for (int i = 0; i < key_person_data.size(); i++) {
                String kp_arr[] = key_person_data.get(i).split("&");
                String user_token = kp_arr[0];
                int user_id = Integer.parseInt(kp_arr[1]);
                String user_name = kp_arr[2];
                final String apiKey = "AAAAn_Hig7I:APA91bFXdudad_67iQZKDgKlVjj4coOqS2neIW3QjXo90nd0K9A1DyP8ER6Ibm9slyVqg1BBeoLQPR0dGmeR1SoH69yf7A8uh0guWoPuS5lHJ95Va7eq2iIOD8oWPmrTP1ad7eAdp4ps";
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key = " + apiKey);
                conn.setDoOutput(true);
                String title = "Enquiry";
                String data_url = "";
                if (user_type.equals("dealer")) {
                    if (enquiry_type.equals("sales")) {
                        title = "Sales Enquiry";
                        data_url = "http://120.138.10.146:8080/APL/DealersOrderController?task=viewEnquiryDetails&enquiry_table_id=" + enquiry_table_id + "";
                    }
                    if (enquiry_type.equals("complaint")) {
                        title = "Complaint Enquiry";
                        data_url = "http://120.138.10.146:8080/APL/DealersOrderController?task=viewComplaintDetails&enquiry_table_id=" + enquiry_table_id + "";
                    }

                } else {
                    if (enquiry_type.equals("sales")) {
                        title = "Sales Enquiry";
                        data_url = "http://120.138.10.146:8080/APL/ApproveOrdersController?task=viewEnquiryDetails&enquiry_table_id=" + enquiry_table_id + "";
                    }
                    if (enquiry_type.equals("complaint")) {
                        title = "Complaint Enquiry";
                        data_url = "http://120.138.10.146:8080/APL/ApproveOrdersController?task=viewComplaintDetails&enquiry_table_id=" + enquiry_table_id + "";
                    }
                }

                String body = "We have one new enquiry for you, kindly check your CRM.\n"
                        + "Customer Name:  " + sender_name + " \n"
                        + "Inquired Product:  " + product_name + " \n"
                        + "Contact Details:  " + sender_mob + "\n"
                        + "Enquiry Id:  " + enquiry_no + "\n"
                        + "Location:  " + enquiry_city + "," + enquiry_state + "\n";

                String input = "{\"data\" : {\"title\" : \"" + title + "\",\"body\" : \"" + body + "\",\"url\" : \"" + data_url + "\"}, \"to\":\"" + user_token + "\"}";

                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                logger.info("response code -- " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Exception----" + e);
        }
        return "strrrrrr";
    }

    public static List<String> getKeyPersonData(int key_person_id) {
        String str = "";
        List<String> list = new ArrayList<>();
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select ut.user_token,u.user_id,u.user_name from user u,key_person kp,user_token ut "
                + " where u.key_person_id=kp.key_person_id and kp.active='Y' and u.active='Y' "
                + " and ut.active='Y' and ut.user_id=u.user_id  and kp.key_person_id='" + key_person_id + "' ";

        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                String user_token = rst.getString(1);
                int user_id = rst.getInt(2);
                String user_name = rst.getString(3);
                str = user_token + "&" + user_id + "&" + user_name;

                list.add(str);
            }
        } catch (Exception e) {
            System.out.println("getKeyPersonData ERROR inside LoginModel - " + e);
        }
        return list;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }
}
