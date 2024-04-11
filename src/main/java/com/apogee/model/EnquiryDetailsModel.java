/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import com.apogee.model.DashboardModel;
import com.apogee.bean.EnquiryBean;
import static com.apogee.model.EnquiryModel.getPersonNameUsingId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 *
 * @author admin
 */
public class EnquiryDetailsModel {

    private static Connection connection;
    private final String COLOR_OK = "green";
    private final String COLOR_ERROR = "red";
    static private String message, messageBGColor = "#a2a220";

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    String SALES_EXECUTIVE = resourceBundle.getString("SALES_EXECUTIVE");
    String SALES_MANAGER = resourceBundle.getString("SALES_MANAGER");
    String SALES_SUPERVISOR = resourceBundle.getString("SALES_SUPERVISOR");

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.registartioncummodel.LoginModel.setConnection(): " + e);
        }
    }

    public ArrayList<EnquiryBean> getAllStatus() {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        String query = "select * from enquiry_status where active='Y';";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                bean.setEnquiry_status_id(rset.getInt("enquiry_status_id"));
                bean.setStatus(rset.getString("status"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getEnquiryDetails(String enquiry_table_id) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        String query = "select * from enquiry_table where enquiry_table_id='" + enquiry_table_id + "' and active='Y';";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_company_name(rset.getString("sender_company_name"));
                bean.setEnquiry_address(rset.getString("enquiry_address"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));
                bean.setEnquiry_message(rset.getString("enquiry_message"));
                bean.setEnquiry_no(rset.getString("enquiry_no"));
                bean.setSender_alternate_email(rset.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rset.getString("sender_alternate_mob"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getHistoryRecord(String enquiry_table_id, ServletContext ctx) {

        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select * from  enquiry_assigned_table  join enquiry_status on enquiry_status.enquiry_status_id=enquiry_assigned_table.enquiry_status_id "
                + "where enquiry_assigned_table.enquiry_table_id='" + enquiry_table_id + "'"
                + "and enquiry_assigned_table.updated_date_time!='' order by enquiry_assigned_table.revision_no asc  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
//                Iterator<EnquiryBean> iterator = list.iterator();
                EnquiryBean bean = new EnquiryBean();

                String status = rset.getString("status");
//                for (EnquiryBean obj : list) {
//                    if (obj.getStatus().equals(status)) {
//
//                    }
//                } 

//                while (iterator.hasNext()) {
//                    EnquiryBean obj = iterator.next();
//                    if (obj.getStatus().equals(status)) {
//                        iterator.remove(); // Removes the matching object from the list
//                    }
//                }
                String assigned_by = rset.getString("assigned_by");

                String assigned_to = rset.getString("assigned_to");
                String created_by = rset.getString("created_by");
                bean.setEnquiry_assigned_table_id(rset.getInt("enquiry_assigned_table_id"));
                bean.setUpdate_date_time(rset.getString("updated_date_time"));
                bean.setRevision_no(rset.getInt("revision_no"));
                bean.setRemark(rset.getString("remark"));
                bean.setStatus(rset.getString("status"));
                DashboardModel dashmodel = new DashboardModel();

                bean.setAssigned_to(getPersonNameUsingId(assigned_to, ctx));
                bean.setAssigned_by(getPersonNameUsingId(assigned_by, ctx));
                bean.setCreated_by(getPersonNameUsingId(created_by, ctx));
//                String baseiname = dashmodel.getKeyPersonDetailsById(assigned_to);
//                if (baseiname != "") {
//                    try {
//                        ObjectMapper objectMapper = new ObjectMapper();
//
//                        JsonNode jsonNode = objectMapper.readTree(baseiname);
//                        String keyPersonName = jsonNode.get("key_person_name").asText();
//
//                        bean.setAssigned_to(keyPersonName);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                String baseinames = dashmodel.getKeyPersonDetailsById(assigned_by);
//                if (baseinames != "") {
//                    try {
//                        ObjectMapper objectMappers = new ObjectMapper();
//
//                        JsonNode jsonNodes = objectMappers.readTree(baseinames);
//                        String keyPersonNames = jsonNodes.get("key_person_name").asText();
//
//                        bean.setAssigned_by(keyPersonNames);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

//                String baseinamess = dashmodel.getKeyPersonDetailsById(created_by);
//                if (baseinamess != "") {
//                    try {
//                        ObjectMapper objectMappers = new ObjectMapper();
//
//                        JsonNode jsonNodes = objectMappers.readTree(baseinamess);
//                        String keyPersonNamess = jsonNodes.get("key_person_name").asText();
//
//                        bean.setCreated_by(keyPersonNamess);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                list.add(bean);
            }
            Collections.reverse(list);
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public String Getremarkbyidandrevision(String id, String revision_no, String status) {
        String multiremark = "";
        String remark = "";
        int rowCount = 1;
        String getrevision = null;
        String query = "Select * from enquiry_assigned_table where enquiry_assigned_table_id='" + id + "' and revision_no='" + revision_no + "'";
//        String query = "Select * from enquiry_assigned_table as eat "
//                + "join enquiry_status as es on es.enquiry_status_id=eat.enquiry_status_id "
//                + " where eat.enquiry_assigned_table_id='" + id + "' and es.status='" + status + "' order by eat.revision_no desc ";
        try {

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                remark = rset.getString("remark");
//                multiremark += rowCount + ". " + remark + "\n";
//                rowCount++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return remark;
    }

    public String addAsignedToExecutive(String assign_by, String assign_to, String updated_date_time, String created_by,
            String enquiry_table_id, String active, String enquiry_status_id) {
        PreparedStatement pst;
        PreparedStatement psts;
        PreparedStatement updatepsts;
        String added = "";
        int rowsAffected = 0;
        int updaterowsAffected = 0;
        String getrevision = "select max(revision_no),enquiry_assigned_table_id ,description, remark from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "'";

        try {
            pst = connection.prepareStatement(getrevision);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                String enquiry_assigned_table_id = rset.getString("enquiry_assigned_table_id");
                int revision_no = rset.getInt("max(revision_no)");
                String description = rset.getString("description");
                String remark = rset.getString("remark");
                String updateAssignedQuery = "update  enquiry_assigned_table set active='N' where enquiry_table_id='" + enquiry_table_id + "' and enquiry_assigned_table_id='" + enquiry_assigned_table_id + "'";
                updatepsts = connection.prepareStatement(updateAssignedQuery);
                updaterowsAffected = updatepsts.executeUpdate();
                if (updaterowsAffected > 0) {
                    String insertAssignedQuery = "insert into enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,"
                            + "active,revision_no,created_by,description,remark,updated_date_time,assigned_by,assigned_to)values(?,?,?,?,?,?,?,?,?,?,?);";
                    psts = connection.prepareStatement(insertAssignedQuery);
                    psts.setString(1, enquiry_assigned_table_id);
                    psts.setString(2, enquiry_table_id);
                    psts.setString(3, enquiry_status_id);
                    psts.setString(4, active);
                    psts.setInt(5, revision_no + 1);
                    psts.setString(6, created_by);
                    psts.setString(7, description);
                    psts.setString(8, remark);
                    psts.setString(9, updated_date_time);
                    psts.setString(10, assign_by);
                    psts.setString(11, assign_to);

                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {

                        added = "Added";
                        message = "Record updated successfully.";
                        messageBGColor = COLOR_OK;
                    } else {
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                    }

                } else {
                    message = "Cannot update the record, some error.";
                    messageBGColor = COLOR_ERROR;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return added;
    }

    public String UpdateDataInAssignTable(String enquiry_table_id, String status_id, String remark, String updated_date_time, String created_by, String key_person_id1) {
        PreparedStatement pst;
        PreparedStatement pstmax;
        PreparedStatement psts;
        PreparedStatement updatepsts;
        String added = "";
        int rowsAffected = 0;
        int updaterowsAffected = 0;
        String getmaxrevision = "select max(revision_no) as maxrevision from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "' ;";
        try {
            pstmax = connection.prepareStatement(getmaxrevision);
            ResultSet rsetpstmax = pstmax.executeQuery();
            if (rsetpstmax.next()) {
                int maxrevision = rsetpstmax.getInt("maxrevision");
                String getrevision = "select revision_no,enquiry_assigned_table_id ,description ,assigned_by,"
                        + "assigned_to, created_by from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "'  order by revision_no desc";

                pst = connection.prepareStatement(getrevision);
                ResultSet rset = pst.executeQuery();
                if (rset.next()) {
                    String enquiry_assigned_table_id = rset.getString("enquiry_assigned_table_id");
                    int revision_no = rset.getInt("revision_no");
                    String description = rset.getString("description");
                    String assigned_by = rset.getString("assigned_by");
                    String assigned_to = rset.getString("assigned_to");
                    String updateAssignedQuery = "update  enquiry_assigned_table set active='N' where enquiry_assigned_table_id='" + enquiry_assigned_table_id + "' ";
                    updatepsts = connection.prepareStatement(updateAssignedQuery);
                    updaterowsAffected = updatepsts.executeUpdate();
                    if (updaterowsAffected > 0) {
                        String insertAssignedQuery = "insert into enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,"
                                + "active,revision_no,created_by,description,remark,updated_date_time,assigned_by,assigned_to)values(?,?,?,?,?,?,?,?,?,?,?);";
                        psts = connection.prepareStatement(insertAssignedQuery);
                        psts.setString(1, enquiry_assigned_table_id);
                        psts.setString(2, enquiry_table_id);
                        psts.setString(3, status_id);
                        psts.setString(4, "Y");
                        psts.setInt(5, maxrevision + 1);
                        psts.setString(6, created_by);
                        psts.setString(7, description);
                        psts.setString(8, remark);
                        psts.setString(9, updated_date_time);
                        psts.setString(10, assigned_by);
                        if (assigned_to.equals("115") || assigned_to.equals("99")) {
                            assigned_to = key_person_id1;
                            psts.setString(11, assigned_to);
                        } else {
                            psts.setString(11, assigned_to);
                        }

                        rowsAffected = psts.executeUpdate();
                        if (rowsAffected > 0) {
                            message = "Record updated successfully.";
                            messageBGColor = COLOR_OK;
                        } else {
                            message = "Cannot update the record, some error.";
                            messageBGColor = COLOR_ERROR;
                        }
                    } else {
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                    }

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return added;
    }

//    public String UpdateRemarkInAssignTable(String enquiry_table_id, String enquiryassignid, String enquiryrevision,
//            String remark, String updated_date_time, String created_by, String enquiry_status) {
//        PreparedStatement pst;
//        PreparedStatement pstmax;
//        PreparedStatement psts;
//        PreparedStatement updatepsts;
//        String added = "";
//        int rowsAffected = 0;
//        int updaterowsAffected = 0;
//        String getmaxrevision = "select max(revision_no) as maxrevision from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "' "
//                + " ;";
//        try {
//            pstmax = connection.prepareStatement(getmaxrevision);
//            ResultSet rsetpstmax = pstmax.executeQuery();
//            if (rsetpstmax.next()) {
//                int maxrevision = rsetpstmax.getInt("maxrevision");
//                String getrevision = "select eat.revision_no,eat.enquiry_assigned_table_id ,eat.description ,eat.assigned_by,"
//                        + " eat.assigned_to, eat.enquiry_status_id ,eat.created_by from enquiry_assigned_table as eat, enquiry_status as es "
//                        + " where eat.enquiry_table_id='" + enquiry_table_id + "' and es.status = '" + enquiry_status + "' "
//                        + "  and eat.enquiry_status_id = es.enquiry_status_id  order by eat.revision_no desc";
//
//                pst = connection.prepareStatement(getrevision);
//                ResultSet rset = pst.executeQuery();
//                if (rset.next()) {
//                    String enquiry_assigned_table_id = rset.getString("enquiry_assigned_table_id");
//                    int revision_no = rset.getInt("revision_no");
//                    String enquiry_status_id = rset.getString("enquiry_status_id");
//                    String description = rset.getString("description");
//                    String assigned_by = rset.getString("assigned_by");
//                    String assigned_to = rset.getString("assigned_to");
//
//                    String updateAssignedQuery = "update  enquiry_assigned_table set active='N' where enquiry_assigned_table_id='" + enquiry_assigned_table_id + "' ";
//                    updatepsts = connection.prepareStatement(updateAssignedQuery);
//                    updaterowsAffected = updatepsts.executeUpdate();
//                    if (updaterowsAffected > 0) {
//                        String insertAssignedQuery = "insert into enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,"
//                                + "active,revision_no,created_by,description,remark,updated_date_time,assigned_by,assigned_to)values(?,?,?,?,?,?,?,?,?,?,?);";
//                        psts = connection.prepareStatement(insertAssignedQuery);
//                        psts.setString(1, enquiry_assigned_table_id);
//                        psts.setString(2, enquiry_table_id);
//                        psts.setString(3, enquiry_status_id);
//                        psts.setString(4, "Y");
//                        psts.setInt(5, maxrevision + 1);
//                        psts.setString(6, created_by);
//                        psts.setString(7, description);
//                        psts.setString(8, remark);
//                        psts.setString(9, updated_date_time);
//                        psts.setString(10, assigned_by);
//                        psts.setString(11, assigned_to);
//
//                        rowsAffected = psts.executeUpdate();
//                        if (rowsAffected > 0) {
//                            message = "Record updated successfully.";
//                            messageBGColor = COLOR_OK;
//                        } else {
//                            message = "Cannot update the record, some error.";
//                            messageBGColor = COLOR_ERROR;
//                        }
//                    } else {
//                        message = "Cannot update the record, some error.";
//                        messageBGColor = COLOR_ERROR;
//                    }
//
//                }
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return added;
//    }
    public String UpdateRemarkInAssignTable(String enquiry_table_id, String enquiryassignid, String enquiryrevision,
            String remark, String updated_date_time, String created_by, String enquiry_status) {
        PreparedStatement pst;
        PreparedStatement pstmax;
        PreparedStatement psts;
        PreparedStatement updatepsts;
        String added = "";
        int rowsAffected = 0;
        int updaterowsAffected = 0;
//        String getmaxrevision = "select max(revision_no) as maxrevision from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "' "
//                + " ;";
        try {
//            pstmax = connection.prepareStatement(getmaxrevision);
//            ResultSet rsetpstmax = pstmax.executeQuery();
//            if (rsetpstmax.next()) {
//                int maxrevision = rsetpstmax.getInt("maxrevision");
            String getrevision = "select eat.revision_no,eat.enquiry_assigned_table_id ,eat.description ,eat.assigned_by,"
                    + " eat.assigned_to, eat.enquiry_status_id ,eat.created_by from enquiry_assigned_table as eat, enquiry_status as es "
                    + " where eat.enquiry_table_id='" + enquiry_table_id + "' and es.status = '" + enquiry_status + "' "
                    + "  and eat.enquiry_status_id = es.enquiry_status_id  order by eat.revision_no desc";

            pst = connection.prepareStatement(getrevision);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                String enquiry_assigned_table_id = rset.getString("enquiry_assigned_table_id");
//                    int revision_no = rset.getInt("revision_no");
//                    String enquiry_status_id = rset.getString("enquiry_status_id");
//                    String description = rset.getString("description");
//                    String assigned_by = rset.getString("assigned_by");
//                    String assigned_to = rset.getString("assigned_to");

                String updateAssignedQuery = "update  enquiry_assigned_table set remark='" + remark + "' where enquiry_assigned_table_id='" + enquiry_assigned_table_id + "' and revision_no='" + enquiryrevision + "' ";
                updatepsts = connection.prepareStatement(updateAssignedQuery);
                updaterowsAffected = updatepsts.executeUpdate();
                if (updaterowsAffected > 0) {
//                        String insertAssignedQuery = "insert into enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,"
//                                + "active,revision_no,created_by,description,remark,updated_date_time,assigned_by,assigned_to)values(?,?,?,?,?,?,?,?,?,?,?);";
//                        psts = connection.prepareStatement(insertAssignedQuery);
//                        psts.setString(1, enquiry_assigned_table_id);
//                        psts.setString(2, enquiry_table_id);
//                        psts.setString(3, enquiry_status_id);
//                        psts.setString(4, "Y");
//                        psts.setInt(5, maxrevision + 1);
//                        psts.setString(6, created_by);
//                        psts.setString(7, description);
//                        psts.setString(8, remark);
//                        psts.setString(9, updated_date_time);
//                        psts.setString(10, assigned_by);
//                        psts.setString(11, assigned_to);

//                        rowsAffected = psts.executeUpdate();
//                        if (rowsAffected > 0) {
                    message = "Record updated successfully.";
                    messageBGColor = COLOR_OK;
//                        } else {
//                            message = "Cannot update the record, some error.";
//                            messageBGColor = COLOR_ERROR;
//                        }
                } else {
                    message = "Cannot update the record, some error.";
                    messageBGColor = COLOR_ERROR;
                }

            }
//            }

        } catch (SQLException ex) {
            Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return added;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

    public String callChatMessageApi(JSONObject chatobj) {
        String result = "";
        try {

            URL url = new URL("http://localhost:8080/CModule/sendChatMessages");
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(chatobj);
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

    public String getAssignedToById(String enquiry_table_id) {
        List<String> idList = new ArrayList<>();
        String organisation_name = "APOGEE GNSS";
        String designation_name = SALES_EXECUTIVE;

        String salesExecutiveDetails = "";
        JSONObject jObjSaleExecutive = new JSONObject();
        String msg = "not exists";

        String query = "select * from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id + "' and active='Y';";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                String assignedToId = rset.getString("assigned_to");
                idList.add(assignedToId);
            }

            salesExecutiveDetails = GetExecutives(organisation_name, designation_name);
            jObjSaleExecutive = new JSONObject(salesExecutiveDetails);
            JSONArray jsonArraySalesExecutive = jObjSaleExecutive.getJSONArray("json");
            for (int i = 0; i < jsonArraySalesExecutive.length(); i++) {
                JSONObject toObj = jsonArraySalesExecutive.getJSONObject(i);
                int salesExecutiveId = toObj.getInt("key_person_id");
                String idString = Integer.toString(salesExecutiveId);

                if (idList.contains(idString)) {
                    System.out.println("ID " + idString + " exists in the list.");
                    msg = "exists";
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return msg;
    }

    public String GetExecutives(String a, String b) throws Exception {

        String baseiname = "";
        String getPersonByDesignationHierarchy = resourceBundle.getString("ORG_MODULE_PERSONBYDESIGNATION_API_URL");
//        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getPersonByDesignationHierarchy";
        String url = getPersonByDesignationHierarchy;

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        String urlParameters = ("{\"organisation_name\": \"" + a + "\",\"designation_name\": \"" + b + "\"}");

        httpClient.setDoOutput(
                true);
        try ( DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();

//        System.out.println(
//                "\nSending 'POST' request to URL : " + url);
//        System.out.println(
//                "Post parameters : " + urlParameters);
//        System.out.println(
//                "Response Code : " + responseCode);
        try ( BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);

            }
            return baseiname = response.toString();
            //print result
            // System.out.println("data" : +baseiname);

        }

    }

}
