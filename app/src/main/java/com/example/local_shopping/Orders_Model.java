package com.example.local_shopping;

import com.google.gson.annotations.SerializedName;

public class Orders_Model {
    String product_id;
    String product_name;
    String price;
    @SerializedName("number_of_product")
    String product_number;
    String imagepath;
    int order_status;
    String issue_date;
    String delivering_date;
    String client_name;
    String contact_no;
    String user_name;
    @SerializedName("phn/gmail")
    String phn_gmail;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getDelivering_date() {
        return delivering_date;
    }

    public void setDelivering_date(String delivering_date) {
        this.delivering_date = delivering_date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhn_gmail() {
        return phn_gmail;
    }

    public void setPhn_gmail(String phn_gmail) {
        this.phn_gmail = phn_gmail;
    }
}
