/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.EventComment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.Events;
import edu.esprit.entities.UserAccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EventCommentController implements Serializable {

    public EventCommentController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventComment eventComment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events eventCommentEventIdFk = eventComment.getEventCommentEventIdFk();
            if (eventCommentEventIdFk != null) {
                eventCommentEventIdFk = em.getReference(eventCommentEventIdFk.getClass(), eventCommentEventIdFk.getEventIdPk());
                eventComment.setEventCommentEventIdFk(eventCommentEventIdFk);
            }
            UserAccount eventCommentUserIdFk = eventComment.getEventCommentUserIdFk();
            if (eventCommentUserIdFk != null) {
                eventCommentUserIdFk = em.getReference(eventCommentUserIdFk.getClass(), eventCommentUserIdFk.getUserIdPk());
                eventComment.setEventCommentUserIdFk(eventCommentUserIdFk);
            }
            em.persist(eventComment);
            if (eventCommentEventIdFk != null) {
                eventCommentEventIdFk.getEventCommentList().add(eventComment);
                eventCommentEventIdFk = em.merge(eventCommentEventIdFk);
            }
            if (eventCommentUserIdFk != null) {
                eventCommentUserIdFk.getEventCommentList().add(eventComment);
                eventCommentUserIdFk = em.merge(eventCommentUserIdFk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventComment eventComment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventComment persistentEventComment = em.find(EventComment.class, eventComment.getEventCommentIdPk());
            Events eventCommentEventIdFkOld = persistentEventComment.getEventCommentEventIdFk();
            Events eventCommentEventIdFkNew = eventComment.getEventCommentEventIdFk();
            UserAccount eventCommentUserIdFkOld = persistentEventComment.getEventCommentUserIdFk();
            UserAccount eventCommentUserIdFkNew = eventComment.getEventCommentUserIdFk();
            if (eventCommentEventIdFkNew != null) {
                eventCommentEventIdFkNew = em.getReference(eventCommentEventIdFkNew.getClass(), eventCommentEventIdFkNew.getEventIdPk());
                eventComment.setEventCommentEventIdFk(eventCommentEventIdFkNew);
            }
            if (eventCommentUserIdFkNew != null) {
                eventCommentUserIdFkNew = em.getReference(eventCommentUserIdFkNew.getClass(), eventCommentUserIdFkNew.getUserIdPk());
                eventComment.setEventCommentUserIdFk(eventCommentUserIdFkNew);
            }
            eventComment = em.merge(eventComment);
            if (eventCommentEventIdFkOld != null && !eventCommentEventIdFkOld.equals(eventCommentEventIdFkNew)) {
                eventCommentEventIdFkOld.getEventCommentList().remove(eventComment);
                eventCommentEventIdFkOld = em.merge(eventCommentEventIdFkOld);
            }
            if (eventCommentEventIdFkNew != null && !eventCommentEventIdFkNew.equals(eventCommentEventIdFkOld)) {
                eventCommentEventIdFkNew.getEventCommentList().add(eventComment);
                eventCommentEventIdFkNew = em.merge(eventCommentEventIdFkNew);
            }
            if (eventCommentUserIdFkOld != null && !eventCommentUserIdFkOld.equals(eventCommentUserIdFkNew)) {
                eventCommentUserIdFkOld.getEventCommentList().remove(eventComment);
                eventCommentUserIdFkOld = em.merge(eventCommentUserIdFkOld);
            }
            if (eventCommentUserIdFkNew != null && !eventCommentUserIdFkNew.equals(eventCommentUserIdFkOld)) {
                eventCommentUserIdFkNew.getEventCommentList().add(eventComment);
                eventCommentUserIdFkNew = em.merge(eventCommentUserIdFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventComment.getEventCommentIdPk();
                if (findEventComment(id) == null) {
                    throw new NonexistentEntityException("The eventComment with id " + id + " no longer exists.");
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
            EventComment eventComment;
            try {
                eventComment = em.getReference(EventComment.class, id);
                eventComment.getEventCommentIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventComment with id " + id + " no longer exists.", enfe);
            }
            Events eventCommentEventIdFk = eventComment.getEventCommentEventIdFk();
            if (eventCommentEventIdFk != null) {
                eventCommentEventIdFk.getEventCommentList().remove(eventComment);
                eventCommentEventIdFk = em.merge(eventCommentEventIdFk);
            }
            UserAccount eventCommentUserIdFk = eventComment.getEventCommentUserIdFk();
            if (eventCommentUserIdFk != null) {
                eventCommentUserIdFk.getEventCommentList().remove(eventComment);
                eventCommentUserIdFk = em.merge(eventCommentUserIdFk);
            }
            em.remove(eventComment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventComment> findEventCommentEntities() {
        return findEventCommentEntities(true, -1, -1);
    }

    public List<EventComment> findEventCommentEntities(int maxResults, int firstResult) {
        return findEventCommentEntities(false, maxResults, firstResult);
    }

    private List<EventComment> findEventCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventComment.class));
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

    public EventComment findEventComment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventComment.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventComment> rt = cq.from(EventComment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
