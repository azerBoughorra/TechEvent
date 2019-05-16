/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
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
@Table(name = "event_participation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventParticipation.findAll", query = "SELECT e FROM EventParticipation e"),
    @NamedQuery(name = "EventParticipation.findByEventIdFkPk", query = "SELECT e FROM EventParticipation e WHERE e.eventParticipationPK.eventIdFkPk = :eventIdFkPk"),
    @NamedQuery(name = "EventParticipation.findByUserIdFkPk", query = "SELECT e FROM EventParticipation e WHERE e.eventParticipationPK.userIdFkPk = :userIdFkPk"),
    @NamedQuery(name = "EventParticipation.findByIsdeleted", query = "SELECT e FROM EventParticipation e WHERE e.isdeleted = :isdeleted")})
public class EventParticipation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventParticipationPK eventParticipationPK;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @JoinColumn(name = "EVENT_ID_FK_PK", referencedColumnName = "EVENT_ID_PK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Events events;
    @JoinColumn(name = "ROLE_PARTICIPATION_FK", referencedColumnName = "ROLE_PARTICIPATION_ID")
    @ManyToOne
    private RoleParticipation roleParticipationFk;
    @JoinColumn(name = "USER_ID_FK_PK", referencedColumnName = "USER_ID_PK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    public EventParticipation() {
    }

    public EventParticipation(EventParticipationPK eventParticipationPK) {
        this.eventParticipationPK = eventParticipationPK;
    }

    public EventParticipation(int eventIdFkPk, int userIdFkPk) {
        this.eventParticipationPK = new EventParticipationPK(eventIdFkPk, userIdFkPk);
    }

    public EventParticipationPK getEventParticipationPK() {
        return eventParticipationPK;
    }

    public void setEventParticipationPK(EventParticipationPK eventParticipationPK) {
        this.eventParticipationPK = eventParticipationPK;
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

    public RoleParticipation getRoleParticipationFk() {
        return roleParticipationFk;
    }

    public void setRoleParticipationFk(RoleParticipation roleParticipationFk) {
        this.roleParticipationFk = roleParticipationFk;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventParticipationPK != null ? eventParticipationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventParticipation)) {
            return false;
        }
        EventParticipation other = (EventParticipation) object;
        if ((this.eventParticipationPK == null && other.eventParticipationPK != null) || (this.eventParticipationPK != null && !this.eventParticipationPK.equals(other.eventParticipationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventParticipation[ eventParticipationPK=" + eventParticipationPK + " ]";
    }
    
}
