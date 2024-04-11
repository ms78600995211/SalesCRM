/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.bean.DashboardBean;
import com.apogee.model.DashboardModel;
import com.apogee.bean.EnquiryBean;
import com.apogee.bean.NotificationBean;
import com.apogee.model.EnquiryModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import com.apogee.notification.SendNotificationSchedulerModel;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author admin
 */
@WebServlet(name = "DashboardController", urlPatterns = {"/DashboardController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class DashboardController extends HttpServlet {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        int lowerLimit = 0, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }
        String role = (String) session.getAttribute("role");

        String task = request.getParameter("task");
        if (task == null || task == "") {
            task = "";
        }
        DashboardModel model = new DashboardModel();
        EnquiryModel model1 = new EnquiryModel();
        int total_enquiries = 0;
        if (task.equals("logout")) {
            session.invalidate();
            request.setAttribute("message", "Logout Successfully");
            request.getRequestDispatcher("login_page").forward(request, response);
        }
        try {
            model.setConnection(DBConnection.getConnection(ctx));
            model1.setConnection(DBConnection.getConnection(ctx));
            String key_person_id = (String) session.getAttribute("key_person_id");
            String organisation = "APOGEE GNSS";
            String organisation_id = "7";
            String baseiname;

            if (role.equals(SALES_EXECUTIVE)) {
                ArrayList<DashboardBean> lists = model.getAllDegignatedPersonsListForExecutive(role, organisation, key_person_id);
                request.setAttribute("lists", lists);
                int soldthismonth;
                int totalsold;
                int pendinglist = model.getPendingEnquiriesforExecutives(key_person_id);
                request.setAttribute("pendinglist", pendinglist);

                total_enquiries = model.getCountEnquiriesforExecutives(key_person_id);
                
                soldthismonth = model.getSoldThisMonthCountForExecutives(key_person_id);
                request.setAttribute("soldthismonth", soldthismonth);
                totalsold = model.gettotalsoldCountForExecutives(key_person_id);
                request.setAttribute("totalsold", totalsold);

                ArrayList<EnquiryBean> list = model.getAllLatestEnquiriesForExecutives(key_person_id, ctx);
                request.setAttribute("list", list);

            } else if (role.equals(SALES_MANAGER)) {

                ArrayList<DashboardBean> lists = model.getAllDegignatedPersonsListforsalesmanager(role, organisation, key_person_id);
                request.setAttribute("lists", lists);
                total_enquiries = model.getCountEnquiries(role, organisation, key_person_id);

                ArrayList<EnquiryBean> list = model.getAllEnquiries(ctx);
                request.setAttribute("list", list);

                int pendinglist;
                int soldthismonth;
                int totalsold;

                try {
                    pendinglist = model.getPendingEnquiries(role, organisation, key_person_id);
                    request.setAttribute("pendinglist", pendinglist);
                    soldthismonth = model.getSoldThisMonthCount(role, organisation, key_person_id);
                    request.setAttribute("soldthismonth", soldthismonth);
                    totalsold = model.gettotalsoldCount(role, organisation, key_person_id);
                    request.setAttribute("totalsold", totalsold);
                } catch (Exception ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                ArrayList<DashboardBean> lists = model.getAllDegignatedPersonsListforsalessupervisor(role, organisation, key_person_id);
                request.setAttribute("lists", lists);

                total_enquiries = model.getCountEnquiries(role, organisation, key_person_id);

                ArrayList<EnquiryBean> list = model.getAllEnquiries(ctx);

                request.setAttribute("list", list);
                int pendinglist;
                int soldthismonth;
                int totalsold;
                try {
                    pendinglist = model.getPendingEnquiries(role, organisation, key_person_id);
                    request.setAttribute("pendinglist", pendinglist);
                    soldthismonth = model.getSoldThisMonthCount(role, organisation, key_person_id);
                    request.setAttribute("soldthismonth", soldthismonth);
                    totalsold = model.gettotalsoldCount(role, organisation, key_person_id);
                    request.setAttribute("totalsold", totalsold);
                } catch (Exception ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (session.getAttribute("notificationBeanList") == null && session.getAttribute("notificationCount") == null) {
                    showDashboardNotification(session, ctx);

                }
            }

            request.setAttribute("total_enquiries", total_enquiries);

            JSONObject reminderObj = new JSONObject();
            try {
                reminderObj.put("from_id", key_person_id);
                reminderObj.put("to_id", "");
                reminderObj.put("type", "Reminder");
                reminderObj.put("ReminderMail", "");
                ArrayList<EnquiryBean> allRemindersDetails;

                try {
                    allRemindersDetails = model1.getAllRemindersDetails(reminderObj);
                    request.setAttribute("allRemindersDetails", allRemindersDetails);
                } catch (Exception ex) {
                    Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (task.equals("deleteReminder")) {
                String reminder_id = request.getParameter("reminder_id");
                if (reminder_id == null) {
                    reminder_id = "";
                }
                if (!reminder_id.equals("")) {
                    String message = model.deleteReminder(reminder_id);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.closeConnection();
        request.getRequestDispatcher("/dashboard").forward(request, response);

    }

    /**
     * change code according 1nd approach mail send only Admin.
     *
     */
    SendNotificationSchedulerModel notificationModel = new SendNotificationSchedulerModel();

    private void showDashboardNotification(HttpSession session, ServletContext ctx) {

        JSONObject jObjSaleExecutive = new JSONObject();
        JSONObject jObjSaleManager = new JSONObject();
        String salesExecutiveDetails = "";
        String salesManagerDetails = "";
        String organisation_name = "APOGEE GNSS";
        String designation_name_SALES_EXECUTIVE = SALES_EXECUTIVE;
        String designation_name_SALES_MANAGER = SALES_MANAGER;
        List<NotificationBean> notificationBeanList = new ArrayList<>();
        int count = 0;
        try {
            notificationModel.setConnection(DBConnection.getConnection(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //send mail to admin of sales executive
            salesExecutiveDetails = notificationModel.GetExecutives(organisation_name, designation_name_SALES_EXECUTIVE);
            jObjSaleExecutive = new JSONObject(salesExecutiveDetails);
            JSONArray jsonArraySalesExecutive = jObjSaleExecutive.getJSONArray("json");
            List<NotificationBean> executiveNotification = showNotificationAdmin(jsonArraySalesExecutive, designation_name_SALES_EXECUTIVE, notificationBeanList);

            //send mail to admin of sales manager
            salesManagerDetails = notificationModel.GetExecutives(organisation_name, designation_name_SALES_MANAGER);
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
            List<NotificationBean> allNotification = showNotificationAdmin(newjsonArraySalesManager, designation_name_SALES_MANAGER, executiveNotification);
            session.setAttribute("allNotification", allNotification);
            for (NotificationBean notification : allNotification) {
                int count1 = notification.getCount();
                count = count += count1;
            }
            session.setAttribute("allNotificationCount", allNotification.size());
            session.setAttribute("allNotificationCount", count);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private List<NotificationBean> showNotificationAdmin(JSONArray jsonArrayExecutiveOrManager, String executiveormanager, List<NotificationBean> notificationBeanList) {
        try {
            String user = "";
            if (executiveormanager.equals(SALES_EXECUTIVE)) {
                user = SALES_EXECUTIVE;
            } else {
                user = SALES_MANAGER;
            }
            for (int i = 0; i < jsonArrayExecutiveOrManager.length(); i++) {
                NotificationBean notification = new NotificationBean();
                JSONObject toObj = jsonArrayExecutiveOrManager.getJSONObject(i);
                int ExecutiveOrManagerId = toObj.getInt("key_person_id");
                String ExecutiveOrManagerName = toObj.getString("key_person_name");
                ArrayList<EnquiryBean> enqueryDetails = notificationModel.getEnqueryDetails(ExecutiveOrManagerId, executiveormanager);
                String commonNotificationFormate = " " + user + " [" + ExecutiveOrManagerName + "] has not worked on [" + enqueryDetails.size() + "] enquiries till now. <br/><br/> ";
                notification.setBody(commonNotificationFormate);
                notification.setCount(enqueryDetails.size());
                notificationBeanList.add(notification);
//                count = count +=enqueryDetails.size();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return notificationBeanList;
    }

    /**
     * change code according 2nd approach mail send only Admin.
     *
     */
//    private void showDashboardNotification(HttpSession session, ServletContext ctx) {
//        SendNotificationSchedulerModel notificationModel = new SendNotificationSchedulerModel();
//        try {
//            notificationModel.setConnection(DBConnection.getConnection(ctx));
//        } catch (SQLException ex) {
//            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//
//            JSONObject jsonObject = notificationModel.getsalesManagerAndExecutiveIds();
//            JSONArray enqueryDetailsOfExecutive = jsonObject.optJSONArray("executeve");
//
//            List<NotificationBean> notificationBeanList = new ArrayList<>();
//
//            if (enqueryDetailsOfExecutive != null) {
//                for (int i = 0; i < enqueryDetailsOfExecutive.length(); i++) {
//                    JSONObject toObj = enqueryDetailsOfExecutive.getJSONObject(i);
//                    String assign_to = toObj.getString("assigned_to");
//                    String expired_date = toObj.getString("update_date_time");
//
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//                    Duration duration = notificationModel.findDuration(dateTime);
//                    long hoursDifference = duration.toHours();
//
//                    String executiveDetails = notificationModel.getPersonDetailByIds(assign_to);
//                    JSONArray executiveDetailsArray = new JSONArray(executiveDetails);
//                    String salesExecutiveNamesString = "";
//                    for (int j = 0; j < executiveDetailsArray.length(); j++) {
//                        JSONObject singleExecutiveDetailsObj = executiveDetailsArray.getJSONObject(j);
//                        String salesExecutiveName = singleExecutiveDetailsObj.getString("key_person_name");
//                        String designation = singleExecutiveDetailsObj.getString("designation");
//                        String salesWithDesignation = salesExecutiveName + "(" + designation + ")";
//                        if (j == 0) {
//                            salesExecutiveNamesString = salesExecutiveNamesString + salesWithDesignation;
//                        } else {
//                            salesExecutiveNamesString = salesExecutiveNamesString + "," + salesWithDesignation;
//                        }
//                    }
//                    String name = "executiveDashboard";
//                    JSONObject sendMailAdmin = notificationModel.createMailFormate(toObj, salesExecutiveNamesString, "114", name);
//                    if (hoursDifference >= 48) {
//                        NotificationBean notificationExecu = new NotificationBean();
//                        notificationExecu.setSubjectName(sendMailAdmin.getString("subjectName"));
//                        notificationExecu.setBody(sendMailAdmin.getString("body"));
//                        notificationExecu.setDuration(hoursDifference);
//                        notificationBeanList.add(notificationExecu);
//                    }
//                }
//            }
//
//            JSONArray enqueryDetailsOfManager = jsonObject.optJSONArray("manager");
//
//            if (enqueryDetailsOfManager != null) {
//                for (int i = 0; i < enqueryDetailsOfManager.length(); i++) {
//                    JSONObject toObj = enqueryDetailsOfManager.getJSONObject(i);
//                    String assign_to = toObj.getString("assigned_to");
//                    String expired_date = toObj.getString("update_date_time");
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    LocalDateTime dateTime = LocalDateTime.parse(expired_date, formatter);
//                    Duration duration = notificationModel.findDuration(dateTime);
//                    long hoursDifference = duration.toHours();
//
//                    String managerDetails = notificationModel.getPersonDetailByIds(assign_to);
//                    JSONArray managerDetailsArray = new JSONArray(managerDetails);
//                    String salesManagerNamesString = "";
//                    for (int j = 0; j < managerDetailsArray.length(); j++) {
//                        JSONObject singleManagerDetailsObj = managerDetailsArray.getJSONObject(j);
//                        String salesManagerName = singleManagerDetailsObj.getString("key_person_name");
//                        String designation = singleManagerDetailsObj.getString("designation");
//                        String salesWithDesignation = salesManagerName + "(" + designation + ")";
//                        if (j == 0) {
//                            salesManagerNamesString = salesManagerNamesString + salesWithDesignation;
//                        } else {
//                            salesManagerNamesString = salesManagerNamesString + "," + salesWithDesignation;
//                        }
//                    }
//                    String name = "managerDashboard";
//                    JSONObject sendMailAdmin = notificationModel.createMailFormate(toObj, salesManagerNamesString, "114", name);
//                    if (hoursDifference >= 48) {
//                        NotificationBean notificationManag = new NotificationBean();
//                        notificationManag.setSubjectName(sendMailAdmin.getString("subjectName"));
//                        notificationManag.setBody(sendMailAdmin.getString("body"));
//                        notificationManag.setDuration(hoursDifference);
//                        notificationBeanList.add(notificationManag);
//                    }
//                }
//            }
//
//            session.setAttribute("notificationBeanList", notificationBeanList);
//            session.setAttribute("notificationCount", notificationBeanList.size());
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
}
