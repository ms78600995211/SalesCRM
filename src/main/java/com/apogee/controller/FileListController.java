/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.bean.EnquiryBean;
import com.apogee.model.EnquiryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "FileListController", urlPatterns = {"/FileListController"})
public class FileListController extends HttpServlet {

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

        String task = request.getParameter("task");

        if (task == null || task == "") {
            task = "";
        }
        String source_search_id = "";
        String status_search_id = "";

        if (task.equals("assign_in_bulk")) {

            String tottal_ids = request.getParameter("tottal_ids");
            String[] enquiry_table_id = tottal_ids.split(",");
//            int length = enquiry_table_ids.length;
//            String enquiry_table_id = enquiry_table_ids[0];

            String assign_by = (String) session.getAttribute("key_person_id");
            String assign_to = request.getParameter("assign_to");
            String[] assignto = assign_to.split("-");
            String assign_to_id = assignto[0];
            String assign_to_name = assignto[1];
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String updated_date_time = formattedDateTime;
            String created_by = (String) session.getAttribute("key_person_id");

            String active = "Y";
            String enquiry_status_id = "11";
            String added = model.addAsignedToExecutiveInBulk(assign_by, assign_to_id, updated_date_time, created_by, enquiry_table_id, active, enquiry_status_id);
            if (added.equals("Added")) {
                request.setAttribute("message", "Data Added Successfully.");

            } else {
                request.setAttribute("message", "Some Error Try Again.");
            }

        } else if (task.equals("GetAllExecutive")) {
            String organisation_name = "APOGEE GNSS";
            String designation_name = SALES_EXECUTIVE;

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
                } catch (JSONException ex) {
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

        } else if (task.equals("Search Result")) {
            source_search_id = request.getParameter("source_id");
            status_search_id = request.getParameter("status_id");
            if (source_search_id == null) {
                source_search_id = "";
            }
            if (status_search_id == null) {
                status_search_id = "";
            }
        } else if (task.equals("sales_enquiry_list")) {
            String pdf = request.getParameter("pdf");
            if (pdf == null) {
                pdf = "";
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

//        if (role.equals(SALES_EXECUTIVE)) {
//
//            ArrayList<EnquiryBean> list = model.getAllAssignedEnquiries(key_person_id, source_search_id, status_search_id, ctx);
//            request.setAttribute("list", list);
//        } else {
//            ArrayList<EnquiryBean> list = model.getAllEnquiries(source_search_id, status_search_id, ctx);
//            request.setAttribute("list", list);
//        }
//        ArrayList<EnquiryBean> verticallist = model.getMarketingVertical();
//
//        request.setAttribute("verticallist", verticallist);
//
//        ArrayList<EnquiryBean> sourcelist = model.getAllSources();
//
//        request.setAttribute("sourcelist", sourcelist);
//        ArrayList<EnquiryBean> statuslist = model.getAllStatus();
//
//        request.setAttribute("statuslist", statuslist);
        request.setAttribute("role", role);

        model.closeConnection();

        request.getRequestDispatcher("filelist").forward(request, response);
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
