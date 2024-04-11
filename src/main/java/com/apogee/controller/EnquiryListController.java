/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.model.EnquiryModel;
import com.apogee.model.EnquiryDetailsModel;
import com.apogee.bean.EnquiryBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "EnquiryListController", urlPatterns = {"/EnquiryListController"})
public class EnquiryListController extends HttpServlet {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        EnquiryDetailsModel emodel = new EnquiryDetailsModel();
        ServletContext ctx = getServletContext();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }
        try {
            emodel.setConnection(DBConnection.getConnection(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String role = (String) session.getAttribute("role");
        String task = request.getParameter("task");
        String enquiry_table_id = request.getParameter("id");

        if (enquiry_table_id == null || enquiry_table_id == "") {
            enquiry_table_id = "0";
        }

        if (task == null || task == "") {
            task = "";
        } else if (task.equals("Assign To")) {
            String assign_by = (String) session.getAttribute("key_person_id");
            String assign_to = request.getParameter("assign_to");
            String[] assignto = assign_to.split("-");
            String assign_to_id = assignto[0];
            String assign_to_name = assignto[1];

            // Get current date and time
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String updated_date_time = formattedDateTime;
            String created_by = (String) session.getAttribute("key_person_id");
            enquiry_table_id = request.getParameter("enquiry_table_id");
            String active = "Y";
            String enquiry_status_id = "11";
            String added = emodel.addAsignedToExecutive(assign_by, assign_to_id, updated_date_time, created_by, enquiry_table_id, active, enquiry_status_id);
            if (added.equals("Added")) {
                request.setAttribute("message", "Data Added Successfully.");
//                request.getRequestDispatcher("enquirylist").forward(request, response);
                enquiry_table_id = enquiry_table_id;
            }
        } else if (task.equals("UPDATE")) {
            String key_person_id1 = (String) session.getAttribute("key_person_id");
            enquiry_table_id = request.getParameter("enquiry_table_id");
            String status_id = request.getParameter("status_id");
            String remark = request.getParameter("remark");
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String updated_date_time = formattedDateTime;
            String created_by = (String) session.getAttribute("key_person_id");
            String added = emodel.UpdateDataInAssignTable(enquiry_table_id, status_id, remark, updated_date_time, created_by, key_person_id1);
            if (added.equals("Added")) {
                request.setAttribute("message", "Data Updated Successfully.");
//                request.getRequestDispatcher("enquirylist").forward(request, response);
                // response.sendRedirect("/SalesCrm/EnquiryListController?id="+enquiry_table_id);
                enquiry_table_id = enquiry_table_id;
            }
        } else if (task.equals("Update Remark")) {
            enquiry_table_id = request.getParameter("enquiry_table_id");
            String enquiry_status = request.getParameter("enquiry_status");
            String enquiryassignid = request.getParameter("enquiryassignid");
            String remark = request.getParameter("remarkpopup");
            String enquiryrevision = request.getParameter("enquiryrevision");
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String updated_date_time = formattedDateTime;
            String created_by = (String) session.getAttribute("key_person_id");
            String added = emodel.UpdateRemarkInAssignTable(enquiry_table_id, enquiryassignid, enquiryrevision, remark, updated_date_time, created_by, enquiry_status);
            if (added.equals("Added")) {
                request.setAttribute("message", "Data Updated Successfully.");
                enquiry_table_id = enquiry_table_id;
            }
        } else if (task.equals("Getremarkbyid")) {
            String revision_no = request.getParameter("revision_no");
            String id = request.getParameter("id");
            String status = request.getParameter("status");

            org.codehaus.jettison.json.JSONObject jsnobject = new org.codehaus.jettison.json.JSONObject();
            String baseiname = "";
            String alldata = "";
            try {
                baseiname = emodel.Getremarkbyidandrevision(id, revision_no, status);
                response.getWriter().write(baseiname);

            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return;
        }

        // Retrieve the value from the session
        String username = (String) session.getAttribute("name");
        String key_person_id = (String) session.getAttribute("key_person_id");

        if (task.equals("sendChatMessage")) {
            JSONObject chatobj = new JSONObject();
            chatobj.put("message", request.getParameter("message"));
            chatobj.put("from_id", key_person_id);
            chatobj.put("to_id", "2");
            chatobj.put("project_id", "6");
            chatobj.put("project_name", "SalesCrm");
            String chatMessageId = emodel.callChatMessageApi(chatobj);
        }

        String assignedToId = emodel.getAssignedToById(enquiry_table_id);

        ArrayList<EnquiryBean> lists = emodel.getHistoryRecord(enquiry_table_id, ctx);

        request.setAttribute("lists", lists);
        ArrayList<EnquiryBean> statuslist = emodel.getAllStatus();
        ArrayList<EnquiryBean> list = emodel.getEnquiryDetails(enquiry_table_id);
        request.setAttribute("list", list);
        request.setAttribute("assignedToId", assignedToId);
//        request.setAttribute("message", emodel.getMessage());
//        request.setAttribute("msgBgColor", emodel.getMessageBGColor());
        request.setAttribute("statuslist", statuslist);
        request.setAttribute("enquiry_table_id", enquiry_table_id);
        request.setAttribute("role", role);
        request.setAttribute("SALES_SUPERVISOR", SALES_SUPERVISOR);
        request.setAttribute("SALES_MANAGER", SALES_MANAGER);
        request.setAttribute("SALES_EXECUTIVE", SALES_EXECUTIVE);
        request.setAttribute("key_person_id", key_person_id);
        emodel.closeConnection();
        request.getRequestDispatcher("enquirydetails").forward(request, response);
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

    private int getAssignedToById(String enquiry_table_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
