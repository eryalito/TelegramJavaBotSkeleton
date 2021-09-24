/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.dataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eryalus
 */
public class Dump {

    public synchronized String getDump(String pass, String user, String host) {
        if (!System.getProperty("os.name").toLowerCase().contains("linux")) {
            return "";
        }
        String output = null;
        try {
            Process p;
            if (pass.equals("")) {
                p = Runtime.getRuntime().exec("mysqldump -h " + host + " -u " + user + " "+Basics.NOMBRE_DB);
            } else {
                p = Runtime.getRuntime().exec("mysqldump -p" + pass + " -h " + host + " -u " + user + " "+Basics.NOMBRE_DB);
            }

            InputStream in = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            br.readLine();
            wait(1);

            String line;
            output = "";
            while ((line = br.readLine()) != null) {
                output += line + "\n";
            }
            in.close();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            Logger.getLogger(Basics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
}
