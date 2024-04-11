/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import com.apogee.DBConnection;
import com.apogee.FileUploadModel;
import com.apogee.bean.FileTypeBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
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
import javax.servlet.http.Part;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@MultipartConfig
@WebServlet(name = "FileUploadController", urlPatterns = {"/FileUploadController"})
public class FileUploadController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        FileUploadModel model = new FileUploadModel();
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }

        try {
            model.setConnection(DBConnection.getConnection(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String submit = request.getParameter("submitFormBtn");
//        String document_id = "";
        if (submit == null) {
            submit = "";
        }
        if (submit.equals("Submit")) {
            //File
            JSONObject obj = new JSONObject();
            try {
                String fileNameByField = request.getParameter("fileNameByField");
                int fileTypeId = 0;
                String fileType_Id = request.getParameter("fileType");
                if (!fileType_Id.equals("")) {
                    fileTypeId = Integer.parseInt(fileType_Id);
                }
                String fileDescription = request.getParameter("fileDescription");
                String document_id = "";
                JSONArray fileArray = new JSONArray();
                Part filePart = request.getPart("fileName");
                String fileName = filePart.getSubmittedFileName();
                // Get the file content
                InputStream fileContent = filePart.getInputStream();
                byte[] fileBytes = new byte[fileContent.available()];
                fileContent.read(fileBytes);
                String FileBytes = Base64.getEncoder().encodeToString(fileBytes);
//                System.out.println("FileBytes :" + FileBytes);

                JSONObject fileObject = new JSONObject();

                fileObject.put("FileName", fileName);
                fileObject.put("FileBytes", FileBytes);

                fileArray.put(fileObject);
//                System.out.println("jsonArray :" + fileArray);

                String path = "";
                if (fileTypeId == 1) {
                    path = "CommunicationData/SalesCRM/Brochure";
                } else if (fileTypeId == 2) {
                    path = "CommunicationData/SalesCRM/Catalog";
                } else if (fileTypeId == 3) {
                    path = "CommunicationData/SalesCRM/Image";
                }
                //////for document ID   
                obj.put("project_id", 6);
                obj.put("project_name", "saleCRM");
                obj.put("path", path);
                obj.put("file_data", fileArray);
                obj.put("file_description", fileDescription);

                if (fileArray != null) {
                    String resultDocumentId = model.callAPI(obj);
                    System.out.println("resultDocumentId :" + resultDocumentId);
                    JSONArray DocJArr = new JSONArray(resultDocumentId);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < DocJArr.length(); i++) {
                        JSONObject DocObj = DocJArr.getJSONObject(i);
                        if (i == 0) {
                            stringBuilder.append(DocObj.getString("documents_id"));
                        } else {
                            stringBuilder.append(",").append(DocObj.getString("documents_id"));
                        }
                    }
                    document_id = stringBuilder.toString();
                    System.out.println(document_id);
                }

                String doc_type_id = model.saveDocTypeDocumentModuleMapping(fileTypeId, document_id, fileNameByField);
                if (!doc_type_id.equals("")) {
                    request.setAttribute("message", "Data Added Successfully.");
                } else {
                    request.setAttribute("message", "Some Error Try Again.");
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        String task = request.getParameter("task");
//        String document_id = "";
        if (task == null) {
            task = "";
        }
        if (task.equals("viewDocuments")) {
            String documents_id = request.getParameter("documents_id");
            if (documents_id == null) {
                documents_id = "";
            }
            if (!documents_id.equals("")) {
                JSONArray rowData = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("documents_id", documents_id);
                obj.put("project_id", "6");
                rowData.put(obj);
                JSONArray arrayObj = new JSONArray();
                String DocumentDetails = model.callAPIForDocDetails(rowData);

                String filePath = "";
                arrayObj = new JSONArray(DocumentDetails);
                for (int i = 0; i < arrayObj.length(); i++) {
                    JSONObject DocObj = arrayObj.getJSONObject(i);
                    String path = DocObj.getString("path");
                    String path_Arr[] = path.split(",");
                    String new_path = path_Arr[0];
                    String filename = path_Arr[1];
                    filePath = new_path + "/" + filename;
                }
                // Set the content type based on the file extension
                if (filePath.endsWith(".pdf")) {
                    response.setContentType("application/pdf");
                } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
                    response.setContentType("image/jpeg");
                } else if (filePath.endsWith(".png")) {
                    response.setContentType("image/png");
                } else {
                    // Handle unsupported file types or invalid paths
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                // Read the file and write it to the response
                try (InputStream fileStream = new FileInputStream(filePath);
                        OutputStream outStream = response.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                } catch (FileNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            }
        }

        if (task.equals("deleteDocuments")) {
            String documents_id = request.getParameter("documents_id");
            if (documents_id == null) {
                documents_id = "";
            }
            if (!documents_id.equals("")) {
                int rowsAffected = model.deleteDocuments(documents_id);
            }

        }

        //File Edit ..............
        if (task.equals("getSingleDocumentDetails")) {
            try {
                String documents_id = request.getParameter("documents_id");
                if (documents_id == null) {
                    documents_id = "";
                }
                JSONObject responseObj = new JSONObject();
                JSONObject returnObj = new JSONObject();
                JSONArray arrayObj2 = new JSONArray();
                if (!documents_id.equals("")) {
                    responseObj = model.getSingleDocument(documents_id);

                    JSONArray rowData = new JSONArray();
                    JSONObject obj = new JSONObject();
                    obj.put("documents_id", documents_id);
                    obj.put("project_id", "6");
                    rowData.put(obj);
                    JSONArray arrayObj = new JSONArray();
                    String DocumentDetails = model.callAPIForDocDetails(rowData);

                    String filePath = "";
                    arrayObj = new JSONArray(DocumentDetails);

                    for (int i = 0; i < arrayObj.length(); i++) {
                        JSONObject DocObj = arrayObj.getJSONObject(i);
                        String path = DocObj.getString("path");
                        String path_Arr[] = path.split(",");
                        String new_path = path_Arr[0];
                        String filename = path_Arr[1];
                        filePath = new_path + "/" + filename;
                        responseObj.put("filePath", filePath);
                        String remark = DocObj.getString("remark");
                        responseObj.put("remark", remark);
                        arrayObj2.put(responseObj);
                    }
                }

                PrintWriter out = response.getWriter();
                returnObj.put("data", arrayObj2);
                out.print(returnObj);
                return;
            } catch (Exception ex) {
                Logger.getLogger(EnquiryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<FileTypeBean> fileTypeList = model.getFileType();
        request.setAttribute("fileTypeList", fileTypeList);

        List<FileTypeBean> documentListData = model.getAllDocuments();
        request.setAttribute("documentListData", documentListData);

        model.closeConnection();
        request.getRequestDispatcher("fileList").forward(request, response);
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
