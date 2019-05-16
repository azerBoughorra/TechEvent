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
@Table(name = "entreprise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entreprise.findAll", query = "SELECT e FROM Entreprise e"),
    @NamedQuery(name = "Entreprise.findByEntrepriseIdPk", query = "SELECT e FROM Entreprise e WHERE e.entrepriseIdPk = :entrepriseIdPk"),
    @NamedQuery(name = "Entreprise.findByEntrepriseName", query = "SELECT e FROM Entreprise e WHERE e.entrepriseName = :entrepriseName"),
    @NamedQuery(name = "Entreprise.findByIsdeleted", query = "SELECT e FROM Entreprise e WHERE e.isdeleted = :isdeleted")})
public class Entreprise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ENTREPRISE_ID_PK")
    private Integer entrepriseIdPk;
    @Basic(optional = false)
    @Column(name = "ENTREPRISE_NAME")
    private String entrepriseName;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(mappedBy = "userEntrepriseIdFk")
    private List<UserAccount> userAccountList;

    public Entreprise() {
    }

    public Entreprise(Integer entrepriseIdPk) {
        this.entrepriseIdPk = entrepriseIdPk;
    }

    public Entreprise(Integer entrepriseIdPk, String entrepriseName) {
        this.entrepriseIdPk = entrepriseIdPk;
        this.entrepriseName = entrepriseName;
    }

    public Integer getEntrepriseIdPk() {
        return entrepriseIdPk;
    }

    public void setEntrepriseIdPk(Integer entrepriseIdPk) {
        this.entrepriseIdPk = entrepriseIdPk;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }

    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
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
        hash += (entrepriseIdPk != null ? entrepriseIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entreprise)) {
            return false;
        }
        Entreprise other = (Entreprise) object;
        if ((this.entrepriseIdPk == null && other.entrepriseIdPk != null) || (this.entrepriseIdPk != null && !this.entrepriseIdPk.equals(other.entrepriseIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.Entreprise[ entrepriseIdPk=" + entrepriseIdPk + " ]";
    }
    
}
