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
public class EventParticipationPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "EVENT_ID_FK_PK")
    private int eventIdFkPk;
    @Basic(optional = false)
    @Column(name = "USER_ID_FK_PK")
    private int userIdFkPk;

    public EventParticipationPK() {
    }

    public EventParticipationPK(int eventIdFkPk, int userIdFkPk) {
        this.eventIdFkPk = eventIdFkPk;
        this.userIdFkPk = userIdFkPk;
    }

    public int getEventIdFkPk() {
        return eventIdFkPk;
    }

    public void setEventIdFkPk(int eventIdFkPk) {
        this.eventIdFkPk = eventIdFkPk;
    }

    public int getUserIdFkPk() {
        return userIdFkPk;
    }

    public void setUserIdFkPk(int userIdFkPk) {
        this.userIdFkPk = userIdFkPk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eventIdFkPk;
        hash += (int) userIdFkPk;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventParticipationPK)) {
            return false;
        }
        EventParticipationPK other = (EventParticipationPK) object;
        if (this.eventIdFkPk != other.eventIdFkPk) {
            return false;
        }
        if (this.userIdFkPk != other.userIdFkPk) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventParticipationPK[ eventIdFkPk=" + eventIdFkPk + ", userIdFkPk=" + userIdFkPk + " ]";
    }
    
}
