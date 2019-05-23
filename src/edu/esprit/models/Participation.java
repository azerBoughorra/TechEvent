/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.models;

/**
 *
 * @author azer
 */
public class Participation {

    private User user;
    private int eventId;
    private RoleParticipation role;

    public Participation(User user, int eventId, RoleParticipation role) {
        this.user = user;
        this.eventId = eventId;
        this.role = role;
    }

    public Participation(int eventId, RoleParticipation role) {
        this.eventId = eventId;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public RoleParticipation getRole() {
        return role;
    }

    public void setRole(RoleParticipation role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Participation{" + "user=" + user + ", event=" + eventId + ", role=" + role + '}';
    }
    
    

}
