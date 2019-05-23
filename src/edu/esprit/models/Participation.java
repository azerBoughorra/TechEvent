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
    private Event event;
    private RoleParticipation role;

    public Participation(User user, Event event, RoleParticipation role) {
        this.user = user;
        this.event = event;
        this.role = role;
    }

    public Participation(Event event, RoleParticipation role) {
        this.event = event;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public RoleParticipation getRole() {
        return role;
    }

    public void setRole(RoleParticipation role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Participation{" + "user=" + user + ", event=" + event + ", role=" + role + '}';
    }
    
    

}
