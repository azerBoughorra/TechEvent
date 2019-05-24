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
@Table(name = "report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByReportIdPk", query = "SELECT r FROM Report r WHERE r.reportIdPk = :reportIdPk"),
    @NamedQuery(name = "Report.findByReportBody", query = "SELECT r FROM Report r WHERE r.reportBody = :reportBody"),
    @NamedQuery(name = "Report.findByReprotTarget", query = "SELECT r FROM Report r WHERE r.reprotTarget = :reprotTarget"),
    @NamedQuery(name = "Report.findByReportTargetId", query = "SELECT r FROM Report r WHERE r.reportTargetId = :reportTargetId"),
    @NamedQuery(name = "Report.findByIsdeleted", query = "SELECT r FROM Report r WHERE r.isdeleted = :isdeleted")})
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REPORT_ID_PK")
    private Integer reportIdPk;
    @Column(name = "REPORT_BODY")
    private String reportBody;
    @Column(name = "REPROT_TARGET")
    private String reprotTarget;
    @Column(name = "REPORT_TARGET_ID")
    private Integer reportTargetId;
    @Column(name = "ISDELETED")
    private Short isdeleted;
    @JoinColumn(name = "REPORTER_ID_FK", referencedColumnName = "USER_ID_PK")
    @ManyToOne(optional = false)
    private UserAccount reporterIdFk;

    public Report() {
    }

    public Report(Integer reportIdPk) {
        this.reportIdPk = reportIdPk;
    }

    public Integer getReportIdPk() {
        return reportIdPk;
    }

    public void setReportIdPk(Integer reportIdPk) {
        this.reportIdPk = reportIdPk;
    }

    public String getReportBody() {
        return reportBody;
    }

    public void setReportBody(String reportBody) {
        this.reportBody = reportBody;
    }

    public String getReprotTarget() {
        return reprotTarget;
    }

    public void setReprotTarget(String reprotTarget) {
        this.reprotTarget = reprotTarget;
    }

    public Integer getReportTargetId() {
        return reportTargetId;
    }

    public void setReportTargetId(Integer reportTargetId) {
        this.reportTargetId = reportTargetId;
    }

    public Short getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Short isdeleted) {
        this.isdeleted = isdeleted;
    }

    public UserAccount getReporterIdFk() {
        return reporterIdFk;
    }

    public void setReporterIdFk(UserAccount reporterIdFk) {
        this.reporterIdFk = reporterIdFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportIdPk != null ? reportIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportIdPk == null && other.reportIdPk != null) || (this.reportIdPk != null && !this.reportIdPk.equals(other.reportIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.esprit.entities.Report[ reportIdPk=" + reportIdPk + " ]";
    }
    
}
