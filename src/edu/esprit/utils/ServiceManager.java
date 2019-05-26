/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utils;

import com.sun.org.apache.bcel.internal.generic.IUSHR;
import edu.esprit.services.ICategoryService;
import edu.esprit.services.ICommentService;
import edu.esprit.services.IEntrepriseService;
import edu.esprit.services.IEventService;
import edu.esprit.services.ILocationService;
import edu.esprit.services.IParticipationService;
import edu.esprit.services.IRatingService;
import edu.esprit.services.IReportService;
import edu.esprit.services.IRoleParticipationService;
import edu.esprit.services.IRoleUserService;
import edu.esprit.services.ISessionService;
import edu.esprit.services.IUserService;
import edu.esprit.services.implementation.CommentService;
import edu.esprit.services.implementation.EventService;
import edu.esprit.services.implementation.ParticipationService;
import edu.esprit.services.implementation.RoleParticipationService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author azer
 */
public class ServiceManager {
     
    private ICategoryService categoryService;
    private ICommentService commentService;
    private IEntrepriseService entrepriseService;
    private IEventService eventService;
    private ILocationService locationService;
    private IParticipationService participationService;
    private IRatingService ratingService;
    private IReportService reportService;
    private IRoleParticipationService roleParticipationService;
    private IRoleUserService roleUserService;
    private ISessionService sessionService;
    private IUserService userService;
    private static ServiceManager instance;
    
    
    private ServiceManager() {
        commentService=new CommentService();
        roleParticipationService=new RoleParticipationService();
        participationService=new ParticipationService();
        eventService=new EventService();
    }
    
    public static  ServiceManager getInstance() {
        if(instance == null)
            instance = new ServiceManager();
        return instance;
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public ICommentService getCommentService() {
        return commentService;
    }

    public IEntrepriseService getEntrepriseService() {
        return entrepriseService;
    }

    public IEventService getEventService() {
        return eventService;
    }

    public ILocationService getLocationService() {
        return locationService;
    }

    public IParticipationService getParticipationService() {
        return participationService;
    }

    public IRatingService getRatingService() {
        return ratingService;
    }

    public IReportService getReportService() {
        return reportService;
    }

    public IRoleParticipationService getRoleParticipationService() {
        return roleParticipationService;
    }

    public IRoleUserService getRoleUserService() {
        return roleUserService;
    }

    public ISessionService getSessionService() {
        return sessionService;
    }

    public IUserService getUserService() {
        return userService;
    }
    
    
}
