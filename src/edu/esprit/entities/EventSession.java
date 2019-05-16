/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author azer
 */
@Entity
@Table(name = "event_session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventSession.findAll", query = "SELECT e FROM EventSession e"),
    @NamedQuery(name = "EventSession.findByEventSessionIdFkPk", query = "SELECT e FROM EventSession e WHERE e.eventSessionPK.eventSessionIdFkPk = :eventSessionIdFkPk"),
    @NamedQuery(name = "EventSession.findByEventSessionStartTimePk", query = "SELECT e FROM EventSession e WHERE e.eventSessionPK.eventSessionStartTimePk = :eventSessionStartTimePk"),
    @NamedQuery(name = "EventSession.findByEventSessionEndTimePk", query = "SELECT e FROM EventSession e WHERE e.eventSessionPK.eventSessionEndTimePk = :eventSessionEndTimePk"),
    @NamedQuery(name = "EventSession.findByEventSessionName", query = "SELECT e FROM EventSession e WHERE e.eventSessionName = :eventSessionName"),
    @NamedQuery(name = "EventSession.findByEventSessionDescription", query = "SELECT e FROM EventSession e WHERE e.eventSessionDescription = :eventSessionDescription"),
    @NamedQuery(name = "EventSession.findByIsdeleted", query = "SELECT e FROM EventSession e WHERE e.isdeleted = :isdeleted")})
public class EventSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventSessionPK eventSessionPK;
    @Column(name = "EVENT_SESSION_NAME")
    private String eventSessionName;
    @Column(name = "EVENT_SESSION_DESCRIPTION")
    private String eventSessionDescription;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @JoinColumn(name = "EVENT_SESSION_ID_FK_PK", referencedColumnName = "EVENT_ID_PK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Events events;

    public EventSession() {
    }

    public EventSession(EventSessionPK eventSessionPK) {
        this.eventSessionPK = eventSessionPK;
    }

    public EventSession(int eventSessionIdFkPk, Date eventSessionStartTimePk, Date eventSessionEndTimePk) {
        this.eventSessionPK = new EventSessionPK(eventSessionIdFkPk, eventSessionStartTimePk, eventSessionEndTimePk);
    }

    public EventSessionPK getEventSessionPK() {
        return eventSessionPK;
    }

    public void setEventSessionPK(EventSessionPK eventSessionPK) {
        this.eventSessionPK = eventSessionPK;
    }

    public String getEventSessionName() {
        return eventSessionName;
    }

    public void setEventSessionName(String eventSessionName) {
        this.eventSessionName = eventSessionName;
    }

    public String getEventSessionDescription() {
        return eventSessionDescription;
    }

    public void setEventSessionDescription(String eventSessionDescription) {
        this.eventSessionDescription = eventSessionDescription;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventSessionPK != null ? eventSessionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventSession)) {
            return false;
        }
        EventSession other = (EventSession) object;
        if ((this.eventSessionPK == null && other.eventSessionPK != null) || (this.eventSessionPK != null && !this.eventSessionPK.equals(other.eventSessionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventSession[ eventSessionPK=" + eventSessionPK + " ]";
    }
    
}
