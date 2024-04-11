/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.notification;

import com.apogee.DBConnection;
import com.apogee.bean.EnquiryBean;
import com.apogee.controller.EnquiryController;
import com.apogee.model.EnquiryModel;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * write code using listner for sheduler
 */
public class SendNotificationScheduler implements ServletContextListener {

    SendNotificationSchedulerModel model = new SendNotificationSchedulerModel();
    EnquiryModel model1 = new EnquiryModel();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");
//    HttpServletRequest request;
    @Context
    ServletContext servletContext;
    int scheduler_count = 0;
    String path = "";
    Connection connection = null;
//    HttpSession session = request.getSession();
//    String key_person_id = (String) session.getAttribute("key_person_id");  

    class VodTimerTask extends TimerTask {

        public String sendMailNotification() {
            try {
                model.setConnection(DBConnection.getConnection(servletContext));
                // status = wsm.saveDeviceRegs(dataString);
            } catch (Exception e) {
                System.out.println("error in WebServicesController.saveDeviceRegRecord()- " + e);
            }
            JSONObject jObjSaleExecutive = new JSONObject();
            JSONObject jObjSaleManager = new JSONObject();
            JSONObject jObjAdmin = new JSONObject();
            String salesExecutiveDetails = "";
            String salesManagerDetails = "";
            String adminDetails = "";
            String organisation_name = "APOGEE GNSS";
            String designation_name_SALES_EXECUTIVE = SALES_EXECUTIVE;
            String designation_name_SALES_MANAGER = SALES_MANAGER;
            String designation_name_ADMIN = SALES_SUPERVISOR;  

            try {
                String mailBodyForManager = "";

                //send mail to admin of sales manager
                adminDetails = model.GetExecutives(organisation_name, designation_name_ADMIN);
                jObjAdmin = new JSONObject(adminDetails);
                JSONArray jsonArrayAdmin = jObjAdmin.getJSONArray("json");
                JSONArray newjsonArrayAdmin = new JSONArray();
                JSONArray toListIds = new JSONArray();
                JSONArray adminMobilesList = new JSONArray();
                for (int i = 0; i < jsonArrayAdmin.length(); i++) {
                    JSONObject toObj = jsonArrayAdmin.getJSONObject(i);
                    String designation = toObj.getString("designation");
                    if (designation.equals(designation_name_ADMIN)) {
                        JSONObject toObj1 = new JSONObject();
                        JSONObject toObj2 = new JSONObject();
                        int key_person_id = toObj.getInt("key_person_id");
                        String mobile_no1 = toObj.getString("mobile_no1");
                        String to_id = String.valueOf(key_person_id);
                        toObj1.put("to_id", to_id);
                        toListIds.put(toObj1);
                        toObj2.put("mobile_no1", mobile_no1);
                        adminMobilesList.put(toObj2);
                    }
                }

                //send mail to admin of sales executive  
                salesExecutiveDetails = model.GetExecutives(organisation_name, designation_name_SALES_EXECUTIVE);
                jObjSaleExecutive = new JSONObject(salesExecutiveDetails);
                JSONArray jsonArraySalesExecutive = jObjSaleExecutive.getJSONArray("json");
                mailBodyForManager = sendMailAdmin(jsonArraySalesExecutive, designation_name_SALES_EXECUTIVE, newjsonArrayAdmin, mailBodyForManager);

                //send mail to admin of sales manager
                salesManagerDetails = model.GetExecutives(organisation_name, designation_name_SALES_MANAGER);
                jObjSaleManager = new JSONObject(salesManagerDetails);
                JSONArray jsonArraySalesManager = jObjSaleManager.getJSONArray("json");
                JSONArray newjsonArraySalesManager = new JSONArray();
                for (int i = 0; i < jsonArraySalesManager.length(); i++) {
                    JSONObject toObj = jsonArraySalesManager.getJSONObject(i);

                    String designation = toObj.getString("designation");
                    if (designation.equals(designation_name_SALES_MANAGER)) {
                        newjsonArraySalesManager.put(toObj);
                    }
                }

                String allSendMailAdmin = sendMailAdmin(newjsonArraySalesManager, designation_name_SALES_MANAGER, newjsonArrayAdmin, mailBodyForManager);
                if (!allSendMailAdmin.equals("")) {
                    //        //send mail
                    String subjectId = "1";
                    String subjectName = model.getSubject(subjectId);
                    JSONObject objsendMail = new JSONObject();
                    objsendMail.put("to", toListIds);
                    objsendMail.put("subjectId", subjectId);
                    objsendMail.put("subjectName", subjectName);
                    objsendMail.put("body", "Hello Sir, <br/><br/>" + allSendMailAdmin);
                    String result2 = model.sendMailApi(objsendMail);   
   
                }

                /**
                 * send mail for reminder start here
                 */
                sendMailReminder();    

//                sendWhatsappMessage(adminMobilesList);  
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;   
        }

        @Override
        public void run() {
            try {
                sendMailNotification();
            } catch (Exception ex) {
                System.out.println("com.apogee.notification.SendNotificationScheduler.VodTimerTask.run()" + ex);
            }
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        TimerTask vodTimer = new VodTimerTask();
        Timer timer = new Timer();
        Date date = new Date();
        timer.schedule(vodTimer, 10 * 1000, (10 * 60 * 1000));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method will be called when the application is shutting down
        // You can use this method to perform any cleanup tasks if needed

    }

    private String sendMailAdmin(JSONArray jsonArrayExecutiveOrManager, String disignationName, JSONArray newjsonArrayAdmin, String mailBodyForManager) {
        try {
            for (int i = 0; i < jsonArrayExecutiveOrManager.length(); i++) {
                JSONObject toObj = jsonArrayExecutiveOrManager.getJSONObject(i);
                int ExecutiveOrManagerId = toObj.getInt("key_person_id");
                String ExecutiveOrManagerName = toObj.getString("key_person_name");
                String designation = toObj.getString("designation");
                ArrayList<EnquiryBean> enqueryDetails = model.getEnqueryDetails(ExecutiveOrManagerId, disignationName);
                String expired_date = "";
                //main jsonObject
                JSONObject jsonArrayWithKeysForManager = new JSONObject();
                JSONObject jsonArrayWithKeysForAdmin = new JSONObject();
                for (EnquiryBean enqueryDetail : enqueryDetails) {

                    if (disignationName.equals(SALES_EXECUTIVE)) {
                        expired_date = enqueryDetail.getUpdate_date_time();
                    } else {
                        expired_date = enqueryDetail.getCreated_at();
                    }
                    if (expired_date != null && !expired_date.isEmpty()) {
                        String sender_name = enqueryDetail.getSender_name();
                        String sender_email = enqueryDetail.getSender_email();
                        String sender_mob = enqueryDetail.getSender_mob();
                        String enquiry_address = enqueryDetail.getEnquiry_address();
                        String enquiry_date_time = enqueryDetail.getEnquiry_date_time();
                        String product_name = enqueryDetail.getProduct_name();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//                        }
                        int year = dateTime.getYear();
                        int month = dateTime.getMonthValue();
                        int day = dateTime.getDayOfMonth();
                        int hour = dateTime.getHour();
                        int minute = dateTime.getMinute();
                        int second = dateTime.getSecond();

                        LocalDateTime specificDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
                        LocalDateTime currentDateTime = LocalDateTime.now();
                        Duration duration = Duration.between(specificDateTime, currentDateTime);
                        long hoursDifference = duration.toHours();
                        long daysDifference = duration.toDays();
                        int dayforManager = 2;
                        int dayforAdmin = 2;
                        int currentTimeIncDcr = 10;
                        LocalTime enquiryTime = dateTime.toLocalTime();
                        //current time
                        LocalTime currentTime = currentDateTime.toLocalTime();
                        LocalTime CurrentFutureTime = currentTime.plusMinutes(currentTimeIncDcr);
                        LocalTime CurrentPastTime = currentTime.minusMinutes(currentTimeIncDcr);
                        // Check if the target time lies between pastTime and futureTime
                        boolean isBetween1 = ((enquiryTime.isAfter(CurrentPastTime) || enquiryTime.equals(CurrentPastTime)) && enquiryTime.isBefore(CurrentFutureTime));

                        //for else condition
                        LocalTime startTime = LocalTime.of(9, 55, 0);
                        LocalTime endTime = LocalTime.of(10, 05, 0);
                        boolean isBetween2 = ((currentTime.isAfter(startTime) || currentTime.equals(startTime)) && currentTime.isBefore(endTime));

                        JSONObject obj1 = new JSONObject();
                        obj1.put("ExecutiveOrManagerName", ExecutiveOrManagerName);
                        obj1.put("sender_name", sender_name);
                        obj1.put("sender_email", sender_email);
                        obj1.put("sender_mob", sender_mob);
                        obj1.put("enquiry_address", enquiry_address);
                        obj1.put("enquiry_date_time", enquiry_date_time);
                        obj1.put("product_name", product_name);
                        obj1.put("hoursDifference", hoursDifference);
                        if (hoursDifference >= 48) { //greater than and equal 2 days
                            if (isBetween2) {
                                jsonArrayWithKeysForAdmin = model.addToJSONArray(jsonArrayWithKeysForAdmin, ExecutiveOrManagerName, obj1);
                            }
                        }
                    }
                }

                //mail send krna h.
                JSONArray detailsSendToAdmin = jsonArrayWithKeysForAdmin.optJSONArray(ExecutiveOrManagerName);
                if (detailsSendToAdmin != null) {
                    mailBodyForManager = model.createMailFormate(detailsSendToAdmin, ExecutiveOrManagerName, "11", disignationName, mailBodyForManager);
                }
//                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mailBodyForManager;
    }

    /**
     * send Mail Admin Function
     */
    private void sendMailReminder() {
        JSONObject reminderObj = new JSONObject();
        try {
            model1.setConnection(DBConnection.getConnection(servletContext));
            reminderObj.put("from_id", "");
            reminderObj.put("to_id", "");
            reminderObj.put("type", "Reminder");
            reminderObj.put("ReminderMail", "ReminderMail");
            ArrayList<EnquiryBean> allRemindersDetails;

            try {
                allRemindersDetails = model1.getAllRemindersforMail(reminderObj);
                for (EnquiryBean reminder : allRemindersDetails) {
                    String reminderTime = reminder.getTime();
                    String reminderDate = reminder.getDate();

                    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime enquiryTime = LocalTime.parse(reminderTime, timeformatter);
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate parsedDate = LocalDate.parse(reminderDate, dateformatter);
                    LocalDate currentDate = LocalDate.now();

                    int currentTimeIncDcr = 5;
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    LocalDateTime dateTime = LocalDateTime.parse(reminderTime, formatter);
//                    LocalTime enquiryTime = dateTime.toLocalTime();

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    //current time
                    LocalTime currentTime = currentDateTime.toLocalTime();
                    LocalTime CurrentFutureTime = currentTime.plusMinutes(currentTimeIncDcr);  
                    LocalTime CurrentPastTime = currentTime.minusMinutes(currentTimeIncDcr);
                    // Check if the target time lies between pastTime and futureTime    
                    boolean isBetween1 = ((enquiryTime.isAfter(CurrentPastTime) || enquiryTime.equals(CurrentPastTime)) && enquiryTime.isBefore(CurrentFutureTime));
                    System.out.println(isBetween1);

                    if (parsedDate.isEqual(currentDate)) {      
                        if (isBetween1) {
                            JSONObject objsendMail = model.createMailFormateForReminder(reminder);
                            String result2 = model.sendMailApi(objsendMail);
                            System.out.println(isBetween1);      
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void sendWhatsappMessage(JSONArray adminMobilesList) {
        JSONObject whatsappObj = new JSONObject();
        whatsappObj.put("number", "919669724508");
        whatsappObj.put("type", "text");
        whatsappObj.put("message", "Testing Message one");
//        reminderObj.put("file_name", "Screenshot 2023-05-01 164046.png");
//        reminderObj.put("file_bytes", "");

        String result2 = model.sendWhatsappApi(whatsappObj);
        System.out.println(result2);
    }
}

/**
 * write code without using listner for sheduler
 */
//@Component
//public class SendNotificationScheduler_old {
//
//    @Autowired
//    ServletContext servletContext;
//    Connection connection = null;
//
//    SendNotificationSchedulerModel model = new SendNotificationSchedulerModel();
//    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
//    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
//    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
//    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");
//
////    @Scheduled(fixedRate = 1000 * 10 * 60)
//    public String getSaleExecutiveStatusNotification() {
//        try {
//            model.setConnection(DBConnection.getConnection(servletContext));
//            // status = wsm.saveDeviceRegs(dataString);
//        } catch (Exception e) {
//            System.out.println("error in WebServicesController.saveDeviceRegRecord()- " + e);
//        }
//
//        /**
//         * change code according 1nd approach mail send only Admin.
//         *
//         */
////        JSONObject jObjSaleExecutive = new JSONObject();
////        String salesExecutiveDetails = "";
////        String organisation_name = "APOGEE GNSS";
////        String designation_name = SALES_EXECUTIVE;
////        try {
////            salesExecutiveDetails = model.GetExecutives(organisation_name, designation_name);
////            jObjSaleExecutive = new JSONObject(salesExecutiveDetails);
////            JSONArray jsonArraySalesExecutive = jObjSaleExecutive.getJSONArray("json");
////
////            for (int i = 0; i < jsonArraySalesExecutive.length(); i++) {
////                JSONObject toObj = jsonArraySalesExecutive.getJSONObject(i);
////                int salesExecutiveId = toObj.getInt("key_person_id");
////                String salesExecutiveName = toObj.getString("key_person_name");
//////                System.out.println(salesExecutiveId);
////                ArrayList<EnquiryBean> enqueryDetails = model.getEnqueryDetails(salesExecutiveId);
////                String expired_date = "";
////
////                //main jsonObject
////                JSONObject jsonArrayWithKeysForManager = new JSONObject();
////                JSONObject jsonArrayWithKeysForAdmin = new JSONObject();
////                for (EnquiryBean enqueryDetail : enqueryDetails) {
//////                    String update_date_time = enqueryDetail.getUpdate_date_time();
////                    expired_date = enqueryDetail.getUpdate_date_time();       //if it is null
////                    String sender_name = enqueryDetail.getSender_name();
////                    String sender_email = enqueryDetail.getSender_email();
////                    String sender_mob = enqueryDetail.getSender_mob();
////                    String enquiry_address = enqueryDetail.getEnquiry_address();
////                    String enquiry_date_time = enqueryDetail.getEnquiry_date_time();
////                    String product_name = enqueryDetail.getProduct_name();
////
////                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////                    LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
////                    int year = dateTime.getYear();
////                    int month = dateTime.getMonthValue();
////                    int day = dateTime.getDayOfMonth();
////                    int hour = dateTime.getHour();
////                    int minute = dateTime.getMinute();
////                    int second = dateTime.getSecond();
////
////                    LocalDateTime specificDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
////                    //get CurrentDateTime from database for practice
//////                    String currentDateTime1 = model.getCurrentDateTime();
//////                    LocalDateTime currentDateTime = LocalDateTime.parse(currentDateTime1, formatter);
////                    LocalDateTime currentDateTime = LocalDateTime.now();
////                    Duration duration = Duration.between(specificDateTime, currentDateTime);
//////                    long secondsDifference = duration.getSeconds();
//////                    long minutesDifference = duration.toMinutes();
////                    long hoursDifference = duration.toHours();
////                    long daysDifference = duration.toDays();
////
////                    int dayforManager = 2;
////                    int dayforAdmin = 4;
////                    int currentTimeIncDcr = 10;
////                    LocalTime enquiryTime = dateTime.toLocalTime();
////                    //current time
////                    LocalTime currentTime = currentDateTime.toLocalTime();
////                    LocalTime CurrentFutureTime = currentTime.plusMinutes(currentTimeIncDcr);
////                    LocalTime CurrentPastTime = currentTime.minusMinutes(currentTimeIncDcr);
////                    // Check if the target time lies between pastTime and futureTime
////                    boolean isBetween1 = ((enquiryTime.isAfter(CurrentPastTime) || enquiryTime.equals(CurrentPastTime)) && enquiryTime.isBefore(CurrentFutureTime));
////
////                    //for else condition
////                    LocalTime startTime = LocalTime.of(9, 55, 0);
////                    LocalTime endTime = LocalTime.of(10, 05, 0);
////                    boolean isBetween2 = ((currentTime.isAfter(startTime) || currentTime.equals(startTime)) && currentTime.isBefore(endTime));
////
////                    JSONObject obj1 = new JSONObject();
////                    obj1.put("salesExecutiveName", salesExecutiveName);
////                    obj1.put("sender_name", sender_name);
////                    obj1.put("sender_email", sender_email);
////                    obj1.put("sender_mob", sender_mob);
////                    obj1.put("enquiry_address", enquiry_address);
////                    obj1.put("enquiry_date_time", enquiry_date_time);
////                    obj1.put("product_name", product_name);
////                    obj1.put("hoursDifference", hoursDifference);
////
////                    if (hoursDifference >= 48) { //greater than and equal 2 days
//////                        if (isBetween1 && (hoursDifference ==48)) {
////                        if (isBetween1 && (daysDifference == dayforManager)) {
////                            jsonArrayWithKeysForManager = model.addToJSONArray(jsonArrayWithKeysForManager, salesExecutiveName, obj1);
////                        } else if (isBetween2) {
////                            jsonArrayWithKeysForManager = model.addToJSONArray(jsonArrayWithKeysForManager, salesExecutiveName, obj1);
////                        }
////                    }
////                    if (hoursDifference >= 96) { //greater than and equal 4 days
//////                        if (isBetween1 && hoursDifference == 96) {
////                        if (isBetween1 && daysDifference == dayforAdmin) {
////                            jsonArrayWithKeysForAdmin = model.addToJSONArray(jsonArrayWithKeysForAdmin, salesExecutiveName, obj1);
////                        } else if (isBetween2) {
////                            jsonArrayWithKeysForAdmin = model.addToJSONArray(jsonArrayWithKeysForAdmin, salesExecutiveName, obj1);
////                        }
////                    }
////
////                }
////
////                //mail send krna h.
////                JSONArray detailsSendToManager = jsonArrayWithKeysForManager.optJSONArray(salesExecutiveName);
////                JSONArray detailsSendToAdmin = jsonArrayWithKeysForAdmin.optJSONArray(salesExecutiveName);
////
////                if (detailsSendToManager != null) {
////                    JSONObject sendMailManager = model.createMailFormate(detailsSendToManager, salesExecutiveName, 99);
////                    String result1 = model.sendMailApi(sendMailManager);
////                }
////
////                if (detailsSendToAdmin != null) {
////                    JSONObject sendMailAdmin = model.createMailFormate(detailsSendToManager, salesExecutiveName, 114);
////                    String result2 = model.sendMailApi(sendMailAdmin);
////                }
////            }
////
////        } catch (Exception e) {
////            System.out.println(e);
////        }
////
////        return null;
//        /**
//         * change code according 2nd approach mail send only Admin.
//         *
//         */
//        //        try {
//        //            int TwoDays = 2;
//        //
//        //            JSONObject jsonObject = model.getsalesManagerAndExecutiveIds();
//        //            JSONArray enqueryDetailsOfExecutive = jsonObject.optJSONArray("executeve");
//        //
//        //            if (enqueryDetailsOfExecutive != null) {
//        //                for (int i = 0; i < enqueryDetailsOfExecutive.length(); i++) {
//        //                    JSONObject toObj = enqueryDetailsOfExecutive.getJSONObject(i);
//        //                    String assign_to = toObj.getString("assigned_to");
//        //                    String expired_date = toObj.getString("update_date_time");
//        //
//        //                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        //                    LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//        //                    Duration duration = model.findDuration(dateTime);
//        //                    long hoursDifference = duration.toHours();
//        //                    long daysDifference = duration.toDays();
//        //
//        //                    LocalDateTime currentDateTime = LocalDateTime.now();
//        //                    boolean isBetween1 = model.findTimeAfterTwoDays(dateTime, currentDateTime);
//        //                    boolean isBetween2 = model.findTimeTen(currentDateTime);
//        //
//        //                    String executiveDetails = model.getPersonDetailByIds(assign_to);
//        //                    JSONArray executiveDetailsArray = new JSONArray(executiveDetails);
//        //                    String salesExecutiveNamesString = "";
//        //                    for (int j = 0; j < executiveDetailsArray.length(); j++) {
//        //                        JSONObject singleExecutiveDetailsObj = executiveDetailsArray.getJSONObject(j);
//        //                        String salesExecutiveName = singleExecutiveDetailsObj.getString("key_person_name");
//        //                        String designation = singleExecutiveDetailsObj.getString("designation");
//        //                        String salesWithDesignation = salesExecutiveName + "(" + designation + ")";
//        //                        if (j == 0) {
//        //                            salesExecutiveNamesString = salesExecutiveNamesString + salesWithDesignation;
//        //                        } else {
//        //                            salesExecutiveNamesString = salesExecutiveNamesString + "," + salesWithDesignation;
//        //                        }
//        //                    }
//        //                    String name = "executive";
//        //                    JSONObject sendMailAdmin = model.createMailFormate(toObj, salesExecutiveNamesString, "114", name);
//        //                    if (hoursDifference >= 48) {
//        //                        if (isBetween1 && (daysDifference == TwoDays)) {
//        ////                            String result1 = model.sendMailApi(sendMailAdmin);
//        //                        } else if (isBetween2) {
//        ////                            String result1 = model.sendMailApi(sendMailAdmin);
//        //                        }
//        //                    }
//        //                }
//        //            }
//        //
//        //            JSONArray enqueryDetailsOfManager = jsonObject.optJSONArray("manager");
//        //
//        //            if (enqueryDetailsOfManager != null) {
//        //                for (int i = 0; i < enqueryDetailsOfManager.length(); i++) {
//        //                    JSONObject toObj = enqueryDetailsOfManager.getJSONObject(i);
//        //                    String assign_to = toObj.getString("assigned_to");
//        //                    String expired_date = toObj.getString("update_date_time");
//        //
//        //                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        //                    LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//        //                    Duration duration = model.findDuration(dateTime);
//        //                    long hoursDifference = duration.toHours();
//        //                    long daysDifference = duration.toDays();
//        //
//        //                    LocalDateTime currentDateTime = LocalDateTime.now();
//        //                    boolean isBetween1 = model.findTimeAfterTwoDays(dateTime, currentDateTime);
//        //                    boolean isBetween2 = model.findTimeTen(currentDateTime);
//        //
//        //                    String managerDetails = model.getPersonDetailByIds(assign_to);
//        //                    JSONArray managerDetailsArray = new JSONArray(managerDetails);
//        //                    String salesManagerNamesString = "";
//        //                    for (int j = 0; j < managerDetailsArray.length(); j++) {
//        //                        JSONObject singleManagerDetailsObj = managerDetailsArray.getJSONObject(j);
//        //                        String salesManagerName = singleManagerDetailsObj.getString("key_person_name");
//        //                        String designation = singleManagerDetailsObj.getString("designation");
//        //                        String salesWithDesignation = salesManagerName + "(" + designation + ")";
//        //                        if (j == 0) {
//        //                            salesManagerNamesString = salesManagerNamesString + salesWithDesignation;
//        //                        } else {
//        //                            salesManagerNamesString = salesManagerNamesString + "," + salesWithDesignation;
//        //                        }
//        //                    }
//        //                    String name = "manager";
//        //                    JSONObject sendMailAdmin = model.createMailFormate(toObj, salesManagerNamesString, "114", name);
//        //                    if (hoursDifference >= 48) {
//        //                        if (isBetween1 && (daysDifference == TwoDays)) {
//        ////                            String result1 = model.sendMailApi(sendMailAdmin);
//        //                        } else if (isBetween2) {
//        ////                            String result1 = model.sendMailApi(sendMailAdmin);
//        //                        }
//        //                    }
//        //                }
//        //            }
//        //
//        //        }
////        catch (Exception e) {
////            System.out.println(e);
////        }
////
////        return null;
//        /**
//         * change code according 3rd approach mail send only Admin.
//         *
//         */
//        JSONObject jObjSaleExecutive = new JSONObject();
//        JSONObject jObjSaleManager = new JSONObject();
//        JSONObject jObjAdmin = new JSONObject();
//        String salesExecutiveDetails = "";
//        String salesManagerDetails = "";
//        String adminDetails = "";
//        String organisation_name = "APOGEE GNSS";
//        String designation_name_SALES_EXECUTIVE = SALES_EXECUTIVE;
//        String designation_name_SALES_MANAGER = SALES_MANAGER;
//        String designation_name_ADMIN = SALES_SUPERVISOR;
//        try {
//            String mailBodyForManager = "";
//
//            //send mail to admin of sales manager
//            adminDetails = model.GetExecutives(organisation_name, designation_name_ADMIN);
//            jObjAdmin = new JSONObject(adminDetails);
//            JSONArray jsonArrayAdmin = jObjAdmin.getJSONArray("json");
//            JSONArray newjsonArrayAdmin = new JSONArray();
//            JSONArray toListIds = new JSONArray();
//            for (int i = 0; i < jsonArrayAdmin.length(); i++) {
//                JSONObject toObj = jsonArrayAdmin.getJSONObject(i);
//                String designation = toObj.getString("designation");
//                if (designation.equals(designation_name_ADMIN)) {
//                    JSONObject toObj1 = new JSONObject();
//                    int key_person_id = toObj.getInt("key_person_id");
//                    String to_id = String.valueOf(key_person_id);
//                    toObj1.put("to_id", to_id);
//                    toListIds.put(toObj1);
//
////                    newjsonArrayAdmin.put(toObj);
//                }
//            }
//
//            //send mail to admin of sales executive
//            salesExecutiveDetails = model.GetExecutives(organisation_name, designation_name_SALES_EXECUTIVE);
//            jObjSaleExecutive = new JSONObject(salesExecutiveDetails);
//            JSONArray jsonArraySalesExecutive = jObjSaleExecutive.getJSONArray("json");
//            mailBodyForManager = sendMailAdmin(jsonArraySalesExecutive, designation_name_SALES_EXECUTIVE, newjsonArrayAdmin, mailBodyForManager);
//
//            //send mail to admin of sales manager
//            salesManagerDetails = model.GetExecutives(organisation_name, designation_name_SALES_MANAGER);
//            jObjSaleManager = new JSONObject(salesManagerDetails);
//            JSONArray jsonArraySalesManager = jObjSaleManager.getJSONArray("json");
//            JSONArray newjsonArraySalesManager = new JSONArray();
//            for (int i = 0; i < jsonArraySalesManager.length(); i++) {
//                JSONObject toObj = jsonArraySalesManager.getJSONObject(i);
//
//                String designation = toObj.getString("designation");
//                if (designation.equals(designation_name_SALES_MANAGER)) {
//                    newjsonArraySalesManager.put(toObj);
//                }
//            }
//            String allSendMailAdmin = sendMailAdmin(newjsonArraySalesManager, designation_name_SALES_MANAGER, newjsonArrayAdmin, mailBodyForManager);
////            System.out.println("allSendMailAdmin" + allSendMailAdmin);  
//            if (!allSendMailAdmin.equals("")) {
//                //        //send mail
//                String subjectId = "1";
//                String subjectName = model.getSubject(subjectId);
//                JSONObject objsendMail = new JSONObject();
//                objsendMail.put("to", toListIds);
//                objsendMail.put("subjectId", subjectId);
//                objsendMail.put("subjectName", subjectName);
//                objsendMail.put("body", "Hello Sir, <br/><br/>" + allSendMailAdmin);
////                String result2 = model.sendMailApi(objsendMail);  
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }
//
//    private String sendMailAdmin(JSONArray jsonArrayExecutiveOrManager, String disignationName, JSONArray newjsonArrayAdmin, String mailBodyForManager) {
//        try {
//            for (int i = 0; i < jsonArrayExecutiveOrManager.length(); i++) {
//                JSONObject toObj = jsonArrayExecutiveOrManager.getJSONObject(i);
//                int ExecutiveOrManagerId = toObj.getInt("key_person_id");
//                String ExecutiveOrManagerName = toObj.getString("key_person_name");
//                String designation = toObj.getString("designation");
//                ArrayList<EnquiryBean> enqueryDetails = model.getEnqueryDetails(ExecutiveOrManagerId, disignationName);
//                String expired_date = "";
//                //main jsonObject
//                JSONObject jsonArrayWithKeysForManager = new JSONObject();
//                JSONObject jsonArrayWithKeysForAdmin = new JSONObject();
//                for (EnquiryBean enqueryDetail : enqueryDetails) {
//                    if (disignationName.equals(SALES_EXECUTIVE)) {
//                        expired_date = enqueryDetail.getUpdate_date_time();
//                    } else {
//                        expired_date = enqueryDetail.getCreated_at();
//                    }
//                    if (expired_date != null && !expired_date.isEmpty()) {
//                        String sender_name = enqueryDetail.getSender_name();
//                        String sender_email = enqueryDetail.getSender_email();
//                        String sender_mob = enqueryDetail.getSender_mob();
//                        String enquiry_address = enqueryDetail.getEnquiry_address();
//                        String enquiry_date_time = enqueryDetail.getEnquiry_date_time();
//                        String product_name = enqueryDetail.getProduct_name();
//
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                        LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
////                        }
//                        int year = dateTime.getYear();
//                        int month = dateTime.getMonthValue();
//                        int day = dateTime.getDayOfMonth();
//                        int hour = dateTime.getHour();
//                        int minute = dateTime.getMinute();
//                        int second = dateTime.getSecond();
//
//                        LocalDateTime specificDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
//                        LocalDateTime currentDateTime = LocalDateTime.now();
//                        Duration duration = Duration.between(specificDateTime, currentDateTime);
//                        long hoursDifference = duration.toHours();
//                        long daysDifference = duration.toDays();
//                        int dayforManager = 2;
//                        int dayforAdmin = 2;
//                        int currentTimeIncDcr = 10;
//                        LocalTime enquiryTime = dateTime.toLocalTime();
//                        //current time
//                        LocalTime currentTime = currentDateTime.toLocalTime();
//                        LocalTime CurrentFutureTime = currentTime.plusMinutes(currentTimeIncDcr);
//                        LocalTime CurrentPastTime = currentTime.minusMinutes(currentTimeIncDcr);
//                        // Check if the target time lies between pastTime and futureTime
//                        boolean isBetween1 = ((enquiryTime.isAfter(CurrentPastTime) || enquiryTime.equals(CurrentPastTime)) && enquiryTime.isBefore(CurrentFutureTime));
//
//                        //for else condition
//                        LocalTime startTime = LocalTime.of(9, 55, 0);
//                        LocalTime endTime = LocalTime.of(10, 05, 0);
//                        boolean isBetween2 = ((currentTime.isAfter(startTime) || currentTime.equals(startTime)) && currentTime.isBefore(endTime));
//
//                        JSONObject obj1 = new JSONObject();
//                        obj1.put("ExecutiveOrManagerName", ExecutiveOrManagerName);
//                        obj1.put("sender_name", sender_name);
//                        obj1.put("sender_email", sender_email);
//                        obj1.put("sender_mob", sender_mob);
//                        obj1.put("enquiry_address", enquiry_address);
//                        obj1.put("enquiry_date_time", enquiry_date_time);
//                        obj1.put("product_name", product_name);
//                        obj1.put("hoursDifference", hoursDifference);
//                        if (hoursDifference >= 48) { //greater than and equal 2 days
//                            if (isBetween2) {
//                                jsonArrayWithKeysForAdmin = model.addToJSONArray(jsonArrayWithKeysForAdmin, ExecutiveOrManagerName, obj1);
//                            }
//                        }
//                    }
//                }
//
//                //mail send krna h.
//                JSONArray detailsSendToAdmin = jsonArrayWithKeysForAdmin.optJSONArray(ExecutiveOrManagerName);
////                for (int j = 0; j < newjsonArrayAdmin.length(); j++) {
////                    JSONObject toobj2 = newjsonArrayAdmin.getJSONObject(j);
////                    int key_person_id = toobj2.getInt("key_person_id");
////                    String to_id = String.valueOf(key_person_id);
//                if (detailsSendToAdmin != null) {
////                        JSONObject sendMailAdmin = model.createMailFormate(detailsSendToAdmin, ExecutiveOrManagerName, to_id, disignationName);
//                    mailBodyForManager = model.createMailFormate(detailsSendToAdmin, ExecutiveOrManagerName, "11", disignationName, mailBodyForManager);
////                        String result2 = model.sendMailApi(sendMailAdmin);
//                }
////                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return mailBodyForManager;
//    }
//}
