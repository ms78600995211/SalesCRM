/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.notification;

import com.apogee.bean.EnquiryBean;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.JSONObject;
import java.net.URLConnection;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author lENOVO
 */
@Component
public class SendNotificationSchedulerModel {

//    private static final Logger logger = LoggerFactory.getLogger(SendNotificationSchedulerModel.class);
    private static final Logger logger = Logger.getLogger(SendNotificationSchedulerModel.class.getName());

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");
    private static Connection connection;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.emergencymodule.EmergencyUserModel.setConnection(): " + e);
        }
    }

    /**
     * change code according 1nd approach mail send only Admin.
     *
     */
    public String GetExecutives(String a, String b) throws Exception {

        String baseiname = "";
        String getPersonByDesignationHierarchy = resourceBundle.getString("ORG_MODULE_PERSONBYDESIGNATION_API_URL");
//        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getPersonByDesignationHierarchy";
        String url = getPersonByDesignationHierarchy;

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        String urlParameters = ("{\"organisation_name\": \"" + a + "\",\"designation_name\": \"" + b + "\"}");

        httpClient.setDoOutput(
                true);
        try ( DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();

//        System.out.println(
//                "\nSending 'POST' request to URL : " + url);
//        System.out.println(
//                "Post parameters : " + urlParameters);
//        System.out.println(
//                "Response Code : " + responseCode);
        try ( BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);

            }
            return baseiname = response.toString();
            //print result
            // System.out.println("data" : +baseiname);

        }

    }

    public ArrayList<EnquiryBean> getEnqueryDetails(int salesExecutiveId, String disignationName) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        String query = "";
        if (disignationName.equals(SALES_EXECUTIVE)) {
            query = "SELECT distinct eat.created_at, eat.updated_date_time, et.sender_name, et.sender_email, et.sender_mob,"
                    + " et.enquiry_address, et.enquiry_date_time, et.product_name\n"
                    + "FROM enquiry_table AS et\n"
                    + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                    + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id\n"
                    + "WHERE eat.assigned_to = '" + salesExecutiveId + "'  AND es.status = 'Pending' and"
                    + " et.active = 'Y' and eat.active = 'Y' and es.active = 'Y'\n";
        } else {
            query = "SELECT distinct eat.created_at, eat.updated_date_time, et.sender_name, et.sender_email, et.sender_mob,"
                    + " et.enquiry_address, et.enquiry_date_time, et.product_name\n"
                    + "FROM enquiry_table AS et\n"
                    + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                    + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id\n"
                    + "WHERE eat.assigned_to = '" + salesExecutiveId + "'  AND es.status = 'Pending' and"
                    + " et.active = 'Y' and eat.active = 'Y' and es.active = 'Y'\n";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                bean.setUpdate_date_time(rset.getString("updated_date_time"));  //iske according mil send krna h.
                bean.setCreated_at(rset.getString("created_at"));  //iske according mil send krna h.
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setEnquiry_address(rset.getString("enquiry_address"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));
                bean.setProduct_name(rset.getString("product_name"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);

        }
        return list;
    }

    String sendMailApi(JSONObject objsendMail) {
        String result = "";
        try {
            String sendMail = resourceBundle.getString("COMM_MODULE_SENDMAIL_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/sendMail");
            URL url = new URL(sendMail);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);
            //////////////create a log file
            try {

                String directoryPath = "C:/Logs";
                String logFilePath = directoryPath + "\\application.txt";

                // Check if the directory exists, create it if not  
                if (!Files.exists(Paths.get(directoryPath))) {
                    Files.createDirectories(Paths.get(directoryPath));
//                    logger.log(Level.INFO, "create file, before call mail api");
                } else {
//                    logger.log(Level.INFO, "create file, before call mail api");
                }

                try {
                    FileHandler fileHandler = new FileHandler(logFilePath);
                    fileHandler.setFormatter(new SimpleFormatter());
                    logger.addHandler(fileHandler);

                    logger.log(Level.INFO, "before call mail api: " + objsendMail.toString());

                } catch (IOException e) {
                    logger.log(Level.INFO, "file not create, before call mail api");
                    e.printStackTrace();
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error creating directory : ");
            }
            /////////////

            PrintStream ps = new PrintStream(urlc.getOutputStream());

            ps.print(objsendMail);

            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            StringBuffer response = new StringBuffer();
            while ((l = br.readLine()) != null) {
                response.append(l);
            }

            br.close();
            result = response.toString();
            logger.log(Level.INFO, "after call mail api result: " + result);
        } catch (Exception e) {
            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
            logger.log(Level.SEVERE, "error{} : ");
        }
        return result;
    }

    public JSONObject addToJSONArray(JSONObject jsonArrayWithKeys, String ExecutiveOrManagerName, JSONObject jsonObject) {
        if (jsonArrayWithKeys.has(ExecutiveOrManagerName)) {
            // Key exists, append the JSON object to the existing JSON array
            JSONArray jsonArray = jsonArrayWithKeys.getJSONArray(ExecutiveOrManagerName);
            jsonArray.put(jsonObject);
        } else {
            // Key does not exist, create a new JSON array and add the JSON object to it
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            jsonArrayWithKeys.put(ExecutiveOrManagerName, jsonArray);
        }
        return jsonArrayWithKeys;
    }

