/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "event_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventComment.findAll", query = "SELECT e FROM EventComment e"),
    @NamedQuery(name = "EventComment.findByEventCommentIdPk", query = "SELECT e FROM EventComment e WHERE e.eventCommentIdPk = :eventCommentIdPk"),
    @NamedQuery(name = "EventComment.findByEventCommentBody", query = "SELECT e FROM EventComment e WHERE e.eventCommentBody = :eventCommentBody"),
    @NamedQuery(name = "EventComment.findByIsdeleted", query = "SELECT e FROM EventComment e WHERE e.isdeleted = :isdeleted")})
public class EventComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVENT_COMMENT_ID_PK")
    private Integer eventCommentIdPk;
    @Basic(optional = false)
    @Column(name = "EVENT_COMMENT_BODY")
    private String eventCommentBody;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @JoinColumn(name = "EVENT_COMMENT_EVENT_ID_FK", referencedColumnName = "EVENT_ID_PK")
    @ManyToOne(optional = false)
    private Events eventCommentEventIdFk;
    @JoinColumn(name = "EVENT_COMMENT_USER_ID_FK", referencedColumnName = "USER_ID_PK")
    @ManyToOne(optional = false)
    private UserAccount eventCommentUserIdFk;

    public EventComment() {
    }

    public EventComment(Integer eventCommentIdPk) {
        this.eventCommentIdPk = eventCommentIdPk;
    }

    public EventComment(Integer eventCommentIdPk, String eventCommentBody) {
        this.eventCommentIdPk = eventCommentIdPk;
        this.eventCommentBody = eventCommentBody;
    }

    public Integer getEventCommentIdPk() {
        return eventCommentIdPk;
    }

    public void setEventCommentIdPk(Integer eventCommentIdPk) {
        this.eventCommentIdPk = eventCommentIdPk;
    }

    public String getEventCommentBody() {
        return eventCommentBody;
    }

    public void setEventCommentBody(String eventCommentBody) {
        this.eventCommentBody = eventCommentBody;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Events getEventCommentEventIdFk() {
        return eventCommentEventIdFk;
    }

    public void setEventCommentEventIdFk(Events eventCommentEventIdFk) {
        this.eventCommentEventIdFk = eventCommentEventIdFk;
    }

    public UserAccount getEventCommentUserIdFk() {
        return eventCommentUserIdFk;
    }

    public void setEventCommentUserIdFk(UserAccount eventCommentUserIdFk) {
        this.eventCommentUserIdFk = eventCommentUserIdFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventCommentIdPk != null ? eventCommentIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventComment)) {
            return false;
        }
        EventComment other = (EventComment) object;
        if ((this.eventCommentIdPk == null && other.eventCommentIdPk != null) || (this.eventCommentIdPk != null && !this.eventCommentIdPk.equals(other.eventCommentIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.EventComment[ eventCommentIdPk=" + eventCommentIdPk + " ]";
    }
    
}
