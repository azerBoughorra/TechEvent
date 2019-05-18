/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.Report;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.UserAccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class ReportController implements Serializable {

    public ReportController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Report report) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount reporterIdFk = report.getReporterIdFk();
            if (reporterIdFk != null) {
                reporterIdFk = em.getReference(reporterIdFk.getClass(), reporterIdFk.getUserIdPk());
                report.setReporterIdFk(reporterIdFk);
            }
            em.persist(report);
            if (reporterIdFk != null) {
                reporterIdFk.getReportList().add(report);
                reporterIdFk = em.merge(reporterIdFk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Report report) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Report persistentReport = em.find(Report.class, report.getReportIdPk());
            UserAccount reporterIdFkOld = persistentReport.getReporterIdFk();
            UserAccount reporterIdFkNew = report.getReporterIdFk();
            if (reporterIdFkNew != null) {
                reporterIdFkNew = em.getReference(reporterIdFkNew.getClass(), reporterIdFkNew.getUserIdPk());
                report.setReporterIdFk(reporterIdFkNew);
            }
            report = em.merge(report);
            if (reporterIdFkOld != null && !reporterIdFkOld.equals(reporterIdFkNew)) {
                reporterIdFkOld.getReportList().remove(report);
                reporterIdFkOld = em.merge(reporterIdFkOld);
            }
            if (reporterIdFkNew != null && !reporterIdFkNew.equals(reporterIdFkOld)) {
                reporterIdFkNew.getReportList().add(report);
                reporterIdFkNew = em.merge(reporterIdFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = report.getReportIdPk();
                if (findReport(id) == null) {
                    throw new NonexistentEntityException("The report with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Report report;
            try {
                report = em.getReference(Report.class, id);
                report.getReportIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The report with id " + id + " no longer exists.", enfe);
            }
            UserAccount reporterIdFk = report.getReporterIdFk();
            if (reporterIdFk != null) {
                reporterIdFk.getReportList().remove(report);
                reporterIdFk = em.merge(reporterIdFk);
            }
            em.remove(report);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Report> findReportEntities() {
        return findReportEntities(true, -1, -1);
    }

    public List<Report> findReportEntities(int maxResults, int firstResult) {
        return findReportEntities(false, maxResults, firstResult);
    }

    private List<Report> findReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Report.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Report findReport(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Report.class, id);
        } finally {
            em.close();
        }
    }

    public int getReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Report> rt = cq.from(Report.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
