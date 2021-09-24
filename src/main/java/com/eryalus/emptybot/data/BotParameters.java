/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.data;

/**
 *
 * @author eryalus
 */
public class BotParameters {

    private int DB_port = 3306;
    private String DB_user = "", DB_password = "";
    private String Bot_username = "", Bot_token = "";

    public int getDB_port() {
        return DB_port;
    }

    public void setDB_port(int DB_port) {
        this.DB_port = DB_port;
    }

    public String getDB_user() {
        return DB_user;
    }

    public void setDB_user(String DB_user) {
        this.DB_user = DB_user;
    }

    public String getDB_password() {
        return DB_password;
    }

    public void setDB_password(String DB_password) {
        this.DB_password = DB_password;
    }

    public String getBot_username() {
        return Bot_username;
    }

    public void setBot_username(String Bot_username) {
        this.Bot_username = Bot_username;
    }

    public String getBot_token() {
        return Bot_token;
    }

    public void setBot_token(String Bot_token) {
        this.Bot_token = Bot_token;
    }

}
