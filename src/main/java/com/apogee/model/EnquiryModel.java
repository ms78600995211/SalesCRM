/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import com.apogee.bean.EnquiryBean;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.codehaus.jettison.json.JSONException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author admin
 */
public class EnquiryModel {

    private static com.itextpdf.text.Font catFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18,
            com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font redFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.NORMAL, BaseColor.RED);
    private static com.itextpdf.text.Font subFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
            com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font smallBold = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.BOLD);
    PdfTemplate total;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    private static Connection connection;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private String message = "";
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

//    public static void addMetaData(Document document) {
//        document.addTitle("PDF");
//        document.addSubject("Using iText");
//        document.addKeywords("Java, PDF, iText");
//    }
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

//    public void addContent(Document document, String path, String loggedUser, String logged_key_person_id, String enquiry_source, String status)
//            throws BadElementException, IOException, DocumentException,
//            JSONException {
//
//        Paragraph preface = new Paragraph();
//        addEmptyLine(preface, 2);
//        preface.add(new Paragraph("Enquiry Report", catFont));
//        preface.add(new LineSeparator());
//        preface.setAlignment(Element.ALIGN_CENTER);
//        addEmptyLine(preface, 2);
//
//        preface.add(new Paragraph(
//                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//                smallBold));
//
//        // Creating an ImageData object       
//        Image img = Image.getInstance(path);
//        img.setAlignment(Element.ALIGN_LEFT);
//
//        // Start Header
//        float[] colWidths1 = {1, 18, 5};
//        PdfPTable tables = new PdfPTable(colWidths1);
//        tables.setWidthPercentage(100);
//        com.itextpdf.text.Font bold12 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font normal = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.NORMAL);
//
//        Paragraph preface3 = new Paragraph();
//        Chunk chunk1 = new Chunk("Uttar Pradesh (Noida)\n", bold12);
//        Chunk chunk2 = new Chunk("+91-7624009260\n", normal);
////        A - 67, A Block, Sector 63, Noida, Uttar Pradesh 201301
//        Chunk chunk3 = new Chunk("A - 67, A Block, Sector 63\nNoida (UP), 201301", normal);
//        Paragraph p = new Paragraph();
//        p.add(chunk1);
//        p.add(chunk2);
//        p.add(chunk3);
//
//        preface3.add(new Paragraph(p));
//
//        PdfPCell cellOne = new PdfPCell(img);
//        PdfPCell cellTwo = new PdfPCell(preface3);
//
//        cellOne.setColspan(2);
//        cellOne.setBorder(Rectangle.NO_BORDER);
//        cellTwo.setBorder(Rectangle.NO_BORDER);
//
//        tables.addCell(cellOne);
//        tables.addCell(cellTwo);
//
//        document.add(tables);
//        Paragraph preface4 = new Paragraph();
//        preface4.add(new LineSeparator());
//        document.add(preface4);
//        // Adding image to the document       
//        addEmptyLine(preface, 3);
//        addEmptyLine(preface, 1);
//        document.add(preface);
//        //End header
//
//        //Start Table for finished and modules
//        float[] colWidths = {1, 3, 2, 2, 3, 3, 3, 4, 2, 2, 4};
//        PdfPTable table = new PdfPTable(colWidths);
//        table.setWidthPercentage(100);
//
//        com.itextpdf.text.Font bold11 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font bold10 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font bold13 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 13, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font bold8 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font bold9 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.BOLD);
//        com.itextpdf.text.Font normal9 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.NORMAL);
//
//        PdfPCell c1 = new PdfPCell(new Phrase("  S. No.", bold12));
//        c1.setFixedHeight(25);
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase(" Sender Name", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  City", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  State", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("  Sender Mobile", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Sender Email", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Date", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Product Name", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Assigned To", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Status", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("  Remarks", bold12));
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("", bold12));
//        c1.setFixedHeight(15);
//        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//        table.addCell(c1);
//
//        try {
//            int count = 1;
//            List<EnquiryBean> list;
//            ServletContext ctx = null;
//            if (loggedUser.equals("Sales Executive")) {
//
//                list = getAllAssignedEnquiries(logged_key_person_id, enquiry_source, status, ctx);
//            } else {
//                list = getAllEnquiries(enquiry_source, status, ctx);
//            }
//            String sender_name = "", sender_mob = "", sender_email = "", product_name = "", city = "", state = "", enq_status = "", assigned_to = "",
//                    date_time = "", remark = "", enquiry_message = "";
//            if (list.size() > 0) {
//
//                for (int i = 0; i < list.size(); i++) {
//                    sender_name = list.get(i).getSender_name();
//                    sender_mob = list.get(i).getSender_mob();
//                    sender_email = list.get(i).getSender_email();
//                    product_name = list.get(i).getProduct_name();
//                    city = list.get(i).getEnquiry_city();
//                    state = list.get(i).getEnquiry_state();
//                    enq_status = list.get(i).getStatus();
//                    assigned_to = list.get(i).getAssigned_to();
//                    if (assigned_to == null) {
//                        assigned_to = "";
//                    }
//                    date_time = list.get(i).getEnquiry_date_time();
//                    remark = list.get(i).getAssigned_remark();
//                    enquiry_message = list.get(i).getEnquiry_message();
//                    String count_str = String.valueOf(count);
//
//                    PdfPCell c2 = new PdfPCell(new Phrase("  " + String.valueOf(count) + ".", bold12));
//                    table.addCell(c2);
//                    table.addCell(new Phrase("  " + sender_name, normal9));
//                    table.addCell(new Phrase("  " + city, normal9));
//                    table.addCell(new Phrase("  " + state, normal9));
//                    table.addCell(new Phrase("  " + sender_mob, normal9));
//                    table.addCell(new Phrase("  " + sender_email, normal9));
//                    table.addCell(new Phrase("  " + date_time, normal9));
//                    table.addCell(new Phrase("  " + product_name, normal9));
//                    table.addCell(new Phrase("  " + assigned_to, normal9));
//                    table.addCell(new Phrase("  " + enq_status, normal9));
//                    table.addCell(new Phrase("  " + remark, normal9));
//                    count++;
//                }
//            }
//
//            document.add(table);
//        } catch (Exception e) {
//            System.out.println("com.dashboard.model.DealersOrderModel.addContent()" + e);
//        }
//
//    }
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

    public ArrayList<EnquiryBean> GetEnquiryDataById(String id) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        String query = "select * from enquiry_table as et join marketing_vertical as mv on mv.marketing_vertical_id=et.marketing_vertical_id  \n"
                + "join enquiry_source_table as est on est.enquiry_source_table_id=et.enquiry_source_table_id\n"
                + "where et.active='Y' and et.enquiry_table_id='" + id + "';";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_alternate_mob(rset.getString("sender_alternate_mob"));
                bean.setSender_alternate_email(rset.getString("sender_alternate_email"));
                bean.setEnquiry_source_table_id(rset.getInt("enquiry_source_table_id"));
                bean.setEnquiry_address(rset.getString("enquiry_address"));
                bean.setEnquiry_message(rset.getString("enquiry_message"));
                bean.setMarketing_vertical_name(rset.getString("marketing_vertical_name"));
                bean.setEnquiry_source(rset.getString("enquiry_source"));

                bean.setEnquiry_no(rset.getString("enquiry_no"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setSender_company_name(rset.getString("sender_company_name"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getStatusList() {
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

    public ArrayList<EnquiryBean> getAllEnquiries(String source_search_id, String status_search_id, String designation_search_id, ServletContext ctx, String role) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

//        String query = "SELECT * FROM enquiry_table AS et  JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id"
//                + " JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
//                + " JOIN marketing_vertical AS mv ON mv.marketing_vertical_id=et.marketing_vertical_id"
//                + " JOIN enquiry_source_table as est on et.enquiry_source_table_id=est.enquiry_source_table_id WHERE et.active = 'Y'";
//added  enquiry_source_table join in below query  to fetch enquiry_source 
        String query = "SELECT et.*,   eat.*,  es.*,    est.*,  mv.*, eat.remark AS eat_remark\n"
                + "FROM enquiry_table AS et\n"
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                + "JOIN enquiry_source_table AS est ON est.enquiry_source_table_id=et.enquiry_source_table_id "
                + "JOIN marketing_vertical AS mv ON mv.marketing_vertical_id=et.marketing_vertical_id\n"
                + "WHERE et.active = 'Y'\n";

        if (!source_search_id.equals("") && source_search_id != null) {
            query += "and et.enquiry_source_table_id='" + source_search_id + "' ";
        }
        if (!status_search_id.equals("") && status_search_id != null) {
            query += "and eat.enquiry_status_id='" + status_search_id + "'";
        }
        if (!designation_search_id.equals("") && designation_search_id != null) {
            query += "and eat.assigned_to='" + designation_search_id + "' "
                    + "and eat.updated_date_time!=''";

        }
        if (role.equals(SALES_MANAGER)) {
            query += " AND et.enquiry_date_time >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH)";
        }
        query += " AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    GROUP BY eat.enquiry_table_id\n"
                + ")\n"
                + "ORDER BY et.enquiry_date_time DESC";
//                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                int enquiry_table_id = rset.getInt("enquiry_table_id");
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setEnquiry_source(rset.getString("enquiry_source"));
                bean.setMarketing_vertical_name(rset.getString("marketing_vertical_name"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));
                bean.setImportantEnquiry(rset.getString("important"));
                bean.setAssigned_remark(rset.getString("eat_remark"));
                bean.setEnquiry_message(rset.getString("enquiry_message"));

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
                    if (updated_date_time.equals("") || updated_date_time == null) {
                        String assigned_to = "";
                        bean.setAssigned_to(assigned_to);
                    } else {
                        String assigned_to = rsts.getString("assigned_to");
                        DashboardModel dashmodel = new DashboardModel();

                        // Optimize on 28-08-2023
                        bean.setAssigned_to(getPersonNameUsingId(assigned_to, ctx));
                        // Optimize on 28-08-2023

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

    public void writeDataToExcel(String file_name, String loggedUser, String logged_key_person_id, String enquiry_source,
            String status, String designation_search_id, ServletContext ctx, String role) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Enquiry");

            HSSFRow rowhead = sheet.createRow((short) 0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 11);
            font.setFontName(HSSFFont.FONT_ARIAL);
            //font.setBoldweight(HSSFFont.COLOR_NORMAL);
//            font.setBold(true);
            font.setColor(HSSFColor.DARK_BLUE.index);
            style.setFont(font);
//Add these lines     
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            // style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            sheet.createFreezePane(0, 1);

            rowhead.createCell(0).setCellValue("S.No.");
            rowhead.createCell(1).setCellValue("Sender Name");
            rowhead.createCell(2).setCellValue("City");
            rowhead.createCell(3).setCellValue("State");
            rowhead.createCell(4).setCellValue("Sender Mob");
            rowhead.createCell(5).setCellValue("Sender Email");
            rowhead.createCell(6).setCellValue("Date Time");
            rowhead.createCell(7).setCellValue("Product Name");
            rowhead.createCell(8).setCellValue("Assigned To");
            rowhead.createCell(9).setCellValue("Status");
            rowhead.createCell(10).setCellValue("Remarks");

            int count = 1;
            List<EnquiryBean> list;

            if (loggedUser.equals(SALES_EXECUTIVE)) {

                list = getAllAssignedEnquiries(logged_key_person_id, enquiry_source, status, designation_search_id, ctx);

            } else {

                list = getAllEnquiries(enquiry_source, status, designation_search_id, ctx, role);

            }
            String sender_name = "", sender_mob = "", sender_email = "", product_name = "", city = "", state = "", enq_status = "", assigned_to = "",
                    date_time = "", remark = "", enquiry_message = "";
            HSSFRow row = sheet.createRow((short) 1);
            row.createCell(0).setCellValue("");
            row.createCell(1).setCellValue("");
            row.createCell(2).setCellValue("");
            row.createCell(3).setCellValue("");
            row.createCell(4).setCellValue("");
            row.createCell(5).setCellValue("");
            row.createCell(6).setCellValue("");
            row.createCell(7).setCellValue("");
            row.createCell(8).setCellValue("");
            row.createCell(9).setCellValue("");
            row.createCell(10).setCellValue("");

            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    sender_name = list.get(i).getSender_name();
                    sender_mob = list.get(i).getSender_mob();
                    sender_email = list.get(i).getSender_email();
                    product_name = list.get(i).getProduct_name();
                    city = list.get(i).getEnquiry_city();
                    state = list.get(i).getEnquiry_state();
                    enq_status = list.get(i).getStatus();
                    assigned_to = list.get(i).getAssigned_to();
                    date_time = list.get(i).getEnquiry_date_time();
                    remark = list.get(i).getAssigned_remark();
                    enquiry_message = list.get(i).getEnquiry_message();
                    String count_str = String.valueOf(count);

                    HSSFRow row1 = sheet.createRow((short) (count + 1));
                    row1.createCell(0).setCellValue(count);
                    row1.createCell(1).setCellValue(sender_name);
                    row1.createCell(2).setCellValue(city);
                    row1.createCell(3).setCellValue(state);
                    row1.createCell(4).setCellValue(sender_mob);
                    row1.createCell(5).setCellValue(sender_email);
                    row1.createCell(6).setCellValue(date_time);
                    row1.createCell(7).setCellValue(product_name);
                    row1.createCell(8).setCellValue(assigned_to);
                    row1.createCell(9).setCellValue(enq_status);
                    row1.createCell(10).setCellValue(remark);
                    count++;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(file_name);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<EnquiryBean> getAllAssignedEnquiries(String key_person_id, String source_search_id, String status_search_id, String designation_search_id, ServletContext ctx) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "SELECT et.*, eat.*, es.*,est.*,eat.remark AS eat_remark\n"
                + "FROM enquiry_table AS et\n"
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id\n"
                + "JOIN enquiry_source_table AS est ON est.enquiry_source_table_id=et.enquiry_source_table_id "
                + "WHERE et.active = 'Y'  AND (es.status != 'Demo Required' ) \n";
        if (!source_search_id.equals("") && source_search_id != null) {
            query += "and et.enquiry_source_table_id='" + source_search_id + "' ";
        }
        if (!status_search_id.equals("") && status_search_id != null) {
            query += "and eat.enquiry_status_id='" + status_search_id + "'";
        }
        if (!designation_search_id.equals("") && designation_search_id != null) {
            query += "and eat.assigned_to='" + designation_search_id + "' "
                    + "and eat.updated_date_time!=''";

        }

        query += " AND et.created_at >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) ";

        query += "AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    WHERE eat.assigned_to = '" + key_person_id + "'  AND eat.active = 'Y'\n"
                + "    GROUP BY eat.enquiry_table_id\n"
                + ")\n"
                + "ORDER BY et.enquiry_date_time DESC ";
//                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                int enquiry_table_id = rset.getInt("enquiry_table_id");
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setEnquiry_source(rset.getString("enquiry_source"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));
                bean.setStatus(rset.getString("status"));
                bean.setAssigned_remark(rset.getString("eat_remark"));
                String order_date = rset.getString("created_at");
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));
                bean.setImportantEnquiry(rset.getString("important"));
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
                String updated_date_time = rset.getString("updated_date_time");

                String assigned_to = rset.getString("assigned_to");
                DashboardModel dashmodel = new DashboardModel();

                // Optimize on 28-08-2023
                bean.setAssigned_to(getPersonNameUsingId(assigned_to, ctx));
                // Optimize on 28-08-2023

//                String baseiname = dashmodel.getKeyPersonDetailsById(assigned_to);
//                if (baseiname != "") {
//                    try {
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        JsonNode jsonNode = objectMapper.readTree(baseiname);
//                        String keyPersonName = jsonNode.get("key_person_name").asText();
//                        bean.setAssigned_to(keyPersonName);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealersOrderModel getAllApprovedOrders()-" + e);
        }
        return list;
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

    public int AddManualEnquiry(EnquiryBean bean, String currentdatetime, String created_by, String state, String city, String sender_company_name, String product_name) {
        int added = 0;
        int rowsAffected = 0;
        int rowsAffecteds = 0;
        int enquiry_table_id = 0;
        int enquiry_no = 0;
        String query = "select enquiry_table_id from enquiry_table order by enquiry_table_id desc";
        String lastenquirynoformanual = "select * from enquiry_table where created_by='Manual' order by enquiry_table_id desc";
        String addquery = "insert into  enquiry_table (enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_no,"
                + "sender_name,sender_mob,enquiry_address,enquiry_message,active,revision_no,enquiry_date_time,created_by,sender_email,"
                + "sender_company_name,product_name,enquiry_city,enquiry_state,country,enquiry_call_duration,enquiry_reciever_mob,"
                + "sender_alternate_email,sender_alternate_mob,description"
                + ")"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst;
        PreparedStatement pstlast;
        try {
            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {

                enquiry_table_id = rst.getInt("enquiry_table_id");

                //enquiry_no = rst.getInt("enquiry_no");
                //String created_byy = rst.getString("created_by");
                pstlast = connection.prepareStatement(lastenquirynoformanual);
                ResultSet rstlast = pstlast.executeQuery();
                if (rstlast.next()) {
                    enquiry_no = rstlast.getInt("enquiry_no");
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, bean.getEnquiry_source_table_id());
                    psts.setInt(3, bean.getMarketing_vertical_id());
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, bean.getSender_name());
                    psts.setString(6, bean.getSender_mob());
                    psts.setString(7, bean.getEnquiry_address());
                    psts.setString(8, bean.getEnquiry_message());
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Manual");
                    psts.setString(13, bean.getSender_email());
                    psts.setString(14, sender_company_name);
                    psts.setString(15, product_name);
                    psts.setString(16, city);
                    psts.setString(17, state);
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, bean.getSender_alternate_email());
                    psts.setString(22, bean.getSender_alternate_mob());
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                    + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                    + "values(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                            pstss.setInt(1, id);
                            pstss.setString(2, "11");
                            pstss.setString(3, "Y");
                            pstss.setString(4, "0");
                            pstss.setString(5, "");
                            pstss.setString(6, "This is manual entry");
                            pstss.setString(7, "manual entry");
                            pstss.setString(8, created_by);
                            pstss.setString(9, created_by);
                            pstss.setString(10, "");
                            rowsAffecteds = pstss.executeUpdate();
                            if (rowsAffecteds > 0) {
                                added = 1;
                            }
                        }
                    }
                } else {
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, bean.getEnquiry_source_table_id());
                    psts.setInt(3, bean.getMarketing_vertical_id());
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, bean.getSender_name());
                    psts.setString(6, bean.getSender_mob());
                    psts.setString(7, bean.getEnquiry_address());
                    psts.setString(8, bean.getEnquiry_message());
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Manual");
                    psts.setString(13, bean.getSender_email());
                    psts.setString(14, "");
                    psts.setString(15, "");
                    psts.setString(16, "");
                    psts.setString(17, "");
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, bean.getSender_alternate_email());
                    psts.setString(22, bean.getSender_alternate_mob());
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                    + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                    + "values(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                            pstss.setInt(1, id);
                            pstss.setString(2, "11");
                            pstss.setString(3, "Y");
                            pstss.setString(4, "0");
                            pstss.setString(5, "");
                            pstss.setString(6, "This is manual entry");
                            pstss.setString(7, "manual entry");
                            pstss.setString(8, created_by);
                            pstss.setString(9, created_by);
                            pstss.setString(10, "");
                            rowsAffecteds = pstss.executeUpdate();
                            if (rowsAffecteds > 0) {
                                added = 1;
                            }
                        }
                    }
                }

            } else {

                pstlast = connection.prepareStatement(lastenquirynoformanual);
                ResultSet rstlast = pstlast.executeQuery();
                if (rstlast.next()) {
                    enquiry_no = rstlast.getInt("enquiry_no");
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, bean.getEnquiry_source_table_id());
                    psts.setInt(3, bean.getMarketing_vertical_id());
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, bean.getSender_name());
                    psts.setString(6, bean.getSender_mob());
                    psts.setString(7, bean.getEnquiry_address());
                    psts.setString(8, bean.getEnquiry_message());
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Manual");
                    psts.setString(13, bean.getSender_email());
                    psts.setString(14, sender_company_name);
                    psts.setString(15, product_name);
                    psts.setString(16, city);
                    psts.setString(17, state);
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, bean.getSender_alternate_email());
                    psts.setString(22, bean.getSender_alternate_mob());
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                    + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                    + "values(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                            pstss.setInt(1, id);
                            pstss.setString(2, "11");
                            pstss.setString(3, "Y");
                            pstss.setString(4, "0");
                            pstss.setString(5, "");
                            pstss.setString(6, "This is manual entry");
                            pstss.setString(7, "manual entry");
                            pstss.setString(8, created_by);
                            pstss.setString(9, created_by);
                            pstss.setString(10, "");
                            rowsAffecteds = pstss.executeUpdate();
                            if (rowsAffecteds > 0) {
                                added = 1;
                            }
                        }
                    }
                } else {
                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id + 1);
                    psts.setInt(2, bean.getEnquiry_source_table_id());
                    psts.setInt(3, bean.getMarketing_vertical_id());
                    psts.setInt(4, enquiry_no + 1);
                    psts.setString(5, bean.getSender_name());
                    psts.setString(6, bean.getSender_mob());
                    psts.setString(7, bean.getEnquiry_address());
                    psts.setString(8, bean.getEnquiry_message());
                    psts.setString(9, "Y");
                    psts.setInt(10, 0);
                    psts.setString(11, currentdatetime);
                    psts.setString(12, "Manual");
                    psts.setString(13, bean.getSender_email());
                    psts.setString(14, sender_company_name);
                    psts.setString(15, product_name);
                    psts.setString(16, city);
                    psts.setString(17, state);
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, bean.getSender_alternate_email());
                    psts.setString(22, bean.getSender_alternate_mob());
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String addquerytoAssign = "insert into  enquiry_assigned_table (enquiry_table_id,enquiry_status_id,active,revision_no,"
                                    + "created_by,description,remark,assigned_by,assigned_to,updated_date_time)"
                                    + "values(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstss = connection.prepareStatement(addquerytoAssign, Statement.RETURN_GENERATED_KEYS);
                            pstss.setInt(1, id);
                            pstss.setString(2, "11");
                            pstss.setString(3, "Y");
                            pstss.setString(4, "0");
                            pstss.setString(5, "");
                            pstss.setString(6, "This is manual entry");
                            pstss.setString(7, "manual entry");
                            pstss.setString(8, created_by);
                            pstss.setString(9, created_by);
                            pstss.setString(10, "");
                            rowsAffecteds = pstss.executeUpdate();
                            if (rowsAffecteds > 0) {
                                added = 1;
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnquiryModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    public int EditEnquiry(EnquiryBean bean, int enquiry_table_id, String currentdatetime,
            String created_by, String state, String city, String sender_company_name, String product_name) {
        int added = 0;
        int rowsAffected = 0;
        int rowsAffecteds = 0;
        //   int enquiry_table_id = 0;

        String query = "select * from enquiry_table where enquiry_table_id='" + enquiry_table_id + "' and active='Y' order by enquiry_table_id desc";
        String updatequery = "update enquiry_table set active='N' where enquiry_table_id='" + enquiry_table_id + "'";
        String addquery = "insert into  enquiry_table (enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_no,"
                + "sender_name,sender_mob,enquiry_address,enquiry_message,active,revision_no,enquiry_date_time,created_by,sender_email,"
                + "sender_company_name,product_name,enquiry_city,enquiry_state,country,enquiry_call_duration,enquiry_reciever_mob,"
                + "sender_alternate_email,sender_alternate_mob,description"
                + ")"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst;
        PreparedStatement pstlast;
        try {
            pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {

                enquiry_table_id = rst.getInt("enquiry_table_id");
                int enquiry_source_table_id = rst.getInt("enquiry_source_table_id");
                int marketing_vertical_id = rst.getInt("marketing_vertical_id");
                int enquiry_no = rst.getInt("enquiry_no");
                String sender_name = rst.getString("sender_name");
                String sender_mob = rst.getString("sender_mob");
                String enquiry_address = rst.getString("enquiry_address");
                String enquiry_message = rst.getString("enquiry_message");
                String active = rst.getString("active");
                int revision_no = rst.getInt("revision_no");
                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String created_by = rst.getString("created_by");
                String sender_email = rst.getString("sender_email");
                //String sender_company_name = rst.getString("sender_company_name");
                // String product_name = rst.getString("product_name");
                String enquiry_city = rst.getString("enquiry_city");
                String enquiry_state = rst.getString("enquiry_state");
                String country = rst.getString("country");
                String enquiry_call_duration = rst.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rst.getString("enquiry_reciever_mob");
                String sender_alternate_email = rst.getString("sender_alternate_email");
                String sender_alternate_mob = rst.getString("sender_alternate_mob");
                String description = rst.getString("description");

                pstlast = connection.prepareStatement(updatequery);
                int rowsAffectedss = pstlast.executeUpdate();
                if (rowsAffectedss > 0) {

                    PreparedStatement psts = connection.prepareStatement(addquery, Statement.RETURN_GENERATED_KEYS);
                    psts.setInt(1, enquiry_table_id);
                    psts.setInt(2, enquiry_source_table_id);
                    psts.setInt(3, marketing_vertical_id);
                    psts.setInt(4, enquiry_no);
                    psts.setString(5, bean.getSender_name());
                    psts.setString(6, bean.getSender_mob());
                    psts.setString(7, bean.getEnquiry_address());
                    psts.setString(8, bean.getEnquiry_message());
                    psts.setString(9, "Y");
                    psts.setInt(10, revision_no + 1);
                    psts.setString(11, enquiry_date_time);
                    psts.setString(12, "Manual");
                    psts.setString(13, bean.getSender_email());
                    psts.setString(14, sender_company_name);
                    psts.setString(15, product_name);
                    psts.setString(16, city);
                    psts.setString(17, state);
                    psts.setString(18, "");
                    psts.setString(19, "");
                    psts.setString(20, "");
                    psts.setString(21, bean.getSender_alternate_email());
                    psts.setString(22, bean.getSender_alternate_mob());
                    psts.setString(23, "");
                    rowsAffected = psts.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = psts.getGeneratedKeys();
                        added = 1;
                    } else {
                        added = 0;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnquiryModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    public static String timeAgo(Date currentDate, Date pastDate) {
        long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
        long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
        long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
        long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
        long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        long msExpired = currentDate.getTime() - pastDate.getTime();
        //Second or Seconds ago calculation
        if (msExpired < milliSecPerMinute) {
            if (Math.round(msExpired / 1000) == 1) {
                return String.valueOf(Math.round(msExpired / 1000)) + " second ago";
            } else {
                return String.valueOf(Math.round(msExpired / 1000) + " seconds ago");
            }
        } //Minute or Minutes ago calculation
        else if (msExpired < milliSecPerHour) {
            if (Math.round(msExpired / milliSecPerMinute) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago";
            }
        } //Hour or Hours ago calculation
        else if (msExpired < milliSecPerDay) {
            if (Math.round(msExpired / milliSecPerHour) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago";
            }
        } //Day or Days ago calculation
        else if (msExpired < milliSecPerMonth) {
            if (Math.round(msExpired / milliSecPerDay) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago";
            }
        } //Month or Months ago calculation
        else if (msExpired < milliSecPerYear) {
            if (Math.round(msExpired / milliSecPerMonth) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago";
            }
        } //Year or Years ago calculation
        else {
            if (Math.round(msExpired / milliSecPerYear) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago";
            }
        }
    }

    public String addAsignedToExecutiveInBulk(String assign_by, String assign_to, String updated_date_time, String created_by,
            String[] enquiry_table_id, String active, String enquiry_status_id) {
        String added = "";
        PreparedStatement pst;
        PreparedStatement psts;
        PreparedStatement updatepsts;

        int rowsAffected = 0;
        int updaterowsAffected = 0;
        int length = enquiry_table_id.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {

                String getrevision = "select max(revision_no),enquiry_assigned_table_id ,description, remark from enquiry_assigned_table where enquiry_table_id='" + enquiry_table_id[i] + "'";

                try {
                    pst = connection.prepareStatement(getrevision);
                    ResultSet rset = pst.executeQuery();
                    if (rset.next()) {
                        String enquiry_assigned_table_id = rset.getString("enquiry_assigned_table_id");
                        int revision_no = rset.getInt("max(revision_no)");
                        String description = rset.getString("description");
                        String remark = rset.getString("remark");
                        String updateAssignedQuery = "update  enquiry_assigned_table set active='N' where enquiry_table_id='" + enquiry_table_id[i] + "' and enquiry_assigned_table_id='" + enquiry_assigned_table_id + "'";
                        updatepsts = connection.prepareStatement(updateAssignedQuery);
                        updaterowsAffected = updatepsts.executeUpdate();
                        if (updaterowsAffected > 0) {
                            String insertAssignedQuery = "insert into enquiry_assigned_table(enquiry_assigned_table_id,enquiry_table_id,enquiry_status_id,"
                                    + "active,revision_no,created_by,description,remark,updated_date_time,assigned_by,assigned_to)values(?,?,?,?,?,?,?,?,?,?,?);";
                            psts = connection.prepareStatement(insertAssignedQuery);
                            psts.setString(1, enquiry_assigned_table_id);
                            psts.setString(2, enquiry_table_id[i]);
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

                            } else {
                                added = "Cannot update the record, some error.";
                            }

                        } else {
                            added = "Cannot update the record, some error.";

                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(EnquiryDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

    public ArrayList<EnquiryBean> getAllSubject() {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select * from subject";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();
            while (stmt.next()) {
                EnquiryBean bean = new EnquiryBean();
                bean.setSubject_id(stmt.getInt("subject_id"));
                bean.setSubject_name(stmt.getString("subject_name"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return list;
    }

    public JSONArray getAllDocumentIds() {
        JSONArray rowData = new JSONArray();

        String query = "select dtd.documents_id\n"
                + "FROM doc_type AS dt\n"
                + "JOIN doc_type_documents_mapping AS dtd ON dtd.doc_type_id = dt.doc_type_id\n"
                + "WHERE dt.active = 'Y'\n";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();

            while (stmt.next()) {
                JSONObject obj = new JSONObject();
                obj.put("documents_id", stmt.getInt("documents_id"));
                obj.put("project_id", "6");
                rowData.put(obj);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return rowData;
    }

    public String callAPI(JSONArray documentlistIds) {
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

    public String getFileNameFromField(String documents_id) {
//        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();

        String query = "select file_name from doc_type_documents_mapping where documents_id='" + documents_id + "' and active='Y' ";

        String fileName = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();
            while (stmt.next()) {
//                EnquiryBean bean = new EnquiryBean();
                fileName = stmt.getString("file_name");
//                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return fileName;
    }

    public String setImportant(String setImportantId) {
        String message1 = "";
        int rowsAffected = 0;
        char imp = 0;
        String query = "select important from enquiry_table where enquiry_table_id='" + setImportantId + "' and active='Y' ";

//        String query1 = "UPDATE enquiry_table SET important = 'Y'  WHERE enquiry_table_id ='" + setImportantId + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet stmt = pstmt.executeQuery();
            while (stmt.next()) {
                String important = stmt.getString("important");
                if (important.equals("N")) {
                    imp = 'Y';
                } else {
                    imp = 'N';
                }
            }
            String query1 = "UPDATE enquiry_table SET important = '" + imp + "'  WHERE enquiry_table_id ='" + setImportantId + "' ";
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
            rowsAffected = pstmt1.executeUpdate();
            if (rowsAffected > 0) {
                String query3 = "select important from enquiry_table where enquiry_table_id='" + setImportantId + "' and active='Y' ";

                PreparedStatement pstmt2 = connection.prepareStatement(query3);
                ResultSet stmt2 = pstmt2.executeQuery();
                while (stmt2.next()) {
                    String important = stmt2.getString("important");
                    if (important.equals("N")) {
                        message1 = "Important unmarked";
                    } else {
                        message1 = "Important marked";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
//        return rowsAffected;
        return message1;
    }

    public ArrayList<EnquiryBean> getImportantEnquiries(String source_search_id, String status_search_id, ServletContext ctx) {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        String query = "SELECT *\n"
                + "FROM enquiry_table AS et\n"
                + "JOIN enquiry_assigned_table AS eat ON eat.enquiry_table_id = et.enquiry_table_id\n"
                + "JOIN enquiry_status AS es ON es.enquiry_status_id = eat.enquiry_status_id "
                + "JOIN enquiry_source_table AS est ON est.enquiry_source_table_id=et.enquiry_source_table_id "
                + "JOIN marketing_vertical AS mv ON mv.marketing_vertical_id=et.marketing_vertical_id\n"
                + "WHERE et.active = 'Y' and et.important = 'Y'\n";

        if (!source_search_id.equals("") && source_search_id != null) {
            query += "and et.enquiry_source_table_id='" + source_search_id + "' ";
        }
        if (!status_search_id.equals("") && status_search_id != null) {
            query += "and eat.enquiry_status_id='" + status_search_id + "'";
        }
        query += " AND (eat.enquiry_table_id, eat.revision_no) IN (\n"
                + "    SELECT eat.enquiry_table_id, MAX(eat.revision_no)\n"
                + "    FROM enquiry_assigned_table AS eat\n"
                + "    GROUP BY eat.enquiry_table_id\n"
                + ")\n"
                + "ORDER BY STR_TO_DATE(et.enquiry_date_time, '%d-%b-%Y %h:%i:%s %p') DESC;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                EnquiryBean bean = new EnquiryBean();
                int enquiry_table_id = rset.getInt("enquiry_table_id");
                bean.setEnquiry_table_id(rset.getInt("enquiry_table_id"));
                bean.setSender_name(rset.getString("sender_name"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setSender_mob(rset.getString("sender_mob"));
                bean.setSender_email(rset.getString("sender_email"));
                bean.setProduct_name(rset.getString("product_name"));
                bean.setEnquiry_source(rset.getString("enquiry_source"));
                bean.setMarketing_vertical_name(rset.getString("marketing_vertical_name"));
                bean.setEnquiry_city(rset.getString("enquiry_city"));
                bean.setEnquiry_state(rset.getString("enquiry_state"));
                bean.setEnquiry_date_time(rset.getString("enquiry_date_time"));
                bean.setImportantEnquiry(rset.getString("important"));

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
                        DashboardModel dashmodel = new DashboardModel();

                        // Optimize on 28-08-2023
                        bean.setAssigned_to(getPersonNameUsingId(assigned_to, ctx));
                        // Optimize on 28-08-2023

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

    public String callApiForSaveReminderData(JSONObject objsendReminder) {

        String result = "";
        try {
            String sendChatMessage = resourceBundle.getString("COMM_MODULE_SENDCHATMESSAGES_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/sendMail");
            URL url = new URL(sendChatMessage);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(objsendReminder);
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

    public ArrayList<EnquiryBean> getAllRemindersDetails(JSONObject reminderObj) throws ParseException {
        String result = "";
        try {
            String getReminderDetails = resourceBundle.getString("COMM_MODULE_GETCHATMESSAGES_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/sendMail");
            URL url = new URL(getReminderDetails);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(reminderObj);
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

        ArrayList<EnquiryBean> arraylist = callFun(result);
        return arraylist;

    }

    private ArrayList<EnquiryBean> callFun(String result) throws ParseException {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        try {
            JSONArray DocJArr = new JSONArray(result);
            for (int i = 0; i < DocJArr.length(); i++) {
                EnquiryBean bean = new EnquiryBean();
                JSONObject DocObj = DocJArr.getJSONObject(i);

                //change Date  2023-08-15 --> 15-Aug-2023 
                SimpleDateFormat inputFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                Date date = inputFormatDate.parse(DocObj.getString("date"));
                SimpleDateFormat outputFormatDate = new SimpleDateFormat("dd-MMM-yyyy");
                String outputDateStr = outputFormatDate.format(date);
                //change Time  20:15 --> 8:15 Am/Pm 
                SimpleDateFormat inputFormatTime = new SimpleDateFormat("HH:mm");
                Date time = inputFormatTime.parse(DocObj.getString("time"));
                SimpleDateFormat outputFormatTime = new SimpleDateFormat("h:mm a");
                String outputTimeStr = outputFormatTime.format(time);
                //////////
                //if condition check expired date 
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                LocalDate specifiedDate = LocalDate.parse(outputDateStr, formatter);
                LocalDate currentDate = LocalDate.now();
                int comparisonResult = currentDate.compareTo(specifiedDate);

//                if (comparisonResult <= 0) {
                bean.setDate(outputDateStr);
                bean.setTime(outputTimeStr);
                bean.setChat_msg_table_id(DocObj.getString("chat_msg_table_id"));
                String key_person_id = DocObj.getString("from_id");

                //get EnquiryDetails and set
                JSONObject enquiryDetails = getEnquiryDetails(DocObj.getString("enquiry_table_id"));
                if (!enquiryDetails.isEmpty()) {
                    bean.setSender_name(enquiryDetails.getString("sender_name"));
                    bean.setSender_mob(enquiryDetails.getString("sender_mob"));
                    bean.setEnquiry_address(enquiryDetails.getString("sender_address"));
                    list.add(bean);
                }
//                }

            }

        } catch (Exception ex) {
            Logger.getLogger(EnquiryModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<EnquiryBean> getAllRemindersforMail(JSONObject reminderObj) throws ParseException {
        String result = "";
        ArrayList<EnquiryBean> arraylist = new ArrayList<>();
        try {
            String getReminderDetails = resourceBundle.getString("COMM_MODULE_GETCHATMESSAGES_API_URL");
//            URL url = new URL("http://localhost:8080/CModule/sendMail");
            URL url = new URL(getReminderDetails);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("datatype", "POST");
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(reminderObj);
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            StringBuffer response = new StringBuffer();
            while ((l = br.readLine()) != null) {
                response.append(l);
            }
            br.close();
            result = response.toString();
            if (result != "" || result != null) {
                arraylist = callReminderFun(result);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.model.UploadDocumentsModel.callApi()" + e);
        }

        return arraylist;

    }

    private ArrayList<EnquiryBean> callReminderFun(String result) throws ParseException {
        ArrayList<EnquiryBean> list = new ArrayList<EnquiryBean>();
        try {
            JSONArray DocJArr = new JSONArray(result);
            for (int i = 0; i < DocJArr.length(); i++) {
                EnquiryBean bean = new EnquiryBean();
                JSONObject DocObj = DocJArr.getJSONObject(i);

                //change Date  2023-08-15 --> 15-Aug-2023 
//                SimpleDateFormat inputFormatDate = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = inputFormatDate.parse(DocObj.getString("date"));
//                SimpleDateFormat outputFormatDate = new SimpleDateFormat("dd-MMM-yyyy");
//                String outputDateStr = outputFormatDate.format(date);
//                //change Time  20:15 --> 8:15 Am/Pm 
//                SimpleDateFormat inputFormatTime = new SimpleDateFormat("HH:mm");
//                Date time = inputFormatTime.parse(DocObj.getString("time"));
//                SimpleDateFormat outputFormatTime = new SimpleDateFormat("h:mm a");
//                String outputTimeStr = outputFormatTime.format(time);
//                //////////
//                //if condition check expired date 
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//                LocalDate specifiedDate = LocalDate.parse(outputDateStr, formatter);
//                LocalDate currentDate = LocalDate.now();
//                int comparisonResult = currentDate.compareTo(specifiedDate);
//                if (comparisonResult <= 0) {
                bean.setDate(DocObj.getString("date"));
                bean.setTime(DocObj.getString("time"));
                bean.setChat_msg_table_id(DocObj.getString("chat_msg_table_id"));
                String key_person_id = DocObj.getString("from_id");
                bean.setKey_person_id(key_person_id);

                //get EnquiryDetails and set
                JSONObject enquiryDetails = getEnquiryDetails1(DocObj.getString("enquiry_table_id"));

                if (enquiryDetails.length() > 0) {

                    bean.setSender_name(enquiryDetails.getString("sender_name"));
                    bean.setSender_mob(enquiryDetails.getString("sender_mob"));
                    bean.setEnquiry_address(enquiryDetails.getString("sender_address"));
                    bean.setSender_email(enquiryDetails.getString("sender_email"));
                    bean.setProduct_name(enquiryDetails.getString("product_name"));
                    list.add(bean);
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(EnquiryModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private JSONObject getEnquiryDetails(String string_enquiry_table_id) {
        int enquiry_table_id = Integer.parseInt(string_enquiry_table_id);
        JSONObject obj = new JSONObject();
        String query = "select * from enquiry_table where enquiry_table_id='" + enquiry_table_id + "' and active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                obj.put("sender_name", rset.getString("sender_name"));
                obj.put("sender_mob", rset.getString("sender_mob"));
                obj.put("sender_address", rset.getString("enquiry_address"));
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return obj;
    }

    private JSONObject getEnquiryDetails1(String string_enquiry_table_id) {
        int enquiry_table_id = Integer.parseInt(string_enquiry_table_id);
        JSONObject obj = new JSONObject();
        String query = "select * from enquiry_table where enquiry_table_id='" + enquiry_table_id + "' and active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                obj.put("sender_name", rset.getString("sender_name"));
                obj.put("sender_mob", rset.getString("sender_mob"));
                obj.put("sender_address", rset.getString("enquiry_address"));
                obj.put("sender_email", rset.getString("sender_email"));
                obj.put("product_name", rset.getString("product_name"));
            }
        } catch (Exception e) {
            System.out.println("com.apogee.EnquiryModel.getAllSources()" + e);
        }
        return obj;
    }

    public String sendData(String loggedUser, String logged_key_person_id, String enquiry_source,
            String status, String designation_search_id, ServletContext ctx, String role) {

        String result = "";
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        org.json.simple.JSONObject obj1 = new org.json.simple.JSONObject();
        org.codehaus.jettison.json.JSONArray arr = new org.codehaus.jettison.json.JSONArray();
        EnquiryModel model = new EnquiryModel();
        List<EnquiryBean> list;
        try {
            if (loggedUser.equals("Sales Executive")) {

                list = model.getAllAssignedEnquiries(logged_key_person_id, enquiry_source, status, designation_search_id, ctx);
            } else {
                list = model.getAllEnquiries(enquiry_source, status, designation_search_id, ctx, role);
            }
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String sender_name = list.get(i).getSender_name();
                    String sender_mob = list.get(i).getSender_mob();
                    String sender_email = list.get(i).getSender_email();
                    String product_name = list.get(i).getProduct_name();
                    String city = list.get(i).getEnquiry_city();
                    String state = list.get(i).getEnquiry_state();
                    String enq_status = list.get(i).getStatus();
                    String assigned_to = list.get(i).getAssigned_to();
                    if (assigned_to == null) {
                        assigned_to = "";
                    }
                    String date_time = list.get(i).getEnquiry_date_time();
                    String remark = list.get(i).getAssigned_remark();
                    String enquiry_message = list.get(i).getEnquiry_message();
                    obj.put("sender_name", sender_name);
                    obj.put("city", city);
                    obj.put("state", state);
                    obj.put("mob", sender_mob);
                    obj.put("email", sender_email);
                    obj.put("date", date_time);
                    obj.put("product_name", product_name);
                    obj.put("assigne_to", assigned_to);
                    obj.put("remark", remark);
                    obj.put("status", enq_status);
                    arr.put(obj);
                }
            }
            obj1.put("report_name", "Sales Report");
            obj1.put("data", arr);

            result = sendAllData(obj1);
        } catch (Exception e) {
            System.out.println("com.apogee.report.ReportsModel.showData()" + e);
        }
        return result;
    }

    public String sendAllData(org.json.simple.JSONObject obj) {
        String result = "";
        try {
            URL url = new URL("http://120.138.10.146:8080/ReportModule/webAPI/report/getAllDataForGeneratingReport");
//            URL url = new URL("http://192.168.1.12:8088/ReportModule/webAPI/report/getAllDataForGeneratingReport");

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
            System.out.println("com.apogee.model.EnquiryModel.sendAllData()" + result);
        } catch (Exception e) {
            System.out.println("com.apogee.report.ReportsModel.sendAllData()" + e);
        }
        return result;
    }

    public String saveDataToXml(String jsonData, ServletContext ctx) {
        String str = "";
        try {
            // Convert JSON to XML
            ObjectMapper jsonMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            try {
                JsonNode jsonNode = jsonMapper.readTree(jsonData);
                String xmlData = xmlMapper.writeValueAsString(jsonNode);

                String folderPath = ctx.getRealPath("/assets/");

                String fileName = "allkeyPersonData.xml";
                File xmlFile = new File(folderPath, fileName);
                xmlMapper.writeValue(xmlFile, jsonNode);
                System.out.println("JSON to XML conversion successful. XML file saved as 'output.xml'");
                str = "saved";
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("com.apogee.model.EnquiryModel.saveDataToXml(): " + e);
        }
        return str;
    }

    public static String getPersonNameUsingId(String keyPersonIdValue, ServletContext ctx) {
        String str = "";
        try {
            String folderPath = ctx.getRealPath("/assets/allkeyPersonData.xml");
            File xmlFile = new File(folderPath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList personNodes = doc.getElementsByTagName("allKeyPersonDetails");

            for (int i = 0; i < personNodes.getLength(); i++) {
                Element personElement = (Element) personNodes.item(i);
                String idValue = personElement.getElementsByTagName("key_person_id").item(0).getTextContent();

                if (idValue.equals(keyPersonIdValue)) {
                    String keyPersonName = personElement.getElementsByTagName("key_person_name").item(0).getTextContent();
                    str = keyPersonName;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
