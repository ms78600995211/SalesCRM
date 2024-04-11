/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author admin
 */
public class LoginModel {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");

    private static Connection connection;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.jpss.registartioncummodel.LoginModel.setConnection(): " + e);
        }
    }

    public String conformLoginDataFromLoginModule(String a, String b, String c) throws Exception {

        String baseiname = "";

//        String url = "http://120.138.10.146:8080/login_module/api/getLoginPersonData";
        String getLoginPersonData = resourceBundle.getString("LOGIN_MODULE_GETLOGINPERSONDETAILS_API_URL");
        String url = getLoginPersonData;
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");

        String urlParameters = ("{\"username\": \"" + a + "\",\"password\": \"" + b + "\",\"project_name\": \"" + c + "\"}");

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

    public String getKeypersonDataFromOrganisation(String a) throws Exception {

        String baseiname = "";
//        String url = "http://120.138.10.146:8080/OrganisationModule/webAPI/service/getDesignationByPersonId";
        String getDesignationByPersonId = resourceBundle.getString("ORG_MODULE_GETDESIGNATIONBYPERSONID_API_URL");
        String url = getDesignationByPersonId;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

}
