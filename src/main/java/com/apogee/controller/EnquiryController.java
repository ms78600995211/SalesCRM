/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.bean.DashboardBean;
import com.apogee.model.EnquiryModel;
import com.apogee.bean.EnquiryBean;
import com.apogee.model.DashboardModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "EnquiryController", urlPatterns = {"/EnquiryController"})
public class EnquiryController extends HttpServlet {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }
        String role = (String) session.getAttribute("role");
        String key_person_id = (String) session.getAttribute("key_person_id");

        ServletContext ctx = getServletContext();
        EnquiryModel model = new EnquiryModel();
        try {
            model.setConnection(DBConnection.getConnection(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Optimize on 28-08-2023
        if (null == session.getAttribute("personXMLsaved")) {
            try {
                DashboardModel dashmodel = new DashboardModel();
                String kpAllData = dashmodel.getAllKeyPersonDetails();
                // save data to xml file
                String folderPath = ctx.getRealPath("/assets/allkeyPersonData.xml");
                File outputFile = new File(folderPath);
//                if (outputFile.exists()) {    
//                    model.getPersonNameUsingId(folderPath);
//                }else{
                String str = model.saveDataToXml(kpAllData, ctx);
                if (str.equals("saved")) {
                    session.setAttribute("personXMLsaved", str);
                }
//                }
            } catch (Exception e) {
                System.out.println("com.apogee.controller.EnquiryController.processRequest(): " + e);
            }
        }
        // Optimize on 28-08-2023

        String task = request.getParameter("task");
        if (task == null || task == "") {
            task = "";
        }
        String refresh = request.getParameter("refresh");
        if (refresh == null || refresh == "") {
            refresh = "";
        }
        if (refresh.equals("Refresh")) {
            try {
                IndiaMartEnquiriesService service = new IndiaMartEnquiriesService();
                IndiaMartEnquiriesService.VodTimerTask obj2 = service.new VodTimerTask();
                obj2.getGNSSEnquiriesFromIndiaMart();
//            ServletContextEvent servletContextEvent = new ServletContextEvent(ctx);
//            service.contextInitialized(servletContextEvent);
            } catch (MalformedURLException ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String source_search_id = "";
        String status_search_id = "";
        String designation_search_id = "";
        String message = "";
        String msgbgcolor = "";
        source_search_id = request.getParameter("source_id");
        status_search_id = request.getParameter("status_id");
        designation_search_id = request.getParameter("designation_id");
        if (source_search_id == null) {
            source_search_id = "";
        }
        if (status_search_id == null) {
            status_search_id = "";
        }
        if (designation_search_id == null) {
            designation_search_id = "";
        }

//         try {
//            String JQstring = request.getParameter("action1");
//            String q = request.getParameter("str");   // field own input           
//            if (JQstring != null) {
//                PrintWriter out = response.getWriter();
//                List<String> list = null;
//                
//                if (JQstring.equals("getMarketingVertical")) {
//                    list = model.getMarketingVertical(q);
//                }
//                
//                org.json.simple.JSONObject gson = new org.json.simple.JSONObject();
//                gson.put("list", list);
//                out.println(gson);
//
//                model.closeConnection();
//                return;
//            }
//        } catch (Exception e) {
//            System.out.println("\n Error --SalesEnquiryController get JQuery Parameters Part-" + e);
//        }
        if (task.equals("assign_in_bulk")) {

            String tottal_ids = request.getParameter("tottal_ids");
            String[] enquiry_table_id = tottal_ids.split(",");
//            int length = enquiry_table_ids.length;
//            String enquiry_table_id = enquiry_table_ids[0];

            String assign_by = (String) session.getAttribute("key_person_id");
            String assign_to = request.getParameter("assign_to");
            String assign_to_id = "";
            String assign_to_name = "";
            if (!assign_to.equals("")) {
                String[] assignto = assign_to.split("-");
                assign_to_id = assignto[0];
                assign_to_name = assignto[1];
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String updated_date_time = formattedDateTime;
            String created_by = (String) session.getAttribute("key_person_id");

            String active = "Y";
            String enquiry_status_id = "11";
            String added = model.addAsignedToExecutiveInBulk(assign_by, assign_to_id, updated_date_time, created_by, enquiry_table_id, active, enquiry_status_id);
            if (added.equals("Added")) {
//                request.setAttribute("message", "Data Added Successfully.");
//                request.setAttribute("msgbgcolor", "green");
                message = "Data Added Successfully.";
                msgbgcolor = "green";

            } else {
//                request.setAttribute("message", "Some Error Try Again.");
//                request.setAttribute("msgbgcolor", "red");
                message = "Some Error Try Again.";
                msgbgcolor = "red";
            }

        } else if (task.equals("GetAllExecutive")) {
            String organisation_name = "";
            String designation_name = "";
            if (role.equals(SALES_SUPERVISOR)) {
                organisation_name = "APOGEE GNSS";
                designation_name = SALES_MANAGER;
            }
            if (role.equals(SALES_MANAGER)) {
                organisation_name = "APOGEE GNSS";
                designation_name = SALES_EXECUTIVE;
            }

            JSONObject jsnobject = new JSONObject();
            String baseiname = "";
            String alldata = "";
            try {
                baseiname = model.GetExecutives(organisation_name, designation_name);
                jsnobject = new JSONObject(baseiname);
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.print(jsnobject.length());
            Iterator<String> keys = jsnobject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    Object keyvalue = jsnobject.get(key);
                    alldata = jsnobject.get(key).toString();
                    System.err.println("all data before -- " + alldata);
                } catch (Exception ex) {
                }
            }
            response.getWriter().write(alldata);
            return;
        } else if (task.equals("GetAllStatus")) {
            ArrayList<EnquiryBean> statuslist = model.getStatusList();
            Gson gson = new Gson();
            String json = gson.toJson(statuslist);
            // Send the JSON data back to the client
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } else if (task.equals("Submit form")) {
            EnquiryBean bean = new EnquiryBean();
            bean.setSender_name(request.getParameter("sender_name"));
            bean.setSender_mob(request.getParameter("sender_mob"));
            bean.setSender_email(request.getParameter("sender_email"));
            bean.setSender_alternate_mob(request.getParameter("other_mobile"));
            bean.setSender_alternate_email(request.getParameter("other_email"));
            String enquiry_source_table_id = request.getParameter("enquiry_source_table_id");
            int i = Integer.parseInt(enquiry_source_table_id);
            bean.setEnquiry_source_table_id(i);
            String marketing_vertical_id = request.getParameter("marketing_vertical_id");
            int j = Integer.parseInt(marketing_vertical_id);
            bean.setMarketing_vertical_id(j);
            bean.setEnquiry_address(request.getParameter("enquiry_address"));
            bean.setEnquiry_message(request.getParameter("enquiry_message"));
            String state = "";
            String city = "";
            String sender_company_name = "";
            String product_name = "";
            product_name = request.getParameter("product_name");
            sender_company_name = request.getParameter("sender_company_name");
            city = request.getParameter("city");
            state = request.getParameter("state");

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String currentdatetime = currentDateTime.format(formatter);
            String created_by = key_person_id;
            int added = model.AddManualEnquiry(bean, currentdatetime, created_by, state, city, sender_company_name, product_name);
            if (added > 0) {

                message = "Added Successfully";
                msgbgcolor = "green";

            } else {
                message = "Some Error Try Again";
                msgbgcolor = "red";
            }

        } else if (task.equals("Update")) {
            EnquiryBean bean = new EnquiryBean();
            int enquiry_table_id = Integer.parseInt(request.getParameter("enquiry_table_id"));
            bean.setSender_name(request.getParameter("sender_name"));
            bean.setSender_mob(request.getParameter("sender_mob"));
            bean.setSender_alternate_email(request.getParameter("other_email"));
            bean.setSender_alternate_mob(request.getParameter("other_mobile"));
            bean.setSender_email(request.getParameter("sender_email"));
//            String enquiry_source_table_id = request.getParameter("enquiry_source_table_id");
//            int i = Integer.parseInt(enquiry_source_table_id);
//            bean.setEnquiry_source_table_id(i);
//            String marketing_vertical_id = request.getParameter("marketing_vertical_id");
//            int j = Integer.parseInt(marketing_vertical_id);
//            bean.setMarketing_vertical_id(j);
            bean.setEnquiry_address(request.getParameter("enquiry_address"));
            bean.setEnquiry_message(request.getParameter("enquiry_message"));
            String state = "";
            String city = "";
            String sender_company_name = "";
            String product_name = "";
            product_name = request.getParameter("product_name");
            sender_company_name = request.getParameter("sender_company_name");
            city = request.getParameter("enquiry_city");
            state = request.getParameter("enquiry_state");

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a");
            String currentdatetime = currentDateTime.format(formatter);
            String created_by = key_person_id;
            int added = model.EditEnquiry(bean, enquiry_table_id, currentdatetime, created_by, state, city, sender_company_name, product_name);
            if (added > 0) {
                message = "Updated Successfully";
                msgbgcolor = "green";

            } else {
                message = "Some Error Try Again";
                msgbgcolor = "red";

            }
        } else if (task.equals("Search Result")) {
            source_search_id = request.getParameter("source_id");
            status_search_id = request.getParameter("status_id");

            designation_search_id = request.getParameter("designation_id");
            if (source_search_id == null) {
                source_search_id = "";
            }
            if (status_search_id == null) {
                status_search_id = "";
            }
            if (designation_search_id == null) {
                designation_search_id = "";
            }
        } else if (task.equals("Excel")) {

            Workbook wb = new HSSFWorkbook();
            String file_name = "C:\\ssadvt_repository\\SalesCrm\\Report\\enquiry_report.xls";
            String directoryPath = "C:/ssadvt_repository/SalesCrm/Report/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try (OutputStream fileOut = new FileOutputStream(file_name)) {
                wb.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any potential exceptions here
            }
            model.writeDataToExcel(file_name, role, key_person_id, source_search_id, status_search_id, designation_search_id, ctx, role);

            File downloadFile = new File(file_name);
            FileInputStream inStream = new FileInputStream(downloadFile);

            String relativePath = getServletContext().getRealPath("");
            ServletContext context = getServletContext();

            String mimeType = context.getMimeType(file_name);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();

        } else if (task.equals("PDF")) {

            try {
                source_search_id = request.getParameter("source_id");
                status_search_id = request.getParameter("status_id");
                designation_search_id = request.getParameter("designation_id");
                if (source_search_id == null) {
                    source_search_id = "";
                }
                if (status_search_id == null) {
                    status_search_id = "";
                }
                if (designation_search_id == null) {
                    designation_search_id = "";
                }
                String result = model.sendData(role, key_person_id, source_search_id, status_search_id, designation_search_id, ctx, role);
                org.codehaus.jettison.json.JSONObject object = new org.codehaus.jettison.json.JSONObject(result);
                String byte_arr = object.get("byte_arr").toString();
                String file_name = object.get("file_name").toString();
                String new_path = "C:\\ssadvt_repository\\SalesCrm\\Report\\" + file_name;
                // byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(byte_arr);
                byte[] fileAsBytes = Base64.getDecoder().decode(byte_arr);
                File file = new File(new_path);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(fileAsBytes);
                outputStream.close();
                FileInputStream inStream = new FileInputStream(file);
                // if you want to use a relative path to context root:
                String relativePath = getServletContext().getRealPath("");
                System.out.println("relativePath = " + relativePath);
                // obtains ServletContext
                ServletContext context = getServletContext();
                // gets MIME type of the file
                String mimeType = context.getMimeType(new_path);
                if (mimeType == null) {
                    // set to binary type if MIME mapping not found
                    mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);
                // modifies response
                response.setContentType(mimeType);
                response.setContentLength((int) file.length());
                // forces download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                response.setHeader(headerKey, headerValue);
                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                inStream.close();
                outStream.close();
            } catch (JSONException ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (task.equals("GetEnquiryDataById")) {
            String id = request.getParameter("id");
            JSONObject jsnobject = new JSONObject();

            String alldata = "";
            String jsonData = "";

            try {
                ArrayList<EnquiryBean> list = model.GetEnquiryDataById(id);
                Gson gson = new GsonBuilder().create();

                jsonData = gson.toJson(list);

            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.getWriter().write(jsonData);
            return;
        }

        if (role.equals(SALES_EXECUTIVE)) {

            ArrayList<EnquiryBean> list = model.getAllAssignedEnquiries(key_person_id, source_search_id, status_search_id, designation_search_id, ctx);
            request.setAttribute("list", list);
        } else {
            ArrayList<EnquiryBean> list = model.getAllEnquiries(source_search_id, status_search_id, designation_search_id, ctx, role);
            request.setAttribute("list", list);
        }
        ArrayList<EnquiryBean> verticallist = model.getMarketingVertical();

        request.setAttribute("verticallist", verticallist);
        ArrayList<EnquiryBean> sourcelist = model.getAllSources();

        request.setAttribute("sourcelist", sourcelist);
        ArrayList<EnquiryBean> statuslist = model.getAllStatus();

        request.setAttribute("statuslist", statuslist);
        ArrayList<EnquiryBean> subjectlist = model.getAllSubject();
        request.setAttribute("subjectlist", subjectlist);

        JSONObject reminderObj = new JSONObject();
        try {
            reminderObj.put("from_id", key_person_id);
            reminderObj.put("to_id", "");
            reminderObj.put("type", "Reminder");
            reminderObj.put("ReminderMail", "");
            ArrayList<EnquiryBean> allRemindersDetails;
            try {
                allRemindersDetails = model.getAllRemindersDetails(reminderObj);
                request.setAttribute("allRemindersDetails", allRemindersDetails);
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONArray documentlistIds = model.getAllDocumentIds();

        String task1 = request.getParameter("task1");
        if (task1 == null) {
            task1 = "";
        }

        if (task1.equals("getDocumentDetails")) {
            try {
                JSONObject obj1 = new JSONObject();
                JSONArray arrayObj = new JSONArray();
                String DocumentDetails = model.callAPI(documentlistIds); //      call API
                arrayObj = new JSONArray(DocumentDetails);
                JSONArray arrayObj2 = new JSONArray();
                for (int i = 0; i < arrayObj.length(); i++) {
                    JSONObject toObj = arrayObj.getJSONObject(i);
                    String documents_id = toObj.getString("documents_id");
//                    System.out.println(documents_id);
                    String getFileNameFromField = model.getFileNameFromField(documents_id);
                    toObj.put("getFileNameFromField", getFileNameFromField);
                    arrayObj2.put(toObj);
                }
                obj1.put("list", arrayObj2);
                PrintWriter out = response.getWriter();
                out.print(obj1);
                return;
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (task1.equals("setReminder")) {
            String enquiry_table_id = "";
            try {
                String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JSONObject jsonObject = new JSONObject(requestBody);
                String date = jsonObject.getString("date");
                String time = jsonObject.getString("time");
                String type = "Reminder";
//                enquiry_table_id = "2";
                JSONArray checkedIdsArray = jsonObject.getJSONArray("checkedIdsList");
                if (checkedIdsArray != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < checkedIdsArray.length(); i++) {
                        String enquiryId = checkedIdsArray.getString(i);
                        if (i == 0) {
                            stringBuilder.append(enquiryId);
                        } else {
                            stringBuilder.append(", ").append(enquiryId);
                        }
                    }
                    enquiry_table_id = stringBuilder.toString();
                }
                JSONObject objsendReminder = new JSONObject();
                objsendReminder.put("from_id", key_person_id);
                objsendReminder.put("to_id", enquiry_table_id);
                objsendReminder.put("project_id", "6");
                objsendReminder.put("project_name", "saleCRM");
                objsendReminder.put("message", "For Reminder text");
                objsendReminder.put("date", date);
                objsendReminder.put("time", time);
                objsendReminder.put("type", type);
//                model.saveReminderData(date, time, enquiry_table_id, type);
                String dataa = model.callApiForSaveReminderData(objsendReminder);
                JSONObject obj1 = new JSONObject();
                if (dataa.equals("from_id or to_id not found")) {
                    obj1.put("msg", "Reminder not Set!");
                } else {
                    obj1.put("msg", "Reminder Set Successfully!");
                }
                PrintWriter out = response.getWriter();
                out.print(obj1);
                return;
            } catch (Exception e) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else if (task1.equals("setImportant")) {
            try {
                JSONObject returnObj = new JSONObject();
                String setImportantId = request.getParameter("setImportantId");
                String message1 = model.setImportant(setImportantId);
                PrintWriter out = response.getWriter();
                returnObj.put("data", message1);
                out.print(returnObj);
                return;
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (task1.equals("getImportant")) {
            ArrayList<EnquiryBean> list = model.getImportantEnquiries(source_search_id, status_search_id, ctx);
            request.setAttribute("list", list);
            System.out.println("getImportant");
        }
        String organisation = "APOGEE GNSS";
        String organisation_id = "7";
        DashboardModel dashmodel = new DashboardModel();
        ArrayList<DashboardBean> lists = dashmodel.getAllDegignatedPersonsListForSearch(role, organisation, key_person_id);
        request.setAttribute("lists", lists);

        request.setAttribute("message", message);
        request.setAttribute("msgbgcolor", msgbgcolor);

        request.setAttribute("role", role);
        request.setAttribute("SALES_EXECUTIVE", SALES_EXECUTIVE);
        request.setAttribute("SALES_MANAGER", SALES_MANAGER);
        request.setAttribute("SALES_SUPERVISOR", SALES_SUPERVISOR);
        model.closeConnection();
        request.getRequestDispatcher("enquirylist").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
