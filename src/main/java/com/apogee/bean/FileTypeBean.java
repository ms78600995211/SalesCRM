/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.bean;

/**
 *
 * @author lENOVO
 */
public class FileTypeBean {

    private int doc_type_id;
    private String file_type;
    private String documents_id;
    private String file_name_from_field;
    private String created_at;
    private String file_name;
    private String remark;
    private String file_path;

    /**
     * @return the doc_type_id
     */
    public int getDoc_type_id() {
        return doc_type_id;
    }

    /**
     * @param doc_type_id the doc_type_id to set
     */
    public void setDoc_type_id(int doc_type_id) {
        this.doc_type_id = doc_type_id;
    }

    /**
     * @return the file_type
     */
    public String getFile_type() {
        return file_type;
    }

    /**
     * @param file_type the file_type to set
     */
    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    /**
     * @return the documents_id
     */
    public String getDocuments_id() {
        return documents_id;
    }

    /**
     * @param documents_id the documents_id to set
     */
    public void setDocuments_id(String documents_id) {
        this.documents_id = documents_id;
    }

    /**
     * @return the file_name_from_field
     */
    public String getFile_name_from_field() {
        return file_name_from_field;
    }

    /**
     * @param file_name_from_field the file_name_from_field to set
     */
    public void setFile_name_from_field(String file_name_from_field) {
        this.file_name_from_field = file_name_from_field;
    }

    /**
     * @return the created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the file_name
     */
    public String getFile_name() {
        return file_name;
    }

    /**
     * @param file_name the file_name to set
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the file_path
     */
    public String getFile_path() {
        return file_path;
    }

    /**
     * @param file_path the file_path to set
     */
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

}
