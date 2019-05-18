/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utils;

import edu.esprit.entites.controllers.EntrepriseController;
import edu.esprit.entites.controllers.EventCategoryController;
import edu.esprit.entites.controllers.EventCommentController;
import edu.esprit.entites.controllers.EventLocationController;
import edu.esprit.entites.controllers.EventParticipationController;
import edu.esprit.entites.controllers.EventRatingController;
import edu.esprit.entites.controllers.EventSessionController;
import edu.esprit.entites.controllers.EventsController;
import edu.esprit.entites.controllers.ReportController;
import edu.esprit.entites.controllers.RoleParticipationController;
import edu.esprit.entites.controllers.RoleUserController;
import edu.esprit.entites.controllers.UserAccountController;
import edu.esprit.entities.Entreprise;
import edu.esprit.entities.RoleUser;
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
import javax.swing.RepaintManager;

/**
 *
 * @author azer
 */
public class DBManager {

    private static DBManager instance;

    private EntityManager em;
    private EntrepriseController entrepriseController;
    private EventCategoryController eventCategoryController;
    private EventCommentController eventCommentController;
    private EventLocationController eventLocationController;
    private EventParticipationController eventParticipationController;
    private EventRatingController eventRatingController;
    private EventSessionController eventSessionController;
    private EventsController eventsController;
    private ReportController reportController;
    private RoleParticipationController roleParticipationController;
    private RoleUserController RoleUserController;
    private UserAccountController userAccountController;

    private DBManager() {

        try (InputStream input = new FileInputStream("resources\\NewDBCredantials.properties")) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value 
            EntityManagerFactory emf;
            emf = Persistence.createEntityManagerFactory("TechEventPU", prop);
            em = emf.createEntityManager();
            this.RoleUserController = new RoleUserController(emf);
            this.entrepriseController = new EntrepriseController(emf);
            this.eventCategoryController = new EventCategoryController(emf);
            this.eventCommentController = new EventCommentController(emf);
            this.eventLocationController = new EventLocationController(emf);
            this.eventParticipationController = new EventParticipationController(emf);
            this.eventParticipationController = new EventParticipationController(emf);
            this.eventRatingController = new EventRatingController(emf);
            this.eventSessionController = new EventSessionController(emf);
            this.eventsController = new EventsController(emf);
            this.reportController = new ReportController(emf);
            this.roleParticipationController = new RoleParticipationController(emf);
            this.userAccountController = new UserAccountController(emf);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public EntrepriseController getEntrepriseController() {
        return entrepriseController;
    }

    public EventCategoryController getEventCategoryController() {
        return eventCategoryController;
    }

    public EventCommentController getEventCommentController() {
        return eventCommentController;
    }

    public EventLocationController getEventLocationController() {
        return eventLocationController;
    }

    public EventParticipationController getEventParticipationController() {
        return eventParticipationController;
    }

    public EventRatingController getEventRatingController() {
        return eventRatingController;
    }

    public EventSessionController getEventSessionController() {
        return eventSessionController;
    }

    public EventsController getEventsController() {
        return eventsController;
    }

    public ReportController getReportController() {
        return reportController;
    }

    public RoleParticipationController getRoleParticipationController() {
        return roleParticipationController;
    }

    public RoleUserController getRoleUserController() {
        return RoleUserController;
    }

    public UserAccountController getUserAccountController() {
        return userAccountController;
    }

    public static DBManager getInstance() throws Exception {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public EntityManager getManager() {
        return em;
    }
}
