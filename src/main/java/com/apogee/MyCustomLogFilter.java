/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee;

/**
 *
 * @author Vikrant
 */
import com.apogee.UrlPrivileges;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONObject;

public class MyCustomLogFilter implements Filter {

    private FilterConfig filterConfig = null;
    private Connection connection;
    private String isSelect;
    private boolean isRedirection = false;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        try {
            String path = ((HttpServletRequest) request).getRequestURI();
            if (!path.startsWith("/DBPrivileges/webAPI/dbsecure/") && !path.startsWith("/SalesCrm/UserNoPrivilegeController")
                    && !path.startsWith("/SalesCrm/assets/") && !path.startsWith("/SalesCrm/calendar/") && !path.startsWith("/SalesCrm/layout/")
                    && !path.endsWith("/UserNoPrivilegeController") && !path.endsWith("/SalesCrm/getGNSSEnquiriesFromIndiaMart")
                    && !path.endsWith("/SalesCrm/home")
                    && !path.endsWith("/SalesCrm/") && !path.endsWith("/LoginController")
                    //////akshay SalesCrm 
                    && !path.startsWith("/SalesCrm/plugins/") && !path.startsWith("/SalesCrm/images/")
                    && !path.startsWith("/SalesCrm/dist/") && !path.startsWith("/SalesCrm/dist/css/")
                    && !path.startsWith("/SalesCrm/JQuery.UI/") && !path.startsWith("/SalesCrm/dist/img/")
                    && !path.startsWith("/SalesCrm/DashboardController")
                    && !path.startsWith("/SalesCrm/api/")) {
                System.err.println("---------------------------------- Log Filter ---------------------------------------------");
                ServletContext ctx = getFilterConfig().getServletContext();
                int userId = 0;
                String url = request.getServletPath().replace("/", "");
                request.getQueryString();
                request.getRequestURL();
                isSelect = "Y";
                isRedirection = false;
                connection = DBConnection.getConnection(ctx);
                if (session != null) {
                    if (session.getAttribute("role") != null) {
                        JSONObject jsonObject = getPrivileges2(url, (String) session.getAttribute("role"), "SalesCrm");
                        System.err.println("json data from api: " + jsonObject.get("result").toString());
                        if (jsonObject.get("result").toString().equalsIgnoreCase("Success")) {
                            request.setAttribute("isSelect", jsonObject.get("Select"));
                            request.setAttribute("isInsert", jsonObject.get("Insert"));
                            request.setAttribute("isDelete", jsonObject.get("Delete"));
                            request.setAttribute("isUpdate", jsonObject.get("Update"));
                            request.setAttribute("isFull", jsonObject.get("Full"));
                            if (jsonObject.get("Full").toString().equalsIgnoreCase("N")) {
                                isRedirection = true;
                            }
                        } else {
                            isRedirection = true;
                        }
                    } else {
                        response.sendRedirect("/SalesCrm/LoginController");
                        return;
                    }
                } else {
                    response.sendRedirect("/SalesCrm/LoginController");
                    return;
                }
                DBConnection.closeConncetion(connection);
                if (isRedirection) {
                    response.sendRedirect("UserNoPrivilegeController");
                    return;
                }
            }
            chain.doFilter(req, res);
        } catch (Exception e) {
            session.invalidate();
            response.sendRedirect("/SalesCrm/LoginController");
            System.out.println("Error LogFiletr" + e);
            return;
        }
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    private JSONObject getPrivileges2(String url_name, String role_name, String project_name) throws IOException, InterruptedException {
        JSONObject obj = new JSONObject();
        try {
            URL url = new URL("http://120.138.10.251:8080/DBPrivileges/webAPI/dbsecure/getPrivileges");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonData = "{\"role_name\":\"" + role_name + "\",\"url\":\"SalesCrm/" + url_name + "\",\"project_name\":\"" + project_name + "\"}";
//            System.err.println("JSON DAta sent to API: " + jsonData);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonData.getBytes());
            outputStream.flush();

            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
//            System.out.println("Response in getPrivileges2(): " + response.toString());
            obj = new JSONObject(response.toString());
//            System.err.println("obj length: " + obj.length());

        } catch (Exception e) {

        }

        return obj;
    }
}
