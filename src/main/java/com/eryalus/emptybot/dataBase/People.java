/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 *
 * @author eryalus
 */
public class People {

    public static final Integer NORMAL_MODE = Basics.NORMAL_MODE, HIDED_MODE = Basics.HIDED_MODE;

    public static boolean isAdmin(Chat chat, Connection conn) {
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("select person_id from admin where person_id=" + chat.getId());
            if (result.next()) {
                return true;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public static Integer getEstado(Chat chat, Connection conn) {
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("select state from person where id=" + chat.getId());
            if (result.next()) {
                return result.getInt("state");
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public static boolean addPerson(Chat chat, Connection conn) {
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery("select id from  person where id=" + chat.getId());
            if (set.next()) {
                //existe
                PreparedStatement ps = conn.prepareStatement("UPDATE person set name=(?) , surname=(?) , username=(?) where id=(?)");
                ps.setString(1, chat.getFirstName());
                ps.setString(2, chat.getLastName());
                ps.setString(3, chat.getUserName());
                ps.setLong(4, chat.getId());
                return ps.execute();
            } else {
                //no existe
                PreparedStatement ps = conn.prepareStatement("INSERT INTO person (id,name,surname,username) values ((?),(?),(?),(?))");
                ps.setLong(1, chat.getId());
                ps.setString(2, chat.getFirstName());
                ps.setString(3, chat.getLastName());
                ps.setString(4, chat.getUserName());
                return ps.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Basics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
