/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee;

import com.apogee.bean.FileTypeBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author lENOVO
 */
public class FileUploadModel {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");

    private Connection connection;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.registartioncummodel.LoginModel.setConnection(): " + e);
        }
    }

    public String callAPI(JSONObject obj) {
        String result = "";
        try {
            String uploadDocs = resourceBundle.getString("UPLOAD_DOCUMENTS_MODULE_UPLOADDOCS_API_URL");
//            URL url = new URL("http://localhost:8080/UploadDocuments/webAPI/uploadDocs");
            URL url = new URL(uploadDocs);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(obj);
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

    public String saveDocTypeDocumentModuleMapping(int doc_type_id, String document_id, String fileNameByField) {
        int rowsAffected = 0;
        String doc_type_documents_id = "";

        try {
            String query = "insert into doc_type_documents_mapping(doc_type_id, documents_id, file_name, active) values(?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, doc_type_id);
            pstmt.setString(2, document_id);
            pstmt.setString(3, fileNameByField);
            pstmt.setString(4, "Y");
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    doc_type_documents_id = Integer.toString(rs.getInt(1));
                }

            }
        } catch (Exception e) {
            System.out.println("Error while insert file_type..." + e);
        }
        return doc_type_documents_id;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public List<FileTypeBean> getFileType() {
        List<FileTypeBean> fileTypeList = new ArrayList<>();
        String query = "select * from doc_type";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();

            while (stmt.next()) {
                FileTypeBean fileType = new FileTypeBean();
                fileType.setDoc_type_id(stmt.getInt("doc_type_id"));
                fileType.setFile_type(stmt.getString("file_type"));
                fileTypeList.add(fileType);
            }

        } catch (Exception e) {
            System.out.println("Error while insert file_type..." + e);
        }

        return fileTypeList;
    }

    public List<FileTypeBean> getAllDocuments() {
        List<FileTypeBean> documentListData = new ArrayList<>();

        String query = " select dtd.documents_id, dtd.file_name, dtd.created_at, dt.file_type "
                + " FROM doc_type AS dt\n"
                + " JOIN doc_type_documents_mapping AS dtd ON dtd.doc_type_id = dt.doc_type_id "
                + " WHERE dtd.active = 'Y' order by dtd.documents_id desc ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();

            while (stmt.next()) {
                FileTypeBean fileType = new FileTypeBean();
                fileType.setDocuments_id(stmt.getString("documents_id"));
                fileType.setFile_name(stmt.getString("file_name"));
                fileType.setCreated_at(stmt.getString("created_at"));
                fileType.setFile_type(stmt.getString("file_type"));
                documentListData.add(fileType);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return documentListData;

    }

    public String callAPIForDocDetails(JSONArray documentlistIds) {
        String result = "";
        try {
            String findDocsDetails = resourceBundle.getString("UPLOAD_DOCUMENTS_MODULE_FINDDOCSDETAILS_API_URL");
//            URL url = new URL("http://localhost:8080/UploadDocuments/webAPI/findDocsDetails");
            URL url = new URL(findDocsDetails);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(documentlistIds);
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

    public int deleteDocuments(String documents_id) {
        int rowsAffected = 0;
        String query = "UPDATE doc_type_documents_mapping SET active = 'N'  WHERE documents_id ='" + documents_id + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return rowsAffected;
    }

    // File Edit ................
    public JSONObject getSingleDocument(String documents_id) {
        JSONObject obj = new JSONObject();
        String query = "select dtd.documents_id, dtd.file_name, dt.file_type\n"
                + "FROM doc_type AS dt\n"
                + "JOIN doc_type_documents_mapping AS dtd ON dtd.doc_type_id = dt.doc_type_id\n"
                + "WHERE dtd.active = 'Y' and documents_id ='" + documents_id + "' \n";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();

            while (stmt.next()) {
                obj.put("documents_id", stmt.getInt("documents_id"));
                obj.put("file_name_from_field", stmt.getString("file_name"));
                obj.put("file_type", stmt.getString("file_type"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return obj;
    }

}
