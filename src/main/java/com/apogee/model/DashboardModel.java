/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import com.apogee.bean.EnquiryBean;
import com.apogee.bean.DashboardBean;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author admin
 */
public class DashboardModel {

    private static Connection connection;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.registartioncummodel.LoginModel.setConnection(): " + e);
        }
    }

    public int getCountEnquiries(String role, String organisation, String key_person_id) {
        PreparedStatement psmt;
        ResultSet rst;
        int emergency_id = 0;
        int count = 0;
        try {
            if (key_person_id.equals("99") || key_person_id.equals("115")) {
                String query = "SELECT count(*) as total_enquiry FROM enquiry_table AS et JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                        + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                        + "where  et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and  et.active='Y' "
                        + "and eat.active='Y' and es.active='Y' and eat.assigned_to='" + key_person_id + "' ";

                // String query = " select count(*) as total_enquiry from enquiry_table where active='Y'";
                psmt = connection.prepareStatement(query);
                rst = psmt.executeQuery();
                if (rst.next()) {
                    count = rst.getInt("total_enquiry");
                } else {
                    count = 0;
                }
            } else {
                String query = "select count(*) as total_enquiry from enquiry_table where active='Y';";

                // String query = " select count(*) as total_enquiry from enquiry_table where active='Y'";
                psmt = connection.prepareStatement(query);
                rst = psmt.executeQuery();
                if (rst.next()) {
                    count = rst.getInt("total_enquiry");
                } else {
                    count = 0;
                }

            }
        } catch (Exception e) {
            System.out.println("com.jpss.emergencymodule.EmergencyModel.saveData(): " + e);
        }
        return count;
    }

    public int getCountEnquiriesforExecutives(String key_person_id) {
        PreparedStatement psmt;
        ResultSet rst;
        int emergency_id = 0;
        int count = 0;
        try {
String query = "SELECT count(*) as total_enquiry FROM enquiry_table AS et JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                        + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                        + "where  et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and  et.active='Y' "
                        + "and eat.active='Y' and es.active='Y' and eat.assigned_to='" + key_person_id + "' ";
//            String query = " SELECT COUNT(*) as total_enquiry FROM (\n"
//                    + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no) AS max_revision_no\n"
//                    + "    FROM enquiry_assigned_table AS eat\n"
//                    + "    WHERE eat.assigned_to = '" + key_person_id + "' and  et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) AND eat.active = 'Y'\n"
//                    + "    GROUP BY eat.enquiry_table_id\n"
//                    + ") AS subquery; ";

            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("total_enquiry");
            } else {
                count = 0;
            }
        } catch (Exception e) {
            System.out.println("com.jpss.emergencymodule.EmergencyModel.saveData(): " + e);
        }
        return count;
    }

    public int getPendingEnquiries(String role, String organisation, String key_person_id) throws Exception {
        String baseiname = getAllDegignatedPersons(role, organisation);
        int count = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(baseiname);
            JsonNode jsonArray = jsonNode.get("json");
            if (jsonArray.isArray()) {
                for (JsonNode jsonElement : jsonArray) {
//                    String keyPersonId = jsonElement.get("key_person_id").asText();

                    if (key_person_id.equals("99") || key_person_id.equals("115")) {

//                        String query = "SELECT count(*) as count FROM enquiry_table AS et "
//                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
//                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
//                                + "WHERE et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and et.active = 'Y' AND es.status = 'Pending' AND (eat.enquiry_table_id, eat.revision_no) IN "
//                                + "(  SELECT eat.enquiry_table_id, MAX(eat.revision_no) FROM enquiry_assigned_table AS eat GROUP BY eat.enquiry_table_id )"
//                                + "and eat.active='Y' and eat.assigned_to='"+keyPersonId+"' ORDER BY eat.revision_no DESC";
                        String query = "SELECT count(*) as count FROM enquiry_table AS et JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id \n"
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id WHERE \n"
                                + " et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and et.active = 'Y' AND es.status = 'Pending'  AND\n"
                                + " (eat.enquiry_table_id, eat.revision_no) IN (  SELECT eat.enquiry_table_id, MAX(eat.revision_no) \n"
                                + "FROM enquiry_assigned_table AS eat where eat.active='Y' and eat.assigned_to='" + key_person_id + "' GROUP BY eat.enquiry_table_id ) and eat.active='Y' and eat.assigned_to='" + key_person_id + "' ORDER BY eat.revision_no DESC;";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");

                        }

                    } else {
                        String query = "SELECT count(*) as count FROM enquiry_table AS et "
                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                                + "WHERE et.active = 'Y' AND es.status = 'Pending' AND (eat.enquiry_table_id, eat.revision_no) IN "
                                + "(  SELECT eat.enquiry_table_id, MAX(eat.revision_no) FROM enquiry_assigned_table AS eat GROUP BY eat.enquiry_table_id )"
                                + "ORDER BY eat.revision_no DESC";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");

                        }

                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int getSoldThisMonthCount(String role, String organisation, String key_person_id) throws Exception {
        String baseiname = getAllDegignatedPersons(role, organisation);
        int count = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(baseiname);
            JsonNode jsonArray = jsonNode.get("json");
            if (jsonArray.isArray()) {
                for (JsonNode jsonElement : jsonArray) {
//                    String keyPersonId = jsonElement.get("key_person_id").asText();

                    if (key_person_id.equals("99") || key_person_id.equals("115")) {

                        String query = "SELECT COUNT(*) AS count\n"
                                + "FROM enquiry_table AS et\n"
                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id\n"
                                + "WHERE YEAR(et.created_at) = YEAR(CURDATE()) \n"
                                + "  AND MONTH(et.created_at) = MONTH(CURDATE())\n"
                                + "  AND et.active = 'Y' \n"
                                + "  AND es.status = 'Success(Converted)'  \n"
                                + "  AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                                + "      SELECT eat.enquiry_table_id, MAX(eat.revision_no) AS max_revision_no\n"
                                + "      FROM enquiry_assigned_table AS eat\n"
                                + "      WHERE eat.active = 'Y' AND eat.assigned_to = '99'\n"
                                + "      GROUP BY eat.enquiry_table_id\n"
                                + "  )\n"
                                + "  AND eat.active = 'Y' AND eat.assigned_to = '99';";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");
                        }
                    } else {
                        String query = "SELECT count(*) as count FROM enquiry_table AS et "
                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                                + "WHERE et.active = 'Y' AND es.status = 'Success(Converted)' AND (eat.enquiry_table_id, eat.revision_no) IN "
                                + "(  SELECT eat.enquiry_table_id, MAX(eat.revision_no) FROM enquiry_assigned_table AS eat GROUP BY eat.enquiry_table_id )"
                                + "ORDER BY eat.revision_no DESC";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");

                        }

                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int gettotalsoldCount(String role, String organisation, String key_person_id) throws Exception {
        String baseiname = getAllDegignatedPersons(role, organisation);
        int count = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(baseiname);
            JsonNode jsonArray = jsonNode.get("json");
            if (jsonArray.isArray()) {
                for (JsonNode jsonElement : jsonArray) {
//                    String keyPersonId = jsonElement.get("key_person_id").asText();

                    if (key_person_id.equals("99") || key_person_id.equals("115")) {

                        String query = "SELECT COUNT(*) AS count\n"
                                + "FROM enquiry_table AS et\n"
                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id\n"
                                + "WHERE YEAR(et.created_at) = YEAR(CURDATE()) \n"
                                + "  AND MONTH(et.created_at) = MONTH(CURDATE())\n"
                                + "  AND et.active = 'Y' \n"
                                + "  AND es.status = 'Success(Converted)'  \n"
                                + "  AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                                + "      SELECT eat.enquiry_table_id, MAX(eat.revision_no) AS max_revision_no\n"
                                + "      FROM enquiry_assigned_table AS eat\n"
                                + "      WHERE eat.active = 'Y' AND eat.assigned_to = '" + key_person_id + "'\n"
                                + "      GROUP BY eat.enquiry_table_id\n"
                                + "  )\n"
                                + "  AND eat.active = 'Y' AND eat.assigned_to = '" + key_person_id + "';";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");

                        }

                    } else {
                        String query = "SELECT count(*) as count FROM enquiry_table AS et "
                                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                                + "WHERE et.active = 'Y' AND es.status = 'Success(Converted)' AND (eat.enquiry_table_id, eat.revision_no) IN "
                                + "(  SELECT eat.enquiry_table_id, MAX(eat.revision_no) FROM enquiry_assigned_table AS eat GROUP BY eat.enquiry_table_id )"
                                + "ORDER BY eat.revision_no DESC";
                        PreparedStatement pst;

                        pst = connection.prepareStatement(query);
                        ResultSet rst = pst.executeQuery();
                        while (rst.next()) {
                            count = rst.getInt("count");

                        }

                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int getSoldThisMonthCountForExecutives(String key_person_id) {

        int count = 0;

        String query = "select * from enquiry_table as et join enquiry_assigned_table as eat on eat.enquiry_table_id=et.enquiry_table_id "
                + "join enquiry_status as es on es.enquiry_status_id=eat.enquiry_status_id where et.active='Y' "
                + "and eat.active='Y'  and eat.assigned_to='" + key_person_id + "' and YEAR(et.created_at) = YEAR(CURDATE()) \n"
                + "  AND MONTH(et.created_at) = MONTH(CURDATE())\n"
                + "and es.status='Success(Converted)' "
                + "GROUP BY eat.enquiry_table_id";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count++;
                String enquiry_table_id = rst.getString("enquiry_table_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int gettotalsoldCountForExecutives(String key_person_id) {

        int count = 0;

        String query = "select * from enquiry_table as et join enquiry_assigned_table as eat on eat.enquiry_table_id=et.enquiry_table_id "
                + "join enquiry_status as es on es.enquiry_status_id=eat.enquiry_status_id where et.active='Y' and eat.active='Y'  "
                + "and   et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and eat.assigned_to='" + key_person_id + "' and es.status='Success(Converted)' "
                + "GROUP BY eat.enquiry_table_id";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count++;
                String enquiry_table_id = rst.getString("enquiry_table_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int getPendingEnquiriesforExecutives(String key_person_id) {

        int count = 0;

        String query = "select * from enquiry_table as et join enquiry_assigned_table as eat on eat.enquiry_table_id=et.enquiry_table_id "
                + "join enquiry_status as es on es.enquiry_status_id=eat.enquiry_status_id where  et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) and et.active='Y' and eat.active='Y'  "
                + "and eat.assigned_to='" + key_person_id + "' and es.status='Pending' "
                + "GROUP BY eat.enquiry_table_id";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count++;
                String enquiry_table_id = rst.getString("enquiry_table_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public ArrayList<EnquiryBean> getAllEnquiries(ServletContext ctx) {

        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

//        String query = "select * from enquiry_table as et join enquiry_assigned_table as eat on eat.enquiry_table_id=et.enquiry_table_id "
//                + "join enquiry_status as es on es.enquiry_status_id=eat.enquiry_status_id where et.active='Y' GROUP BY eat.enquiry_table_id "
//                + "ORDER BY et.enquiry_table_id DESC LIMIT 0, 8 ";
//        String query = "SELECT et.sender_name,et.sender_mob,et.product_name,et.enquiry_table_id,et.enquiry_date_time, "
//                + " eat.assigned_to , es.status, eat.updated_date_time "
//                + "FROM enquiry_table AS et\n"
//                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
//                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
//                + "WHERE et.active = 'Y'\n";
//
//        query += " AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
//                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
//                + "    FROM enquiry_assigned_table AS eat\n"
//                + "    GROUP BY eat.enquiry_table_id\n"
//                + ")\n"
//                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC LIMIT 0, 8";
        String query = "SELECT et.sender_name,et.sender_mob,et.product_name,et.enquiry_table_id,et.enquiry_date_time,"
                + "eat.assigned_to , es.status, eat.updated_date_time "
                + "FROM enquiry_table AS et\n"
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                + "WHERE et.active = 'Y' AND et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) ";

        query += " AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    GROUP BY eat.enquiry_table_id\n"
                + ")\n"
                + "ORDER BY et.enquiry_date_time DESC LIMIT 0, 8 ";
//                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC LIMIT 0, 8 ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();

                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));

                String assigned_to = "";

//                String baseiname = getKeyPersonDetailsById(assigned_to);
//                if (baseiname != "") {
//                    try {
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        //JsonNode jsonNode = objectMapper.readTree(baseiname);
//
//                        JsonNode jsonNode = objectMapper.readTree(baseiname);
//                        String keyPersonName = jsonNode.get("key_person_name").asText();
//                        bean.setAssigned_to(keyPersonName);
////                        bean.setAssigned_to(rset.getString(keyPersonName));
//                        //JsonNode dataNode = jsonNode.get("");
////                        if (jsonNode.isArray() && jsonNode.size() > 0) {
////                            JsonNode firstElement = jsonNode.get(0);
////                            String key_person_name = firstElement.get("key_person_name").asText();
////                            bean.setAssigned_to(rset.getString(key_person_name));
////                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                String updated_date_time = rset.getString("updated_date_time");
                if (updated_date_time.equals("") || updated_date_time == null) {
                    bean.setAssigned_to(assigned_to);
                } else {
                    assigned_to = rset.getString("assigned_to");
//                    DashboardModel dashmodel = new DashboardModel();

                    // Optimize on 28-08-2023
                    bean.setAssigned_to(EnquiryModel.getPersonNameUsingId(assigned_to, ctx));
                    // Optimize on 28-08-2023

                }
                bean.setStatus(rset.getString("status"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getAllLatestEnquiriesForExecutives(String key_person_id, ServletContext ctx) {

        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

//        String query = "SELECT * FROM enquiry_table as et JOIN enquiry_assigned_table as eat ON eat.enquiry_table_id = et.enquiry_table_id "
//                + "JOIN enquiry_status as es ON es.enquiry_status_id = eat.enquiry_status_id "
//                + "WHERE et.active = 'Y' AND eat.active = 'Y' AND assigned_to = '" + key_person_id + "' "
//                + "GROUP BY eat.enquiry_table_id HAVING es.status != 'Closed' AND es.status != 'Demo Required' "
//                + "ORDER BY et.enquiry_table_id DESC LIMIT 0, 8;";
        String query = "SELECT * "
                + "FROM enquiry_table AS et "
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id "
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                + "WHERE et.active = 'Y' AND et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) ";

        query += "AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    WHERE eat.assigned_to = '" + key_person_id + "'  AND eat.active = 'Y'\n"
                + "    GROUP BY eat.enquiry_table_id HAVING es.status != 'Closed' AND es.status != 'Demo Required') "
                + "ORDER BY et.enquiry_date_time DESC LIMIT 0, 8 ";
//                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC LIMIT 0, 8 ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();

                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));

                String assigned_to = rset.getString("assigned_to");

                // Optimize on 28-08-2023
                bean.setAssigned_to(EnquiryModel.getPersonNameUsingId(assigned_to, ctx));
                // Optimize on 28-08-2023

//                }
                bean.setStatus(rset.getString("status"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public ArrayList<DashboardBean> getAllDegignatedPersonsListForExecutive(String role, String organisation, String key_person_id) {
        ArrayList<DashboardBean> list = new ArrayList<DashboardBean>();
        String baseiname;
        try {
            baseiname = getAllDegignatedPersons(role, organisation);
            if (baseiname != "") {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(baseiname);
                    JsonNode jsonArray = jsonNode.get("json");
                    if (jsonArray.isArray()) {
                        for (JsonNode jsonElement : jsonArray) {
                            String keyPersonId = jsonElement.get("key_person_id").asText();
                            if (key_person_id.endsWith(keyPersonId)) {
                                String salutation = jsonElement.get("salutation").asText();
                                String key_person_name = jsonElement.get("key_person_name").asText();
                                String designation_id = jsonElement.get("designation_id").asText();
                                String designation_name = jsonElement.get("designation").asText();
                                DashboardBean bean = new DashboardBean();
                                bean.setKey_person_name(key_person_name);
                                bean.setSalutation(salutation);
                                bean.setKey_person_id(keyPersonId);
                                bean.setDesignation_id(designation_id);
                                bean.setDesignation_name(designation_name);
                                int notansweringcount = getAllnotanswering(keyPersonId);
                                bean.setNotansweringcount(notansweringcount);
                                int followupcount = getAllfollowup(keyPersonId);
                                bean.setFollowupcount(followupcount);
                                int democount = getAlldemo(keyPersonId);
                                bean.setDemocount(democount);
                                int convertedcount = getAllconverted(keyPersonId);
                                bean.setConvertedcount(convertedcount);
                                list.add(bean);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<DashboardBean> getAllDegignatedPersonsListForSearch(String role, String organisation, String key_person_id) {
        ArrayList<DashboardBean> list = new ArrayList<DashboardBean>();
        String baseiname;
        try {
            baseiname = getAllDegignatedPersons(role, organisation);
            if (baseiname != "") {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(baseiname);
                    JsonNode jsonArray = jsonNode.get("json");
                    if (jsonArray.isArray()) {
                        for (JsonNode jsonElement : jsonArray) {
                            String keyPersonId = jsonElement.get("key_person_id").asText();
                            String salutation = jsonElement.get("salutation").asText();
                            String key_person_name = jsonElement.get("key_person_name").asText();
                            DashboardBean bean = new DashboardBean();
                            bean.setKey_person_name(key_person_name);
                            bean.setSalutation(salutation);
                            bean.setKey_person_id(keyPersonId);
                            list.add(bean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getAllnotanswering(String keyPersonId) {
        int notanswerinfcount = 0;
        String query = "select count(*) as totcount from enquiry_assigned_table as eat join enquiry_status as es "
                + "on es.enquiry_status_id=eat.enquiry_status_id where es.status='Not Answering' and eat.assigned_to='" + keyPersonId + "'  and eat.active='Y'  ; ";

        try {

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                notanswerinfcount = rst.getInt("totcount");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notanswerinfcount;
    }

    public int getAllfollowup(String keyPersonId) {
        int notanswerinfcount = 0;
        String query = "select count(*) as totcount from enquiry_assigned_table as eat join enquiry_status as es "
                + "on es.enquiry_status_id=eat.enquiry_status_id where es.status='Follow up Again' and eat.assigned_to='" + keyPersonId + "'  and eat.active='Y'  ; ";

        try {

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                notanswerinfcount = rst.getInt("totcount");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notanswerinfcount;
    }

    public int getAlldemo(String keyPersonId) {
        int notanswerinfcount = 0;
        String query = "select count(*) as totcount from enquiry_assigned_table as eat join enquiry_status as es "
                + "on es.enquiry_status_id=eat.enquiry_status_id where es.status='Demo Required' and eat.assigned_to='" + keyPersonId + "'  and eat.active='Y'  ; ";

        try {

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                notanswerinfcount = rst.getInt("totcount");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notanswerinfcount;
    }

    public int getAllconverted(String keyPersonId) {
        int notanswerinfcount = 0;
        String query = "select count(*) as totcount from enquiry_assigned_table as eat join enquiry_status as es "
                + "on es.enquiry_status_id=eat.enquiry_status_id where es.status='Success(Converted)' and eat.assigned_to='" + keyPersonId + "'  and eat.active='Y'  ; ";

        try {

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                notanswerinfcount = rst.getInt("totcount");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notanswerinfcount;
    }

    public ArrayList<DashboardBean> getAllDegignatedPersonsListforsalesmanager(String role, String organisation, String key_person_id) {
        ArrayList<DashboardBean> list = new ArrayList<DashboardBean>();
        String baseiname;
        try {
            baseiname = getAllDegignatedPersons(role, organisation);
            if (baseiname != "") {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(baseiname);
                    JsonNode jsonArray = jsonNode.get("json");
                    if (jsonArray.isArray()) {
                        for (JsonNode jsonElement : jsonArray) {
                            String keyPersonId = jsonElement.get("key_person_id").asText();

                            if (keyPersonId.endsWith("99") || keyPersonId.endsWith("115")) {

                            } else {

                                String salutation = jsonElement.get("salutation").asText();
                                String key_person_name = jsonElement.get("key_person_name").asText();
                                String designation_id = jsonElement.get("designation_id").asText();
                                String designation_name = jsonElement.get("designation").asText();
                                DashboardBean bean = new DashboardBean();
                                bean.setKey_person_name(key_person_name);
                                bean.setSalutation(salutation);
                                bean.setKey_person_id(keyPersonId);
                                bean.setDesignation_id(designation_id);
                                bean.setDesignation_name(designation_name);
                                int notansweringcount = getAllnotanswering(keyPersonId);
                                bean.setNotansweringcount(notansweringcount);
                                int followupcount = getAllfollowup(keyPersonId);
                                bean.setFollowupcount(followupcount);
                                int democount = getAlldemo(keyPersonId);
                                bean.setDemocount(democount);
                                int convertedcount = getAllconverted(keyPersonId);
                                list.add(bean);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<DashboardBean> getAllDegignatedPersonsListforsalessupervisor(String role, String organisation, String key_person_id) {
        ArrayList<DashboardBean> list = new ArrayList<DashboardBean>();
        String baseiname;
        try {
            baseiname = getAllDegignatedPersons(role, organisation);
            if (baseiname != "") {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(baseiname);
                    JsonNode jsonArray = jsonNode.get("json");
                    if (jsonArray.isArray()) {
                        for (JsonNode jsonElement : jsonArray) {
                            String keyPersonId = jsonElement.get("key_person_id").asText();

                            if (keyPersonId.endsWith("108") || keyPersonId.endsWith("114")) {

                            } else {
                                //String keyPersonId = jsonElement.get("key_person_id").asText();
                                String salutation = jsonElement.get("salutation").asText();
                                String key_person_name = jsonElement.get("key_person_name").asText();
                                String designation_id = jsonElement.get("designation_id").asText();
                                String designation_name = jsonElement.get("designation").asText();
                                DashboardBean bean = new DashboardBean();
                                bean.setKey_person_name(key_person_name);
                                bean.setSalutation(salutation);
                                bean.setKey_person_id(keyPersonId);
                                bean.setDesignation_id(designation_id);
                                bean.setDesignation_name(designation_name);
                                int notansweringcount = getAllnotanswering(keyPersonId);
                                bean.setNotansweringcount(notansweringcount);
                                int followupcount = getAllfollowup(keyPersonId);
                                bean.setFollowupcount(followupcount);
                                int democount = getAlldemo(keyPersonId);
                                bean.setDemocount(democount);
                                int convertedcount = getAllconverted(keyPersonId);
                                list.add(bean);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public String getAllDegignatedPersons(String a, String b) throws Exception {
        String baseiname = "";

        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getPersonByDesignationHierarchy";
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        String urlParameters = ("{\"designation_name\": \"" + a + "\",\"organisation_name\":\"" + b + "\"}");

        httpClient.setDoOutput(
                true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
//
//        System.out.println(
//                "\nSending 'POST' request to URL : " + url);
//        System.out.println(
//                "Post parameters : " + urlParameters);
//        System.out.println(
//                "Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);

            }
            return baseiname = response.toString();
        }
    }

    public String getKeyPersonDetailsById(String a) throws Exception {

        String baseiname = "";

        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getKeyPersonDetail";
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        String urlParameters = ("{\"key_person_id\": \"" + a + "\"}");

        httpClient.setDoOutput(
                true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
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
        try (BufferedReader in = new BufferedReader(
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

    public String getAllKeyPersonDetails() throws Exception {
        String baseiname = "";
        String getAllKeyPersonDetails = resourceBundle.getString("GET_ALL_KEY_PERSON_DETAILS");
//        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getAllKeyPersonDetails";
        String url = getAllKeyPersonDetails;
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        httpClient.setDoOutput(
                true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.flush();
        }
        int responseCode = httpClient.getResponseCode();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return baseiname = response.toString();
        }

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public String deleteReminder(String reminder_id) {
        String result = "";
        try {
            String deleteReminderDetails = resourceBundle.getString("COMM_MODULE_DELETECHATMESSAGES_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/deleteChatMessage");
            URL url = new URL(deleteReminderDetails);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(reminder_id);
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

}
