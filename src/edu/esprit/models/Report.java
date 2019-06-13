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
public class Report {

    private int id;
    private User reporter;
    private String body;
    private Reportable target;
 

    public Report(int id, User reporter, String body, Reportable target) {
        this.id = id;
        this.reporter = reporter;
        this.body = body;
        this.target = target;
    }

    public Report(User reporter, String body, Reportable target) {
        this.reporter = reporter;
        this.body = body;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Reportable getTarget() {
        return target;
    }

    public void setTarget(Reportable target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", reporter=" + reporter + ", body=" + body + ", target=" + target + '}';
    }
    
    
}
