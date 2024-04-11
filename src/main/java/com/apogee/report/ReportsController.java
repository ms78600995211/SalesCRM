/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.report;

import com.apogee.DBConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "ReportsController", urlPatterns = {"/ReportsController"})

public class ReportsController extends HttpServlet {

    private File tmpDir;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 4 * 1024 * 1024;

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
        Map<String, String> map = new HashMap<String, String>();
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }
        String role = (String) session.getAttribute("role");
        String key_person_id = (String) session.getAttribute("key_person_id");
        ServletContext ctx = getServletContext();
        ReportsModel model = new ReportsModel();
        try {
            model.setConnection(DBConnection.getConnection(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String source_search_id = "";
        String status_search_id = "";

        List items = null;
        Iterator itr = null;
        Iterator itr2 = null;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        try {
            items = uploadHandler.parseRequest(request);
            itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    map.put(item.getFieldName(), item.getString("UTF-8"));

                } else {
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {
                        String image_name = item.getName();
                        image_name = image_name.substring(0, image_name.length());
                        map.put(item.getFieldName(), item.getName());
                    }
                }
            }
            itr = null;
            itr = items.iterator();

            itr2 = items.iterator();

        } catch (Exception ex) {
            System.out.println("Error encountered while uploading file" + ex);
        }

        try {
            String task = map.get("task");
            if (task == null) {
                task = "";
            }
            String task1 = request.getParameter("task1");
            if (task1 == null) {
                task1 = "";
            }
            if (!map.isEmpty()) {
                String report_name = map.get("report_name");
                if (report_name == null) {
                    report_name = "";
                }
                String report_type = map.get("report_type");
                if (report_type == null) {
                    report_type = "";
                }
                if (report_type.equals("Select Report Type")) {
                    report_type = "";
                }
                if (task.equals("Send")) {
                    String msg = model.setDataforAPI(map, itr, itr2, report_name, report_type);
                }
            }

            if (task.equals("View Text File")) {
                String report_name = map.get("report_name");
                if (report_name == null) {
                    report_name = "";
                }
                JSONObject obj = new JSONObject();
                String new_path = "C:\\ssadvt_repository\\SalesCrm\\Text files\\" + report_name + ".txt";
                File file = new File(new_path);
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
                request.setAttribute("message", "downloaded");
                request.setAttribute("msgBgColor", "green");
                response.sendRedirect(request.getContextPath() + "/reports");
            }

//            if (task1.equals("PDF")) {
//
//              
//                String result = model.sendData(role, key_person_id, source_search_id, status_search_id);
//                org.codehaus.jettison.json.JSONObject object = new org.codehaus.jettison.json.JSONObject(result);
//                String byte_arr = object.get("byte_arr").toString();
//                String file_name = object.get("file_name").toString();
//                String new_path = "C:\\ssadvt_repository\\SalesCrm\\Reports\\" + file_name;
//                byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(byte_arr);
//                File file = new File(new_path);
//                FileOutputStream outputStream = new FileOutputStream(file);
//                outputStream.write(fileAsBytes);
//                outputStream.close();
//                FileInputStream inStream = new FileInputStream(file);
//                // if you want to use a relative path to context root:
//                String relativePath = getServletContext().getRealPath("");
//                System.out.println("relativePath = " + relativePath);
//                // obtains ServletContext
//                ServletContext context = getServletContext();
//                // gets MIME type of the file
//                String mimeType = context.getMimeType(new_path);
//                if (mimeType == null) {
//                    // set to binary type if MIME mapping not found
//                    mimeType = "application/octet-stream";
//                }
//                System.out.println("MIME type: " + mimeType);
//                // modifies response
//                response.setContentType(mimeType);
//                response.setContentLength((int) file.length());
//                // forces download
//                String headerKey = "Content-Disposition";
//                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
//                response.setHeader(headerKey, headerValue);
//                OutputStream outStream = response.getOutputStream();
//                byte[] buffer = new byte[4096];
//                int bytesRead = -1;
//                while ((bytesRead = inStream.read(buffer)) != -1) {
//                    outStream.write(buffer, 0, bytesRead);
//                }
//                inStream.close();
//                outStream.close();
//
//            }
            request.getRequestDispatcher("/reports").forward(request, response);
        } catch (Exception e) {
            System.out.println("com.apogee.report.ReportsController.processRequest()" + e);
        }

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
