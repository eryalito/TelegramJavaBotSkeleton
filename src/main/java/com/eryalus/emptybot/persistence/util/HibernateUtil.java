package com.eryalus.emptybot.persistence.util;

import com.eryalus.emptybot.persistence.entities.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static String user = null;
    private static String pass = null;
    private static String port = "3306";
    private static String database = null;
    private static String ip = "localhost";

    public static void init(String user, String pass, String port, String database, String ip){
        HibernateUtil.user = user;
        HibernateUtil.pass = pass;
        HibernateUtil.port = port;
        HibernateUtil.database = database;
        HibernateUtil.ip = ip;
    }


    public static SessionFactory getSessionFactory() {
        if(user == null || pass == null ||  database == null || port == null || ip == null){
            throw new RuntimeException("HibernateUtil not initialized");
        }
        if (sessionFactory == null) {
            try {
                boolean ssl = ip.equals("127.0.0.1") || ip.equals("localhost");
                // Create SessionFactory
                sessionFactory = new Configuration()
                // .addResource("hibernate.cfg.xml")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("current_session_context_class", "thread")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                .setProperty("hibernate.connection.url", "jdbc:mysql://" + ip + "/" + database + "?connectTimeout=15000&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=" + ssl)
                .setProperty("hibernate.connection.username", user)
                .setProperty("hibernate.connection.password", pass)
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
                
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}