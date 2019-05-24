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
@Table(name = "user_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByUserIdPk", query = "SELECT u FROM UserAccount u WHERE u.userIdPk = :userIdPk"),
    @NamedQuery(name = "UserAccount.findByUserEmail", query = "SELECT u FROM UserAccount u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "UserAccount.findByUserName", query = "SELECT u FROM UserAccount u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserAccount.findByUserLastName", query = "SELECT u FROM UserAccount u WHERE u.userLastName = :userLastName"),
    @NamedQuery(name = "UserAccount.findByUserLogin", query = "SELECT u FROM UserAccount u WHERE u.userLogin = :userLogin"),
    @NamedQuery(name = "UserAccount.findByUserPassword", query = "SELECT u FROM UserAccount u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "UserAccount.findByUserBirthdate", query = "SELECT u FROM UserAccount u WHERE u.userBirthdate = :userBirthdate"),
    @NamedQuery(name = "UserAccount.findByUserAdress", query = "SELECT u FROM UserAccount u WHERE u.userAdress = :userAdress"),
    @NamedQuery(name = "UserAccount.findByUserPhotoUrl", query = "SELECT u FROM UserAccount u WHERE u.userPhotoUrl = :userPhotoUrl"),
    @NamedQuery(name = "UserAccount.findByUserBlocked", query = "SELECT u FROM UserAccount u WHERE u.userBlocked = :userBlocked"),
    @NamedQuery(name = "UserAccount.findByUserActivated", query = "SELECT u FROM UserAccount u WHERE u.userActivated = :userActivated"),
    @NamedQuery(name = "UserAccount.findByIsdeleted", query = "SELECT u FROM UserAccount u WHERE u.isdeleted = :isdeleted")})
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID_PK")
    private Integer userIdPk;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Basic(optional = false)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @Column(name = "USER_LAST_NAME")
    private String userLastName;
    @Basic(optional = false)
    @Column(name = "USER_LOGIN")
    private String userLogin;
    @Basic(optional = false)
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "USER_BIRTHDATE")
    private String userBirthdate;
    @Basic(optional = false)
    @Column(name = "USER_ADRESS")
    private String userAdress;
    @Column(name = "USER_PHOTO_URL")
    private String userPhotoUrl;
    @Column(name = "USER_BLOCKED")
    private Short userBlocked;
    @Column(name = "USER_ACTIVATED")
    private Short userActivated;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount")
    private List<EventParticipation> eventParticipationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reporterIdFk")
    private List<Report> reportList;
    @JoinColumn(name = "USER_ENTREPRISE_ID_FK", referencedColumnName = "ENTREPRISE_ID_PK")
    @ManyToOne
    private Entreprise userEntrepriseIdFk;
    @JoinColumn(name = "USER_ROLE_ID_FK", referencedColumnName = "ROLE_ID_PK")
    @ManyToOne
    private RoleUser userRoleIdFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount")
    private List<EventRating> eventRatingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventCommentUserIdFk")
    private List<EventComment> eventCommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventOrganisatorIdFk")
    private List<Events> eventsList;

    public UserAccount() {
    }

    public UserAccount(Integer userIdPk) {
        this.userIdPk = userIdPk;
    }

    public UserAccount(Integer userIdPk, String userName, String userLastName, String userLogin, String userPassword, String userBirthdate, String userAdress) {
        this.userIdPk = userIdPk;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userBirthdate = userBirthdate;
        this.userAdress = userAdress;
    }

    public Integer getUserIdPk() {
        return userIdPk;
    }

    public void setUserIdPk(Integer userIdPk) {
        this.userIdPk = userIdPk;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public Short getUserBlocked() {
        return userBlocked;
    }

    public void setUserBlocked(Short userBlocked) {
        this.userBlocked = userBlocked;
    }

    public Short getUserActivated() {
        return userActivated;
    }

    public void setUserActivated(Short userActivated) {
        this.userActivated = userActivated;
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
    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public Entreprise getUserEntrepriseIdFk() {
        return userEntrepriseIdFk;
    }

    public void setUserEntrepriseIdFk(Entreprise userEntrepriseIdFk) {
        this.userEntrepriseIdFk = userEntrepriseIdFk;
    }

    public RoleUser getUserRoleIdFk() {
        return userRoleIdFk;
    }

    public void setUserRoleIdFk(RoleUser userRoleIdFk) {
        this.userRoleIdFk = userRoleIdFk;
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
    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userIdPk != null ? userIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.userIdPk == null && other.userIdPk != null) || (this.userIdPk != null && !this.userIdPk.equals(other.userIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserAccount{" + "userIdPk=" + userIdPk + ", userEmail=" + userEmail + ", userName=" + userName + ", userLastName=" + userLastName + ", userLogin=" + userLogin + ", userPassword=" + userPassword + ", userBirthdate=" + userBirthdate + ", userAdress=" + userAdress + ", userPhotoUrl=" + userPhotoUrl + ", userBlocked=" + userBlocked + ", userActivated=" + userActivated + ", isdeleted=" + isdeleted + ", eventParticipationList=" + eventParticipationList + ", reportList=" + reportList + ", userEntrepriseIdFk=" + userEntrepriseIdFk + ", userRoleIdFk=" + userRoleIdFk + ", eventRatingList=" + eventRatingList + ", eventCommentList=" + eventCommentList + ", eventsList=" + eventsList + '}';
    }

    
    
}
