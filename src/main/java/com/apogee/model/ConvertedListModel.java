/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import com.apogee.bean.EnquiryBean;
import static com.apogee.model.EnquiryModel.getPersonNameUsingId;
import static com.apogee.model.EnquiryModel.timeAgo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;

/**
 *
 * @author admin
 */
public class ConvertedListModel {

    private static Connection connection;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

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

    public ArrayList<EnquiryBean> getAllConvertedEnquiries(String source_search_id, String status_search_id, ServletContext ctx, String role) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "SELECT *\n"
                + "FROM enquiry_table AS et\n"
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                + "JOIN marketing_vertical AS mv ON mv.marketing_vertical_id=et.marketing_vertical_id\n"
                + "WHERE et.active = 'Y' AND es.status='Success(Converted)' \n";
        if (!source_search_id.equals("") && source_search_id != null) {
            query += "and et.enquiry_source_table_id='" + source_search_id + "' ";
        }
        if (!status_search_id.equals("") && status_search_id != null) {
            query += "and eat.enquiry_status_id='" + status_search_id + "'";
        }
        if (role.equals(SALES_MANAGER)) {
            query += " AND et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) AND et.created_at <= CURDATE()";
        }
        query += "AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    GROUP BY eat.enquiry_table_id\n"
                + ")\n"
                + "ORDER BY eat.enquiry_assigned_table_id DESC;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                int enquiry_table_id = rset.getInt("enquiry_table_id");
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setMarketing_vertical_name(rset.getString("marketing_vertical_name"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));

                bean.setStatus(rset.getString("status"));
                String order_date = rset.getString("created_at");
                String order_date_arr[] = order_date.split("-");
                String year = order_date_arr[0];
                String month = order_date_arr[1];
                String day_time = order_date_arr[2];
                if (month.equals("01")) {
                    month = "Jan";
                }
                if (month.equals("02")) {
                    month = "Feb";
                }
                if (month.equals("03")) {
                    month = "Mar";
                }
                if (month.equals("04")) {
                    month = "Apr";
                }
                if (month.equals("05")) {
                    month = "May";
                }
                if (month.equals("06")) {
                    month = "Jun";
                }
                if (month.equals("07")) {
                    month = "Jul";
                }
                if (month.equals("08")) {
                    month = "Aug";
                }
                if (month.equals("09")) {
                    month = "Sep";
                }
                if (month.equals("10")) {
                    month = "Oct";
                }
                if (month.equals("11")) {
                    month = "Nov";
                }
                if (month.equals("12")) {
                    month = "Dec";
                }

                String day_time_arr[] = day_time.split(" ");
                String day = day_time_arr[0];
                String time = day_time_arr[1];

                String new_order_date = day + "-" + month + "-" + year + " " + time;
//                System.out.println(new_order_date);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                java.util.Date date = new java.util.Date();
                String currentDateString = dateFormatter.format(date);
                java.util.Date currentDate = dateFormatter.parse(currentDateString);
                java.util.Date past_date = dateFormatter.parse(new_order_date);

                String time_ago = timeAgo(currentDate, past_date);
                bean.setTime_ago(time_ago);
                String query2 = "select * from  enquiry_assigned_table  where enquiry_table_id='" + enquiry_table_id + "'"
                        + "and updated_date_time!='' order by revision_no desc limit 0,1 ";
                PreparedStatement pst = connection.prepareStatement(query2);
                ResultSet rsts = pst.executeQuery();
                if (rsts.next()) {

                    String updated_date_time = rsts.getString("updated_date_time");
                    if (updated_date_time == "" || updated_date_time == null) {
                        String assigned_to = "";
                        bean.setAssigned_to(assigned_to);
                    } else {
                        String assigned_to = rsts.getString("assigned_to");
                        bean.setAssigned_to(EnquiryModel.getPersonNameUsingId(assigned_to, ctx));
//                        DashboardModel dashmodel = new DashboardModel();
//                        String baseiname = dashmodel.getKeyPersonDetailsById(assigned_to);
//                        if (baseiname != "") {
//                            try {
//                                ObjectMapper objectMapper = new ObjectMapper();
//                                JsonNode jsonNode = objectMapper.readTree(baseiname);
//                                String keyPersonName = jsonNode.get("key_person_name").asText();
//                                bean.setAssigned_to(keyPersonName);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getMarketingVertical() {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select * from marketing_vertical where active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();

                bean.setMarketing_vertical_id(rset.getInt("marketing_vertical_id"));
                bean.setMarketing_vertical_name(rset.getString("marketing_vertical_name"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getAllSources() {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select * from enquiry_source_table where active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();

                bean.setEnquiry_source_table_id(rset.getInt("enquiry_source_table_id"));
                bean.setEnquiry_source(rset.getString("enquiry_source"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getAllStatus() {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select * from enquiry_status where active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();

                bean.setEnquiry_status_id(rset.getInt("enquiry_status_id"));
                bean.setStatus(rset.getString("status"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return list;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

}
