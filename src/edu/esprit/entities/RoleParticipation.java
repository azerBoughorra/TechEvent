/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "role_participation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoleParticipation.findAll", query = "SELECT r FROM RoleParticipation r"),
    @NamedQuery(name = "RoleParticipation.findByRoleParticipationId", query = "SELECT r FROM RoleParticipation r WHERE r.roleParticipationId = :roleParticipationId"),
    @NamedQuery(name = "RoleParticipation.findByRoleParticipationDescription", query = "SELECT r FROM RoleParticipation r WHERE r.roleParticipationDescription = :roleParticipationDescription"),
    @NamedQuery(name = "RoleParticipation.findByIsdeleted", query = "SELECT r FROM RoleParticipation r WHERE r.isdeleted = :isdeleted")})
public class RoleParticipation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLE_PARTICIPATION_ID")
    private Integer roleParticipationId;
    @Column(name = "ROLE_PARTICIPATION_DESCRIPTION")
    private String roleParticipationDescription;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(mappedBy = "roleParticipationFk")
    private List<EventParticipation> eventParticipationList;

    public RoleParticipation() {
    }

    public RoleParticipation(Integer roleParticipationId) {
        this.roleParticipationId = roleParticipationId;
    }

    public Integer getRoleParticipationId() {
        return roleParticipationId;
    }

    public void setRoleParticipationId(Integer roleParticipationId) {
        this.roleParticipationId = roleParticipationId;
    }

    public String getRoleParticipationDescription() {
        return roleParticipationDescription;
    }

    public void setRoleParticipationDescription(String roleParticipationDescription) {
        this.roleParticipationDescription = roleParticipationDescription;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleParticipationId != null ? roleParticipationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleParticipation)) {
            return false;
        }
        RoleParticipation other = (RoleParticipation) object;
        if ((this.roleParticipationId == null && other.roleParticipationId != null) || (this.roleParticipationId != null && !this.roleParticipationId.equals(other.roleParticipationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.RoleParticipation[ roleParticipationId=" + roleParticipationId + " ]";
    }
    
}
