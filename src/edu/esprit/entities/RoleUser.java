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
@Table(name = "role_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoleUser.findAll", query = "SELECT r FROM RoleUser r"),
    @NamedQuery(name = "RoleUser.findByRoleIdPk", query = "SELECT r FROM RoleUser r WHERE r.roleIdPk = :roleIdPk"),
    @NamedQuery(name = "RoleUser.findByRoleDescription", query = "SELECT r FROM RoleUser r WHERE r.roleDescription = :roleDescription"),
    @NamedQuery(name = "RoleUser.findByIsdeleted", query = "SELECT r FROM RoleUser r WHERE r.isdeleted = :isdeleted")})
public class RoleUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLE_ID_PK")
    private Integer roleIdPk;
    @Basic(optional = false)
    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(mappedBy = "userRoleIdFk")
    private List<UserAccount> userAccountList;

    public RoleUser() {
    }

    public RoleUser(Integer roleIdPk) {
        this.roleIdPk = roleIdPk;
    }

    public RoleUser(Integer roleIdPk, String roleDescription) {
        this.roleIdPk = roleIdPk;
        this.roleDescription = roleDescription;
    }

    public Integer getRoleIdPk() {
        return roleIdPk;
    }

    public void setRoleIdPk(Integer roleIdPk) {
        this.roleIdPk = roleIdPk;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }

    public void setUserAccountList(List<UserAccount> userAccountList) {
        this.userAccountList = userAccountList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleIdPk != null ? roleIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleUser)) {
            return false;
        }
        RoleUser other = (RoleUser) object;
        if ((this.roleIdPk == null && other.roleIdPk != null) || (this.roleIdPk != null && !this.roleIdPk.equals(other.roleIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.RoleUser[ roleIdPk=" + roleIdPk + " ]";
    }
    
}
