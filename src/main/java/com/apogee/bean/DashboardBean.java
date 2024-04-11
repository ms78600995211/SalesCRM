/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.bean;

/**
 *
 * @author admin
 */
public class DashboardBean {

    private String key_person_id;
    private String salutation;
    private String key_person_name;
    private String designation_id;
    private String designation_name;
    private int notansweringcount;
    private int followupcount;
    private int democount;
    private int convertedcount;
    /**
     * @return the key_person_id
     */
    public String getKey_person_id() {
        return key_person_id;
    }

    /**
     * @param key_person_id the key_person_id to set
     */
    public void setKey_person_id(String key_person_id) {
        this.key_person_id = key_person_id;
    }

    /**
     * @return the salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * @param salutation the salutation to set
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the key_person_name
     */
    public String getKey_person_name() {
        return key_person_name;
    }

    /**
     * @param key_person_name the key_person_name to set
     */
    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    /**
     * @return the designation_id
     */
    public String getDesignation_id() {
        return designation_id;
    }

    /**
     * @param designation_id the designation_id to set
     */
    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    /**
     * @return the designation_name
     */
    public String getDesignation_name() {
        return designation_name;
    }

    /**
     * @param designation_name the designation_name to set
     */
    public void setDesignation_name(String designation_name) {
        this.designation_name = designation_name;
    }

    /**
     * @return the notansweringcount
     */
    public int getNotansweringcount() {
        return notansweringcount;
    }

    /**
     * @param notansweringcount the notansweringcount to set
     */
    public void setNotansweringcount(int notansweringcount) {
        this.notansweringcount = notansweringcount;
    }

    /**
     * @return the followupcount
     */
    public int getFollowupcount() {
        return followupcount;
    }

    /**
     * @param followupcount the followupcount to set
     */
    public void setFollowupcount(int followupcount) {
        this.followupcount = followupcount;
    }

    /**
     * @return the democount
     */
    public int getDemocount() {
        return democount;
    }

    /**
     * @param democount the democount to set
     */
    public void setDemocount(int democount) {
        this.democount = democount;
    }

    /**
     * @return the convertedcount
     */
    public int getConvertedcount() {
        return convertedcount;
    }

    /**
     * @param convertedcount the convertedcount to set
     */
    public void setConvertedcount(int convertedcount) {
        this.convertedcount = convertedcount;
    }
}
