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
public class Rating {

    private User user;
    private int eventId;
    private double rate;

    public Rating(User user, int eventId, double rate) {
        this.user = user;
        this.eventId = eventId;
        this.rate = rate;
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

    public void setEventId(int event) {
        this.eventId = event;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rating{" + "user=" + user + ", event=" + eventId + ", rate=" + rate + '}';
    }
    
    

}
