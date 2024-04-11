/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apogee.controller;

import com.apogee.model.LoginModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.apogee.DBConnection;
import com.apogee.DBConnection;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vikranth
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        String task = request.getParameter("task");
        if (task == null || task == "") {
            task = "";
        }
        try {
            if (task.equals("Log In")) {
                //response.sendRedirect("http://localhost:8080/login_module/LoginController?project_name=SalesCrm");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String project_name = "SalesCrm";
                LoginModel model = new LoginModel();
                model.setConnection(DBConnection.getConnection(ctx));
                String baseiname = model.conformLoginDataFromLoginModule(username, password, project_name);
                if (baseiname != "") {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(baseiname);
                        JsonNode dataNode = jsonNode.get("Data");
                        if (dataNode.isArray() && dataNode.size() > 0) {
                            JsonNode firstElement = dataNode.get(0);
                            String keyPersonId = firstElement.get("Key_person_id").asText();
                            String email = firstElement.get("Username").asText();
                            String name = firstElement.get("Name").asText();
                            String phone = firstElement.get("Phone").asText();
                            String datafromorganisation = model.getKeypersonDataFromOrganisation(keyPersonId);
                            ObjectMapper objectMappers = new ObjectMapper();
                            JsonNode jsonNodes = objectMappers.readTree(datafromorganisation);
                            JsonNode dataNodes = jsonNodes.get("city_location");
                            if (dataNodes.isArray() && dataNodes.size() > 0) {
                                JsonNode SecondElement = dataNodes.get(0);
                                String designation = SecondElement.get("designation").asText();
                                request.setAttribute("keyPersonId", keyPersonId);
                                request.setAttribute("designation", designation);
                                request.setAttribute("email", email);
                                request.setAttribute("name", name);
                                // response.sendRedirect("/SalesCrm/DashboardController");
                                HttpSession session = request.getSession();
                                session.setAttribute("name", name);
                                session.setAttribute("email", email);
                                session.setAttribute("key_person_id", keyPersonId);
                                request.setAttribute("msg", "Success");
                                session.setAttribute("role", designation);
                                //request.getRequestDispatcher("DashboardController").forward(request, response);
                                response.sendRedirect("/SalesCrm/DashboardController");
                            } else {
                                request.setAttribute("message", "Please check username and password again");
                                request.getRequestDispatcher("login_page").forward(request, response);
                            }
                        } else {
                            request.setAttribute("message", "Please check username and password again");
                            request.getRequestDispatcher("login_page").forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("message", "Please check username and password again");
                    request.getRequestDispatcher("login_page").forward(request, response);
                }
            } else {

                request.getRequestDispatcher("login_page").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("com.jpss.registrationcummodule.LoginController.doGet(): " + ex);
        }
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