//    JSONObject createMailFormate(JSONArray detailsSendToManager, String salesExecutiveName, String idTo, String disignationName) {
//        String mailBodyForManager = "";
//        String finaleMailToManager = "";
//        String commonMailFormate = "";
//        if (disignationName.equals(SALES_EXECUTIVE)) {
//            commonMailFormate = "Hello Sir, <br/><br/> " + disignationName + " " + salesExecutiveName + " has not worked on the below enquiries till now. <br/><br/> ";
//        } else {
//            commonMailFormate = "Hello Sir, <br/><br/> " + disignationName + " " + salesExecutiveName + " has not worked on the below enquiries till now. <br/><br/> ";
//        }
//        for (int j = 0; j < detailsSendToManager.length(); j++) {
//            JSONObject toObj1 = detailsSendToManager.getJSONObject(j);
////            String ExecutiveOrManagerName = toObj1.getString("ExecutiveOrManagerName");
//            String sender_name = toObj1.getString("sender_name");
//            String sender_email = toObj1.getString("sender_email");
//            String sender_mob = toObj1.getString("sender_mob");
//            String enquiry_address = toObj1.getString("enquiry_address");
//            String enquiry_date_time = toObj1.getString("enquiry_date_time");
//            String product_name = toObj1.getString("product_name");
//
//            //create mail body
//            String mailFormateForManager = " " + (j + 1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
//            finaleMailToManager += mailFormateForManager;
//        }
//        mailBodyForManager = commonMailFormate + "<br/>" + finaleMailToManager;
////        System.out.println(mailBodyForManager);
//
//        //send mail
//        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("to_id", "1");
//        jsonObject.put("to_id", idTo);
//        JSONArray toListIds = new JSONArray();
//        toListIds.put(jsonObject);
//        
//
//        String subjectId = "1";
//        String subjectName = getSubject(subjectId);
//
//        if (disignationName.equals(SALES_EXECUTIVE)) {
//            subjectName += " of " + SALES_EXECUTIVE + " [" + salesExecutiveName + "]";
//        } else {
//            subjectName += " of " + SALES_MANAGER + " [" + salesExecutiveName + "]";
//        }
//
//        JSONObject objsendMail = new JSONObject();
//        objsendMail.put("to", toListIds);
//        objsendMail.put("subjectId", subjectId);
//        objsendMail.put("subjectName", subjectName);
//        objsendMail.put("body", mailBodyForManager);
//        return objsendMail;
//    }
    String createMailFormate(JSONArray detailsSendToManager, String salesExecutiveName, String idTo, String disignationName, String mailBodyForManager) {
//        String mailBodyForManager = "";
        String finaleMailToManager = "";
        String commonMailFormate = "";
        if (disignationName.equals(SALES_EXECUTIVE)) {
            commonMailFormate = " " + disignationName + " " + salesExecutiveName + " has not worked on the below enquiries till now. <br/><br/> ";
        } else {
            commonMailFormate = " " + disignationName + " " + salesExecutiveName + " has not worked on the below enquiries till now. <br/><br/> ";
        }
        for (int j = 0; j < detailsSendToManager.length(); j++) {
            JSONObject toObj1 = detailsSendToManager.getJSONObject(j);
//            String ExecutiveOrManagerName = toObj1.getString("ExecutiveOrManagerName");
            String sender_name = toObj1.getString("sender_name");
            String sender_email = toObj1.getString("sender_email");
            String sender_mob = toObj1.getString("sender_mob");
            String enquiry_address = toObj1.getString("enquiry_address");
            String enquiry_date_time = toObj1.getString("enquiry_date_time");
            String product_name = toObj1.getString("product_name");

            //create mail body
            String mailFormateForManager = " " + (j + 1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
            finaleMailToManager += mailFormateForManager;
        }
        mailBodyForManager = mailBodyForManager + "<br>" + commonMailFormate + "<br/>" + finaleMailToManager;
//        System.out.println(mailBodyForManager);

//        //send mail
//        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("to_id", "1");
//        jsonObject.put("to_id", idTo);
//        JSONArray toListIds = new JSONArray();
//        toListIds.put(jsonObject);
//        
//
//        String subjectId = "1";
//        String subjectName = getSubject(subjectId);
//
//        if (disignationName.equals(SALES_EXECUTIVE)) {
//            subjectName += " of " + SALES_EXECUTIVE + " [" + salesExecutiveName + "]";
//        } else {
//            subjectName += " of " + SALES_MANAGER + " [" + salesExecutiveName + "]";
//        }
//
//        JSONObject objsendMail = new JSONObject();
//        objsendMail.put("to", toListIds);
//        objsendMail.put("subjectId", subjectId);
//        objsendMail.put("subjectName", subjectName);
//        objsendMail.put("body", mailBodyForManager);
        return mailBodyForManager;
    }

    String getCurrentDateTime() {
        String query = "SELECT * FROM chat_table WHERE chat_table_id = ?";
        String currentDateTime = null;
        try {

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, 1);
            ResultSet stmt = pstmt.executeQuery();

            while (stmt.next()) {
                currentDateTime = stmt.getString("created_at");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return currentDateTime;
    }

    public String getSubject(String subjectId) {
        String subjectName = "";
        String query = "select subject_name from subject where subject_id = '" + subjectId + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();
            while (stmt.next()) {
                subjectName = stmt.getString("subject_name");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return subjectName;
    }

    JSONObject createMailFormateForReminder(EnquiryBean reminder) {
        JSONObject objsendMail = new JSONObject();

        try {
            JSONObject jObjkeypersonId = new JSONObject();
            jObjkeypersonId.put("key_person_id", reminder.getKey_person_id());
            String keyPersonNameById = getPersonNameByPersonId(jObjkeypersonId);

            JSONObject jsonObjectkeyPersonNameById = new JSONObject(keyPersonNameById);
            JSONArray outerArray = jsonObjectkeyPersonNameById.getJSONArray("person");
            String keyPersonName = "";
            for (int j = 0; j < outerArray.length(); j++) {
                JSONObject singleExecutiveDetailsObj = outerArray.getJSONObject(j);
                keyPersonName = singleExecutiveDetailsObj.getString("key_person_name");
            }

            String commonMailFormate = " Dear " + keyPersonName + " <br/> <br/> You have a reminder scheduled for your " + reminder.getProduct_name() + " enquiry at " + reminder.getTime() + ". <br/><br/> Contact Detail: <br/><br/> " + reminder.getSender_name() + " <br/> Address : " + reminder.getEnquiry_address() + " <br/> Mobile : " + reminder.getSender_mob() + " <br/> E-mail : " + reminder.getSender_email() + " ";

            //send mail
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to_id", reminder.getKey_person_id());
            JSONArray toListIds = new JSONArray();
            toListIds.put(jsonObject);

            String subjectId = "2";
            String subjectName = "";
//            String subjectName = "Reminder for your " + reminder.getProduct_name() + " enquiry";
            subjectName = getSubject(subjectId);

            objsendMail.put("to", toListIds);
            objsendMail.put("subjectId", subjectId);
            objsendMail.put("subjectName", subjectName + " " + reminder.getProduct_name() + " enquiry");
            objsendMail.put("body", commonMailFormate);
        } catch (Exception e) {
            System.out.println(e);
        }
        return objsendMail;
    }

    String getPersonNameByPersonId(JSONObject keypersonId) {
        String result = "";
        try {
            String personnamebypersonid = resourceBundle.getString("GET_PERSONNAME_BY_PERSONID");
//            URL url = new URL("http://localhost:8080/CModule/sendMail");
            URL url = new URL(personnamebypersonid);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(keypersonId);
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
            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
        }
        return result;
    }

    String sendWhatsappApi(JSONObject whatsappObj) {
        String result = "";
        try {
            String sendWhatsapp = resourceBundle.getString("COMM_MODULE_SENDWHATSAPP_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/sendWhatsAppMessage");
            URL url = new URL(sendWhatsapp);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(whatsappObj);
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
            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
        }
        return result;
    }

    /**
     * change code according 2nd approach mail send only Admin.
     *
     */
//    public JSONObject getsalesManagerAndExecutiveIds() {
//        ArrayList<EnquiryBean> list1 = new ArrayList<EnquiryBean>();
//        ArrayList<EnquiryBean> list2 = new ArrayList<EnquiryBean>();
//        JSONObject jsonObject = new JSONObject();
//        //sales executive
//        String query1 = "SELECT distinct eat.created_at, et.sender_name, et.sender_email, et.sender_mob,et.enquiry_address, et.enquiry_date_time, et.product_name,\n"
//                + "eat.enquiry_assigned_table_id, eat.enquiry_table_id, eat.enquiry_status_id, eat.assigned_by,GROUP_CONCAT(assigned_to) AS assigned_to \n"
//                + "FROM enquiry_table AS et \n"
//                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id \n"
//                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id \n"
//                + "WHERE eat.enquiry_status_id=11 and eat.assigned_by!=108 and eat.assigned_to!=assigned_by and \n"
//                + "et.active = 'Y' and eat.active = 'Y' and es.active = 'Y' group by eat.enquiry_table_id;";
//
//        //sales manager
//        String query2 = "SELECT distinct eat.created_at, et.sender_name, et.sender_email, et.sender_mob,et.enquiry_address, et.enquiry_date_time, et.product_name,\n"
//                + "eat.enquiry_assigned_table_id, eat.enquiry_table_id, eat.enquiry_status_id, eat.assigned_by,GROUP_CONCAT(assigned_to) AS assigned_to \n"
//                + "FROM enquiry_table AS et \n"
//                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id \n"
//                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id \n"
//                + "WHERE eat.enquiry_status_id=11 and eat.assigned_by=108 and \n"
//                + "et.active = 'Y' and eat.active = 'Y' and es.active = 'Y' group by eat.enquiry_table_id;";
//
//        try {
//            ResultSet rset1 = connection.prepareStatement(query1).executeQuery();
//            while (rset1.next()) {
//                EnquiryBean bean = new EnquiryBean();
//                bean.setUpdate_date_time(rset1.getString("created_at"));
//                bean.setSender_name(rset1.getString("sender_name"));
//                bean.setSender_email(rset1.getString("sender_email"));
//                bean.setSender_mob(rset1.getString("sender_mob"));
//                bean.setEnquiry_address(rset1.getString("enquiry_address"));
//                bean.setEnquiry_date_time(rset1.getString("enquiry_date_time"));
//                bean.setProduct_name(rset1.getString("product_name"));
//                bean.setAssigned_to(rset1.getString("assigned_to"));
//                list1.add(bean);
//            }
//
//            ResultSet rset2 = connection.prepareStatement(query2).executeQuery();
//            while (rset2.next()) {
//                EnquiryBean bean2 = new EnquiryBean();
//                bean2.setUpdate_date_time(rset2.getString("created_at"));
//                bean2.setSender_name(rset2.getString("sender_name"));
//                bean2.setSender_email(rset2.getString("sender_email"));
//                bean2.setSender_mob(rset2.getString("sender_mob"));
//                bean2.setEnquiry_address(rset2.getString("enquiry_address"));
//                bean2.setEnquiry_date_time(rset2.getString("enquiry_date_time"));
//                bean2.setProduct_name(rset2.getString("product_name"));
//                bean2.setAssigned_to(rset2.getString("assigned_to"));
//                list2.add(bean2);
//            }
//
//            jsonObject.put("executeve", list1);
//            jsonObject.put("manager", list2);
//
//        } catch (Exception e) {
//            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
//
//        }
//        return jsonObject;
//
//    }
//
//    private String getSubject(String subjectId) {
//        String subjectName = "";
//        String query = "select subject_name from subject where subject_id = '" + subjectId + "' ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet stmt = pstmt.executeQuery();
//            while (stmt.next()) {
//                subjectName = stmt.getString("subject_name");
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        return subjectName;
//    }
//
//    public JSONObject createMailFormate(JSONObject detailsSendToAdmin, String salesName, String idTo, String name) {
//
//        String mailBodyForManager = "";
//        String sender_name = detailsSendToAdmin.getString("sender_name");
//        String sender_email = detailsSendToAdmin.getString("sender_email");
//        String sender_mob = detailsSendToAdmin.getString("sender_mob");
//        String enquiry_address = detailsSendToAdmin.getString("enquiry_address");
//        String enquiry_date_time = detailsSendToAdmin.getString("enquiry_date_time");
//        String product_name = detailsSendToAdmin.getString("product_name");
//
//        //create mail body
////        if (name.equals("executive")) {
////            mailBodyForManager = " Hello Sir/Mam, <br/><br/> Sales Executive <b>[" + salesName + "]</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
////        } else if (name.equals("manager")) {
////            mailBodyForManager = " Hello Sir/Mam, <br/><br/> Sales Manager <b>[" + salesName + "]</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
////        } else if (name.equals("managerDashboard")) {
////            mailBodyForManager = " Sales Manager <b>[" + salesName + "]</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
////        } else if (name.equals("executiveDashboard")) {
////            mailBodyForManager = " Sales Executive <b>[" + salesName + "]</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
////        }
//        if (name.equals("executive") || name.equals("manager")) {
//            mailBodyForManager = " Hello Sir/Mam, <br/><br/> <b>" + salesName + "</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
//        } else if (name.equals("managerDashboard") || name.equals("executiveDashboard")) {
//            mailBodyForManager = " <b>" + salesName + "</b> has not worked on the below enquiries till now. <br/><br/> " + (1) + ". Name - [" + sender_name + "] <br/> Email - " + sender_email + " <br/> phone No. - " + sender_mob + " <br/> Address - " + enquiry_address + " <br/> Date & Time - " + enquiry_date_time + " <br/> Product - " + product_name + " <br/><br/>";
//        }
//
//        //send mail
//        JSONArray toListIds = new JSONArray();
//        JSONObject toObjId = new JSONObject();
////        jsonObject.put("to_id", "1");
//        toObjId.put("to_id", idTo);
//        toListIds.put(toObjId);
//
//        JSONArray ccListIds = new JSONArray();
//
//        String assign_to = detailsSendToAdmin.getString("assigned_to");
//        String[] ccIdStrings = assign_to.split(",");
//        for (String idString : ccIdStrings) {
//            try {
//                JSONObject ccObjId = new JSONObject();
//                int cc_id = Integer.parseInt(idString);
//                String cc_ids = Integer.toString(cc_id);
//                ccObjId.put("cc_id", cc_ids);
//                ccListIds.put(ccObjId);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid ID format: " + idString);
//            }
//        }
//
//        String subjectId = "1";
//        String subjectName = getSubject(subjectId);
//
////        if (name.equals("executive") || name.equals("executiveDashboard")) {
////            subjectName += " Sales Executive [" + salesName + "]";
////        } else if (name.equals("manager") || name.equals("managerDashboard")) {
////            subjectName += " Sales Manager [" + salesName + "]";
////        }
//        if (name.equals("executive") || name.equals("executiveDashboard")) {
//            subjectName += " " + salesName;
//        } else if (name.equals("manager") || name.equals("managerDashboard")) {
//            subjectName += " " + salesName;
//        }
//
//        JSONObject objsendMail = new JSONObject();
//        objsendMail.put("to", toListIds);
//        objsendMail.put("cc", ccListIds);
//        objsendMail.put("subjectId", subjectId);
//        objsendMail.put("subjectName", subjectName);
//        objsendMail.put("body", mailBodyForManager);
//        return objsendMail;
//    }
//
//    String sendMailApi(JSONObject objsendMail) {
//        String result = "";
//        try {
//            String sendMail = resourceBundle.getString("COMM_MODULE_SENDMAIL_API_URL");
////            URL url = new URL("http://localhost:8080/CModule/sendMail");
//            URL url = new URL(sendMail);
//            URLConnection urlc = url.openConnection();
//            urlc.setRequestProperty("datatype", "POST");
//            urlc.setRequestProperty("Content-Type", "application/json");
//            urlc.setDoOutput(true);
//            urlc.setAllowUserInteraction(false);
//
//            PrintStream ps = new PrintStream(urlc.getOutputStream());
//            ps.print(objsendMail);
//            ps.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
//            String l = null;
//            StringBuffer response = new StringBuffer();
//            while ((l = br.readLine()) != null) {
//                response.append(l);
//            }
//            br.close();
//            result = response.toString();
//        } catch (Exception e) {
//            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
//        }
//        return result;
//    }
//
//    public String getPersonDetailByIds(String PersonIds) throws Exception {
//
//        String result = "";
//        try {
//            String getPersonByDesignationHierarchy = resourceBundle.getString("ORG_MODULE_PERSONDETAILSBYIDS_API_URL");
////            String getPerson = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getPersonDetailByIds";
//
//            URL url = new URL(getPersonByDesignationHierarchy);
//
//            URLConnection urlc = url.openConnection();
//            urlc.setRequestProperty("datatype", "POST");
//            urlc.setRequestProperty("Content-Type", "application/json");
//            urlc.setDoOutput(true);
//            urlc.setAllowUserInteraction(false);
//
//            PrintStream ps = new PrintStream(urlc.getOutputStream());
//            ps.print(PersonIds);
//            ps.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
//            String l = null;
//            StringBuffer response = new StringBuffer();
//            while ((l = br.readLine()) != null) {
//                response.append(l);
//            }
//            br.close();
//            result = response.toString();
//        } catch (Exception e) {
//            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
//        }
//        return result;
//    }
//
//    public Duration findDuration(LocalDateTime dateTime) {
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////        LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//        int year = dateTime.getYear();
//        int month = dateTime.getMonthValue();
//        int day = dateTime.getDayOfMonth();
//        int hour = dateTime.getHour();
//        int minute = dateTime.getMinute();
//        int second = dateTime.getSecond();
//        LocalDateTime specificDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        Duration duration = Duration.between(specificDateTime, currentDateTime);
//        return duration;
//    }
//
//    public boolean findTimeTen(LocalDateTime currentDateTime) {
//        LocalTime currentTime = currentDateTime.toLocalTime();
//        LocalTime startTime = LocalTime.of(9, 55, 0);
//        LocalTime endTime = LocalTime.of(10, 05, 0);
//        boolean isBetween2 = ((currentTime.isAfter(startTime) || currentTime.equals(startTime)) && currentTime.isBefore(endTime));
//        return isBetween2;
//    }
//
//    boolean findTimeAfterTwoDays(LocalDateTime dateTime, LocalDateTime currentDateTime) {
//        int currentTimeIncDcr = 5;
//        LocalTime enquiryTime = dateTime.toLocalTime();
//        LocalTime currentTime = currentDateTime.toLocalTime();
//        LocalTime CurrentFutureTime = currentTime.plusMinutes(currentTimeIncDcr);
//        LocalTime CurrentPastTime = currentTime.minusMinutes(currentTimeIncDcr);
//
//        boolean isBetween1 = ((enquiryTime.isAfter(CurrentPastTime) || enquiryTime.equals(CurrentPastTime)) && enquiryTime.isBefore(CurrentFutureTime));
//        return isBetween1;
//    }
}
