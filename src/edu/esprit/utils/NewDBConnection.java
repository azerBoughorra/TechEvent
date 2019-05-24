/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author azer
 */
public class NewDBConnection {

    private EntityManager em;
    public static NewDBConnection instance;

    private NewDBConnection() {

        try (InputStream input = new FileInputStream("resources\\NewDBCredantials.properties")) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value 
            EntityManagerFactory emf;
            emf = Persistence.createEntityManagerFactory("TechEventPU",prop);
            em = emf.createEntityManager();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static NewDBConnection getInstance() throws Exception {
        if (instance == null) {
            instance = new NewDBConnection();
        }
        return instance;
    }

    public EntityManager getManager() {
        return em;
    }
}
