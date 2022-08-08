package com.example.offilinefirebasedata.Model;

import com.example.offilinefirebasedata.Utils.Constants;

public class Model {

    private int viewType;
    private String name;
    private String email;
    private int userid;
    private String address;
    private int table2Id;

    public Model(String name, String email, int userid) {
        this.name = name;
        this.email = email;
        this.userid = userid;
        viewType = Constants.TABLE1;
    }

    public Model(String name, int table2Id, String address) {
        this.name = name;
        this.address = address;
        this.table2Id = table2Id;
        viewType = Constants.TABLE2;

    }

    public Model() {
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTable2Id() {
        return table2Id;
    }

    public void setTable2Id(int table2Id) {
        this.table2Id = table2Id;
    }
}
