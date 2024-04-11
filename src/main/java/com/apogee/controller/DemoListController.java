/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.bean.EnquiryBean;
import com.apogee.model.DemoListModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

/**
 *
 * @author admin
 */
@WebServlet(name = "DemoListController", urlPatterns = {"/DemoListController"})
public class DemoListController extends HttpServlet {

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
        DemoListModel model = new DemoListModel();
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

        if (role.equals(SALES_EXECUTIVE)) {

//            ArrayList<EnquiryBean> list = model.getAllAssignedEnquiries(key_person_id, source_search_id, status_search_id);
//            request.setAttribute("list", list);
        } else {
            ArrayList<EnquiryBean> list = model.getAllDemoEnquiries(source_search_id, status_search_id, ctx, role);
            request.setAttribute("list", list);
        }
        ArrayList<EnquiryBean> verticallist = model.getMarketingVertical();

        request.setAttribute("verticallist", verticallist);

        ArrayList<EnquiryBean> sourcelist = model.getAllSources();

        request.setAttribute("sourcelist", sourcelist);
        ArrayList<EnquiryBean> statuslist = model.getAllStatus();

        request.setAttribute("statuslist", statuslist);
        request.setAttribute("role", role);

        model.closeConnection();

        request.getRequestDispatcher("demolist").forward(request, response);
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
