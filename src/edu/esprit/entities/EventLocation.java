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
@Table(name = "event_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventLocation.findAll", query = "SELECT e FROM EventLocation e"),
    @NamedQuery(name = "EventLocation.findByEventLocationIdPk", query = "SELECT e FROM EventLocation e WHERE e.eventLocationIdPk = :eventLocationIdPk"),
    @NamedQuery(name = "EventLocation.findByEventLocationName", query = "SELECT e FROM EventLocation e WHERE e.eventLocationName = :eventLocationName"),
    @NamedQuery(name = "EventLocation.findByEventLocationLat", query = "SELECT e FROM EventLocation e WHERE e.eventLocationLat = :eventLocationLat"),
    @NamedQuery(name = "EventLocation.findByEventLocationLong", query = "SELECT e FROM EventLocation e WHERE e.eventLocationLong = :eventLocationLong"),
    @NamedQuery(name = "EventLocation.findByEventLocationZipCode", query = "SELECT e FROM EventLocation e WHERE e.eventLocationZipCode = :eventLocationZipCode"),
    @NamedQuery(name = "EventLocation.findByIsdeleted", query = "SELECT e FROM EventLocation e WHERE e.isdeleted = :isdeleted")})
public class EventLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVENT_LOCATION_ID_PK")
    private Integer eventLocationIdPk;
    @Basic(optional = false)
    @Column(name = "EVENT_LOCATION_NAME")
    private String eventLocationName;
    @Basic(optional = false)
    @Column(name = "EVENT_LOCATION_LAT")
    private double eventLocationLat;
    @Basic(optional = false)
    @Column(name = "EVENT_LOCATION_LONG")
    private double eventLocationLong;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EVENT_LOCATION_ZIP_CODE")
    private Double eventLocationZipCode;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventLocationIdFk")
    private List<Events> eventsList;

    public EventLocation() {
    }

    public EventLocation(Integer eventLocationIdPk) {
        this.eventLocationIdPk = eventLocationIdPk;
    }

    public EventLocation(Integer eventLocationIdPk, String eventLocationName, double eventLocationLat, double eventLocationLong) {
        this.eventLocationIdPk = eventLocationIdPk;
        this.eventLocationName = eventLocationName;
        this.eventLocationLat = eventLocationLat;
        this.eventLocationLong = eventLocationLong;
    }

    public Integer getEventLocationIdPk() {
        return eventLocationIdPk;
    }

    public void setEventLocationIdPk(Integer eventLocationIdPk) {
        this.eventLocationIdPk = eventLocationIdPk;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public double getEventLocationLat() {
        return eventLocationLat;
    }

    public void setEventLocationLat(double eventLocationLat) {
        this.eventLocationLat = eventLocationLat;
    }

    public double getEventLocationLong() {
        return eventLocationLong;
    }

    public void setEventLocationLong(double eventLocationLong) {
        this.eventLocationLong = eventLocationLong;
    }

    public Double getEventLocationZipCode() {
        return eventLocationZipCode;
    }

    public void setEventLocationZipCode(Double eventLocationZipCode) {
        this.eventLocationZipCode = eventLocationZipCode;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventLocationIdPk != null ? eventLocationIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventLocation)) {
            return false;
        }
        EventLocation other = (EventLocation) object;
        if ((this.eventLocationIdPk == null && other.eventLocationIdPk != null) || (this.eventLocationIdPk != null && !this.eventLocationIdPk.equals(other.eventLocationIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventLocation[ eventLocationIdPk=" + eventLocationIdPk + " ]";
    }
    
}
