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
@Table(name = "event_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventCategory.findAll", query = "SELECT e FROM EventCategory e"),
    @NamedQuery(name = "EventCategory.findByEventCategoryIdPk", query = "SELECT e FROM EventCategory e WHERE e.eventCategoryIdPk = :eventCategoryIdPk"),
    @NamedQuery(name = "EventCategory.findByEventCategoryName", query = "SELECT e FROM EventCategory e WHERE e.eventCategoryName = :eventCategoryName"),
    @NamedQuery(name = "EventCategory.findByIsdeleted", query = "SELECT e FROM EventCategory e WHERE e.isdeleted = :isdeleted")})
public class EventCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVENT_CATEGORY_ID_PK")
    private Integer eventCategoryIdPk;
    @Basic(optional = false)
    @Column(name = "EVENT_CATEGORY_NAME")
    private String eventCategoryName;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventCategoryIdFk")
    private List<Events> eventsList;

    public EventCategory() {
    }

    public EventCategory(Integer eventCategoryIdPk) {
        this.eventCategoryIdPk = eventCategoryIdPk;
    }

    public EventCategory(Integer eventCategoryIdPk, String eventCategoryName) {
        this.eventCategoryIdPk = eventCategoryIdPk;
        this.eventCategoryName = eventCategoryName;
    }

    public Integer getEventCategoryIdPk() {
        return eventCategoryIdPk;
    }

    public void setEventCategoryIdPk(Integer eventCategoryIdPk) {
        this.eventCategoryIdPk = eventCategoryIdPk;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
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
        hash += (eventCategoryIdPk != null ? eventCategoryIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventCategory)) {
            return false;
        }
        EventCategory other = (EventCategory) object;
        if ((this.eventCategoryIdPk == null && other.eventCategoryIdPk != null) || (this.eventCategoryIdPk != null && !this.eventCategoryIdPk.equals(other.eventCategoryIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventCategory[ eventCategoryIdPk=" + eventCategoryIdPk + " ]";
    }
    
}
