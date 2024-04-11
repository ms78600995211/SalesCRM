/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.controller;

import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.apogee.DBConnection;
import com.apogee.model.WebServicesModel;
import java.io.FileOutputStream;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Base64;

/**
 *
 * @author akshay
 */
@RestController
public class PrivController {

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/home")
    public String getData(JSONObject inputJsonObj) {
        return "Hello There!";
    }

    @PostMapping("/addWebsiteEnquiry")
    public String addWebsiteEnquiry(@RequestBody String str) throws JSONException {

        String status = "some error";
        JSONObject inputJsonObj = new JSONObject(str);
        WebServicesModel wsm = new WebServicesModel();
        try {
            wsm.setConnection(DBConnection.getConnection(servletContext));
            status = wsm.addEnquiryFromWebsite(inputJsonObj);

        } catch (Exception e) {
            System.out.println("error in WebServicesController.saveDeviceRegRecord()- " + e);
        }
        wsm.closeConnection();
        return status;
    }

    @PostMapping("/getTextFileForReport")
    public String getTextFileForReport(@RequestBody String str) throws JSONException {
        String status = "some error";
        JSONObject inputJsonObj = new JSONObject(str);
        WebServicesModel wsm = new WebServicesModel();
        try {
            wsm.setConnection(DBConnection.getConnection(servletContext));
          //  status = wsm.addEnquiryFromWebsite(inputJsonObj);
            String file_name = inputJsonObj.get("file_name").toString();
            String file_bytes = inputJsonObj.get("file_bytes").toString();
            String report_name = inputJsonObj.get("report_name").toString();
            byte[] fileAsBytes = Base64.getDecoder().decode(file_bytes);
            String file = "C:\\ssadvt_repository\\SalesCrm\\Text files\\" + file_name;
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fileAsBytes);
            outputStream.close();
        } catch (Exception e) {
            System.out.println("error in WebServicesController.saveDeviceRegRecord()- " + e);
        }
        wsm.closeConnection();
        return status;
    }

}
