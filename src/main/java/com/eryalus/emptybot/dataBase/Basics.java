/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.dataBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Utilidades básicas de la base de datos.
 *
 * @author ad_ri
 */
public class Basics {

    protected static final String NOMBRE_DB = "bot_test";
    private static final String IP = "localhost";
    public static final Integer NORMAL_MODE = 0, HIDED_MODE = 1;

    /**
     * Trata de crear la base de datos en caso de no estar creada.
     *
     * @param usuario Usuario de la base de datos
     * @param pass Contraseña de a base de datos
     * @param port Puerto de acceso a la base de datos
     * @return true en caso de terminar la ejecución dejando la base de datos
     * creada o false en caso de haber algún error
     */
    public static boolean crearBaseDatos(String usuario, String pass, String port) {
        try {
            Connection conn = MySQLConnection(usuario, pass, port, "");
            conn.close();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Crea una conexión con la base de datos a nivel de la DB del programa
     *
     * @return Conexión con la base de datos ex1.printStackTrace();
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection conectarBaseDatos(String usuario, String pass, String port) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return MySQLConnection(usuario, pass, port, NOMBRE_DB);
    }

    /**
     * Crea una conexión con la base de datos dada.
     *
     * @param user usuario
     * @param pass contraseña
     * @param db_name nombre de la base de datos a conectar
     * @return Conexión
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static Connection MySQLConnection(String user, String pass, String port, String db_name) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn;

        boolean ssl = IP.equals("127.0.0.1") || IP.equals("localhost");
        if (port.equals("")) {
            conn = DriverManager.getConnection("jdbc:mysql://" + IP + "/" + db_name + "?connectTimeout=15000&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=" + ssl, user, pass);
        } else {
            conn = DriverManager.getConnection("jdbc:mysql://" + IP + ":" + port + "/" + db_name + "?connectTimeout=15000&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=" + ssl, user, pass);
        }
        return conn;
    }
}
