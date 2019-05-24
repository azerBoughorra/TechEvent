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
@Table(name = "event_rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventRating.findAll", query = "SELECT e FROM EventRating e"),
    @NamedQuery(name = "EventRating.findByUserIdFkPk", query = "SELECT e FROM EventRating e WHERE e.eventRatingPK.userIdFkPk = :userIdFkPk"),
    @NamedQuery(name = "EventRating.findByEventIdFkPk", query = "SELECT e FROM EventRating e WHERE e.eventRatingPK.eventIdFkPk = :eventIdFkPk"),
    @NamedQuery(name = "EventRating.findByEventRate", query = "SELECT e FROM EventRating e WHERE e.eventRate = :eventRate"),
    @NamedQuery(name = "EventRating.findByIsdeleted", query = "SELECT e FROM EventRating e WHERE e.isdeleted = :isdeleted")})
public class EventRating implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventRatingPK eventRatingPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EVENT_RATE")
    private Double eventRate;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @JoinColumn(name = "EVENT_ID_FK_PK", referencedColumnName = "EVENT_ID_PK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Events events;
    @JoinColumn(name = "USER_ID_FK_PK", referencedColumnName = "USER_ID_PK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    public EventRating() {
    }

    public EventRating(EventRatingPK eventRatingPK) {
        this.eventRatingPK = eventRatingPK;
    }

    public EventRating(int userIdFkPk, int eventIdFkPk) {
        this.eventRatingPK = new EventRatingPK(userIdFkPk, eventIdFkPk);
    }

    public EventRatingPK getEventRatingPK() {
        return eventRatingPK;
    }

    public void setEventRatingPK(EventRatingPK eventRatingPK) {
        this.eventRatingPK = eventRatingPK;
    }

    public Double getEventRate() {
        return eventRate;
    }

    public void setEventRate(Double eventRate) {
        this.eventRate = eventRate;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventRatingPK != null ? eventRatingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventRating)) {
            return false;
        }
        EventRating other = (EventRating) object;
        if ((this.eventRatingPK == null && other.eventRatingPK != null) || (this.eventRatingPK != null && !this.eventRatingPK.equals(other.eventRatingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventRating[ eventRatingPK=" + eventRatingPK + " ]";
    }
    
}
