/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author azer
 */
@Entity
@Table(name = "events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Events.findAll", query = "SELECT e FROM Events e"),
    @NamedQuery(name = "Events.findByEventIdPk", query = "SELECT e FROM Events e WHERE e.eventIdPk = :eventIdPk"),
    @NamedQuery(name = "Events.findByEventTitle", query = "SELECT e FROM Events e WHERE e.eventTitle = :eventTitle"),
    @NamedQuery(name = "Events.findByEventDescription", query = "SELECT e FROM Events e WHERE e.eventDescription = :eventDescription"),
    @NamedQuery(name = "Events.findByEventPhotoUrl", query = "SELECT e FROM Events e WHERE e.eventPhotoUrl = :eventPhotoUrl"),
    @NamedQuery(name = "Events.findByEventFree", query = "SELECT e FROM Events e WHERE e.eventFree = :eventFree"),
    @NamedQuery(name = "Events.findByEventPrice", query = "SELECT e FROM Events e WHERE e.eventPrice = :eventPrice"),
    @NamedQuery(name = "Events.findByIsdeleted", query = "SELECT e FROM Events e WHERE e.isdeleted = :isdeleted")})
public class Events implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVENT_ID_PK")
    private Integer eventIdPk;
    @Basic(optional = false)
    @Column(name = "EVENT_TITLE")
    private String eventTitle;
    @Column(name = "EVENT_DESCRIPTION")
    private String eventDescription;
    @Column(name = "EVENT_PHOTO_URL")
    private String eventPhotoUrl;
    @Basic(optional = false)
    @Column(name = "EVENT_FREE")
    private short eventFree;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EVENT_PRICE")
    private Double eventPrice;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "events")
    private List<EventParticipation> eventParticipationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "events")
    private List<EventRating> eventRatingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventCommentEventIdFk")
    private List<EventComment> eventCommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "events")
    private List<EventSession> eventSessionList;
    @JoinColumn(name = "EVENT_CATEGORY_ID_FK", referencedColumnName = "EVENT_CATEGORY_ID_PK")
    @ManyToOne(optional = false)
    private EventCategory eventCategoryIdFk;
    @JoinColumn(name = "EVENT_LOCATION_ID_FK", referencedColumnName = "EVENT_LOCATION_ID_PK")
    @ManyToOne(optional = false)
    private EventLocation eventLocationIdFk;
    @JoinColumn(name = "EVENT_ORGANISATOR_ID_FK", referencedColumnName = "USER_ID_PK")
    @ManyToOne(optional = false)
    private UserAccount eventOrganisatorIdFk;

    public Events() {
    }

    public Events(Integer eventIdPk) {
        this.eventIdPk = eventIdPk;
    }

    public Events(Integer eventIdPk, String eventTitle, short eventFree) {
        this.eventIdPk = eventIdPk;
        this.eventTitle = eventTitle;
        this.eventFree = eventFree;
    }

    public Integer getEventIdPk() {
        return eventIdPk;
    }

    public void setEventIdPk(Integer eventIdPk) {
        this.eventIdPk = eventIdPk;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventPhotoUrl() {
        return eventPhotoUrl;
    }

    public void setEventPhotoUrl(String eventPhotoUrl) {
        this.eventPhotoUrl = eventPhotoUrl;
    }

    public short getEventFree() {
        return eventFree;
    }

    public void setEventFree(short eventFree) {
        this.eventFree = eventFree;
    }

    public Double getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(Double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<EventParticipation> getEventParticipationList() {
        return eventParticipationList;
    }

    public void setEventParticipationList(List<EventParticipation> eventParticipationList) {
        this.eventParticipationList = eventParticipationList;
    }

    @XmlTransient
    public List<EventRating> getEventRatingList() {
        return eventRatingList;
    }

    public void setEventRatingList(List<EventRating> eventRatingList) {
        this.eventRatingList = eventRatingList;
    }

    @XmlTransient
    public List<EventComment> getEventCommentList() {
        return eventCommentList;
    }

    public void setEventCommentList(List<EventComment> eventCommentList) {
        this.eventCommentList = eventCommentList;
    }

    @XmlTransient
    public List<EventSession> getEventSessionList() {
        return eventSessionList;
    }

    public void setEventSessionList(List<EventSession> eventSessionList) {
        this.eventSessionList = eventSessionList;
    }

    public EventCategory getEventCategoryIdFk() {
        return eventCategoryIdFk;
    }

    public void setEventCategoryIdFk(EventCategory eventCategoryIdFk) {
        this.eventCategoryIdFk = eventCategoryIdFk;
    }

    public EventLocation getEventLocationIdFk() {
        return eventLocationIdFk;
    }

    public void setEventLocationIdFk(EventLocation eventLocationIdFk) {
        this.eventLocationIdFk = eventLocationIdFk;
    }

    public UserAccount getEventOrganisatorIdFk() {
        return eventOrganisatorIdFk;
    }

    public void setEventOrganisatorIdFk(UserAccount eventOrganisatorIdFk) {
        this.eventOrganisatorIdFk = eventOrganisatorIdFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventIdPk != null ? eventIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.eventIdPk == null && other.eventIdPk != null) || (this.eventIdPk != null && !this.eventIdPk.equals(other.eventIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.Events[ eventIdPk=" + eventIdPk + " ]";
    }
    
}
