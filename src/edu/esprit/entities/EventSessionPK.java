/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author azer
 */
@Embeddable
public class EventSessionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "EVENT_SESSION_ID_FK_PK")
    private int eventSessionIdFkPk;
    @Basic(optional = false)
    @Column(name = "EVENT_SESSION_START_TIME_PK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventSessionStartTimePk;
    @Basic(optional = false)
    @Column(name = "EVENT_SESSION_END_TIME_PK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventSessionEndTimePk;

    public EventSessionPK() {
    }

    public EventSessionPK(int eventSessionIdFkPk, Date eventSessionStartTimePk, Date eventSessionEndTimePk) {
        this.eventSessionIdFkPk = eventSessionIdFkPk;
        this.eventSessionStartTimePk = eventSessionStartTimePk;
        this.eventSessionEndTimePk = eventSessionEndTimePk;
    }

    public int getEventSessionIdFkPk() {
        return eventSessionIdFkPk;
    }

    public void setEventSessionIdFkPk(int eventSessionIdFkPk) {
        this.eventSessionIdFkPk = eventSessionIdFkPk;
    }

    public Date getEventSessionStartTimePk() {
        return eventSessionStartTimePk;
    }

    public void setEventSessionStartTimePk(Date eventSessionStartTimePk) {
        this.eventSessionStartTimePk = eventSessionStartTimePk;
    }

    public Date getEventSessionEndTimePk() {
        return eventSessionEndTimePk;
    }

    public void setEventSessionEndTimePk(Date eventSessionEndTimePk) {
        this.eventSessionEndTimePk = eventSessionEndTimePk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eventSessionIdFkPk;
        hash += (eventSessionStartTimePk != null ? eventSessionStartTimePk.hashCode() : 0);
        hash += (eventSessionEndTimePk != null ? eventSessionEndTimePk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventSessionPK)) {
            return false;
        }
        EventSessionPK other = (EventSessionPK) object;
        if (this.eventSessionIdFkPk != other.eventSessionIdFkPk) {
            return false;
        }
        if ((this.eventSessionStartTimePk == null && other.eventSessionStartTimePk != null) || (this.eventSessionStartTimePk != null && !this.eventSessionStartTimePk.equals(other.eventSessionStartTimePk))) {
            return false;
        }
        if ((this.eventSessionEndTimePk == null && other.eventSessionEndTimePk != null) || (this.eventSessionEndTimePk != null && !this.eventSessionEndTimePk.equals(other.eventSessionEndTimePk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventSessionPK[ eventSessionIdFkPk=" + eventSessionIdFkPk + ", eventSessionStartTimePk=" + eventSessionStartTimePk + ", eventSessionEndTimePk=" + eventSessionEndTimePk + " ]";
    }
    
}
