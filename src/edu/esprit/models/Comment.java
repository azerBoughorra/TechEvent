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
public class Comment extends Reportable {

    private int id;
    private Event event;
    private User User;
    private String body;

    public Comment(int id, Event event, User User, String body) {
        this.id = id;
        this.event = event;
        this.User = User;
        this.body = body;
    }

    public Comment(Event event, User User, String body) {
        this.event = event;
        this.User = User;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", event=" + event + ", User=" + User + ", body=" + body + '}';
    }
     
}
