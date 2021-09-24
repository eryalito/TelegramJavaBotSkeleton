/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.dataBase;

import static com.eryalus.emptybot.dataBase.Basics.conectarBaseDatos;
import com.eryalus.emptybot.utils.time.Timer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eryalus
 */
public class ReguladorConexion extends Timer {

    private Connection conn = null;
    private final String usuario;
    private final String pass;
    private final String port;

    /**
     * Crea y mantiene activa una conexión reiniciandola cada cierto tiempo
     *
     * @param tiempo tiempo en milisegundos para reiniciar la conexión
     * @param usuario Usuario de la base de datos
     * @param pass Contraseña de a base de datos
     * @param port Puerto de acceso a la base de datos
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public ReguladorConexion(long tiempo, String usuario, String pass, String port) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(tiempo);
        this.usuario = usuario;
        this.pass = pass;
        this.port = port;
        conn = conectarBaseDatos(usuario, pass, port);
    }

    /**
     * Toma la conexión con la base de datos
     * @return conexion
     */
    public synchronized Connection getConnection() {
        return conn;
    }

    public synchronized boolean refresh(){
        try {
            Connection aux = conectarBaseDatos(usuario, pass, port);
            conn = aux;
            return true;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ReguladorConexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    protected void action() {
        try {
            conn.createStatement().execute("SELECT NOW()");
        } catch (SQLException ex) {
            refresh();
        }
    }
}
