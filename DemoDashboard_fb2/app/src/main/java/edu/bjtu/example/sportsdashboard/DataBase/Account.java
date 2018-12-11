package edu.bjtu.example.sportsdashboard.DataBase;

import cn.bmob.v3.BmobObject;

public class Account extends BmobObject {
    private String user_ID;
    private String user_key;

    public String getName() {
        return user_ID;
    }
    public void setName(String name) {
        this.user_ID = name;
    }
    public String getKey() {
        return user_key;
    }
    public void setKey(String password) {
        this.user_key = password;
    }
}