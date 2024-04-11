package com.apogee.controller;

import com.apogee.model.IndiaMartServicesModel;
import com.apogee.bean.EnquiryBean;
import com.apogee.DBConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author admin
 */
public class IndiaMartEnquiriesService implements ServletContextListener {

    @Context
    ServletContext servletContext;
    int scheduler_count = 0;
    String path = "";
    Connection connection = null;

    class VodTimerTask extends TimerTask {

        public String getGNSSEnquiriesFromIndiaMart() throws MalformedURLException, IOException, JSONException {
            String status = "some error";
            IndiaMartServicesModel wsm = new IndiaMartServicesModel();
            try {
                wsm.setConnection(DBConnection.getConnection(servletContext));
                // status = wsm.saveDeviceRegs(dataString);
            } catch (Exception e) {
                System.out.println("error in WebServicesController.saveDeviceRegRecord()- " + e);
            }
            String result = "";
            JSONObject obj = new JSONObject();
            try {
                String start_date = "";
                String end_date = "";
                Date date = new Date();
                Date yesterday_date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                yesterday_date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
                start_date = sdf.format(yesterday_date);
                end_date = sdf.format(date);
                String webPage = "https://mapi.indiamart.com/wservce/crm/crmListing/v2/?glusr_crm_key=mR20Eb1u5njHSfep73OP7l+Hp1TNmTNj&start_time=" + start_date + "&end_time=" + end_date;
                URL url = new URL(webPage);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
                urlConnection.setRequestProperty("Cookie", "foo=bar");
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = response.toString();
                }
                System.out.println("result===" + result);
                JSONObject obj1 = new JSONObject(result);
                JSONArray jsonArr = new JSONArray(obj1.getString("RESPONSE"));
                for (int i = jsonArr.length() - 1; i >= 0; i--) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    int enquiry_table_id = 0;
                    EnquiryBean bean = new EnquiryBean();
                    bean.setEnquiry_table_id(enquiry_table_id);
                    bean.setMarketing_vertical_name("GNSS");
                    bean.setEnquiry_no(jsonObj.get("UNIQUE_QUERY_ID").toString());
                    bean.setSender_name(jsonObj.get("SENDER_NAME").toString());
                    bean.setSender_email(jsonObj.get("SENDER_EMAIL").toString());
                    bean.setSender_alternate_email(jsonObj.get("SENDER_EMAIL_ALT").toString());
                    bean.setSender_mob(jsonObj.get("SENDER_MOBILE").toString());
                    bean.setSender_alternate_mob(jsonObj.get("SENDER_MOBILE_ALT").toString());
                    bean.setSender_company_name(jsonObj.get("SENDER_COMPANY").toString());
                    bean.setEnquiry_address(jsonObj.get("SENDER_ADDRESS").toString());
                    bean.setEnquiry_city(jsonObj.get("SENDER_CITY").toString());
                    bean.setEnquiry_state(jsonObj.get("SENDER_STATE").toString());
                    bean.setCountry(jsonObj.get("SENDER_COUNTRY_ISO").toString());
                    bean.setEnquiry_message(jsonObj.get("QUERY_MESSAGE").toString());
                    String query_time = jsonObj.get("QUERY_TIME").toString();
//                    String arr[] = query_time.split(" ");
//                    String arr2[] = arr[0].split("-");
//                    String month = arr2[1];
//                    String arr3[] = arr[1].split(":");
//                    int hour = Integer.parseInt(arr3[0]);
//                    String minute = arr3[1];
//                    String second = arr3[2];
//                    String m = "";
//                    String am_pm = "";
//                    String hours = "";
//
//                    if (hour <= 12) {
//                        hours = String.valueOf(hour);
//                    }
//                    if (hour == 13) {
//                        hours = "01";
//                    }
//                    if (hour == 14) {
//                        hours = "02";
//                    }
//                    if (hour == 15) {
//                        hours = "03";
//                    }
//                    if (hour == 16) {
//                        hours = "04";
//                    }
//                    if (hour == 17) {
//                        hours = "05";
//                    }
//                    if (hour == 18) {
//                        hours = "06";
//                    }
//                    if (hour == 19) {
//                        hours = "07";
//                    }
//                    if (hour == 20) {
//                        hours = "08";
//                    }
//                    if (hour == 21) {
//                        hours = "09";
//                    }
//                    if (hour == 22) {
//                        hours = "10";
//                    }
//                    if (hour == 23) {
//                        hours = "11";
//                    }
//                    if (hour == 24) {
//                        hours = "12";
//                    }
//
//                    if (hour <= 12) {
//                        am_pm = "AM";
//                    } else {
//                        am_pm = "PM";
//                    }
//                    if (month.equals("01")) {
//                        m = "Jan";
//                    }
//                    if (month.equals("02")) {
//                        m = "Feb";
//
//                    }
//                    if (month.equals("03")) {
//                        m = "Mar";
//
//                    }
//                    if (month.equals("04")) {
//                        m = "Apr";
//
//                    }
//                    if (month.equals("05")) {
//                        m = "May";
//
//                    }
//                    if (month.equals("06")) {
//                        m = "Jun";
//
//                    }
//                    if (month.equals("07")) {
//                        m = "Jul";
//
//                    }
//                    if (month.equals("08")) {
//                        m = "Aug";
//
//                    }
//                    if (month.equals("09")) {
//                        m = "Sep";
//
//                    }
//                    if (month.equals("10")) {
//                        m = "Oct";
//
//                    }
//                    if (month.equals("11")) {
//                        m = "Nov";
//
//                    }
//                    if (month.equals("12")) {
//                        m = "Dec";
//                    }
//                    String new_date = arr2[2] + "-" + m + "-" + arr2[0] + " " + hours + ":" + minute + ":" + second + " " + am_pm;
//                    bean.setDate_time(new_date);
                    bean.setDate_time(query_time);
                    bean.setProduct_name(jsonObj.get("QUERY_PRODUCT_NAME").toString());

                    if (enquiry_table_id == 0) {
                        try {
                            wsm.insertEnquiries(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(IndiaMartEnquiriesService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            wsm.closeConnection();
            return result;

        }

        @Override
        public void run() {
            try {
                getGNSSEnquiriesFromIndiaMart();
            } catch (IOException ex) {
                Logger.getLogger(IndiaMartEnquiriesService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(IndiaMartEnquiriesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        TimerTask vodTimer = new VodTimerTask();
        Timer timer = new Timer();

        Date date = new Date();
        // timer.schedule(vodTimer, 900000);
        timer.schedule(vodTimer, 1 * 1000, (60 * 60 * 1000));
//          timer.schedule(vodTimer, 20 * 1000, (60 * 60 * 1000));          

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method will be called when the application is shutting down
        // You can use this method to perform any cleanup tasks if needed

    }

}
