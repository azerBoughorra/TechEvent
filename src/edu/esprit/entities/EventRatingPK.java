/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author azer
 */
@Embeddable
public class EventRatingPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "USER_ID_FK_PK")
    private int userIdFkPk;
    @Basic(optional = false)
    @Column(name = "EVENT_ID_FK_PK")
    private int eventIdFkPk;

    public EventRatingPK() {
    }

    public EventRatingPK(int userIdFkPk, int eventIdFkPk) {
        this.userIdFkPk = userIdFkPk;
        this.eventIdFkPk = eventIdFkPk;
    }

    public int getUserIdFkPk() {
        return userIdFkPk;
    }

    public void setUserIdFkPk(int userIdFkPk) {
        this.userIdFkPk = userIdFkPk;
    }

    public int getEventIdFkPk() {
        return eventIdFkPk;
    }

    public void setEventIdFkPk(int eventIdFkPk) {
        this.eventIdFkPk = eventIdFkPk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userIdFkPk;
        hash += (int) eventIdFkPk;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventRatingPK)) {
            return false;
        }
        EventRatingPK other = (EventRatingPK) object;
        if (this.userIdFkPk != other.userIdFkPk) {
            return false;
        }
        if (this.eventIdFkPk != other.eventIdFkPk) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventRatingPK[ userIdFkPk=" + userIdFkPk + ", eventIdFkPk=" + eventIdFkPk + " ]";
    }
    
}
