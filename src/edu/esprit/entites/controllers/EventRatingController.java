/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entites.controllers.exceptions.PreexistingEntityException;
import edu.esprit.entities.EventRating;
import edu.esprit.entities.EventRatingPK;
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
public class EventRatingController implements Serializable {

    public EventRatingController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventRating eventRating) throws PreexistingEntityException, Exception {
        if (eventRating.getEventRatingPK() == null) {
            eventRating.setEventRatingPK(new EventRatingPK());
        }
        eventRating.getEventRatingPK().setEventIdFkPk(eventRating.getEvents().getEventIdPk());
        eventRating.getEventRatingPK().setUserIdFkPk(eventRating.getUserAccount().getUserIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events events = eventRating.getEvents();
            if (events != null) {
                events = em.getReference(events.getClass(), events.getEventIdPk());
                eventRating.setEvents(events);
            }
            UserAccount userAccount = eventRating.getUserAccount();
            if (userAccount != null) {
                userAccount = em.getReference(userAccount.getClass(), userAccount.getUserIdPk());
                eventRating.setUserAccount(userAccount);
            }
            em.persist(eventRating);
            if (events != null) {
                events.getEventRatingList().add(eventRating);
                events = em.merge(events);
            }
            if (userAccount != null) {
                userAccount.getEventRatingList().add(eventRating);
                userAccount = em.merge(userAccount);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEventRating(eventRating.getEventRatingPK()) != null) {
                throw new PreexistingEntityException("EventRating " + eventRating + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventRating eventRating) throws NonexistentEntityException, Exception {
        eventRating.getEventRatingPK().setEventIdFkPk(eventRating.getEvents().getEventIdPk());
        eventRating.getEventRatingPK().setUserIdFkPk(eventRating.getUserAccount().getUserIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventRating persistentEventRating = em.find(EventRating.class, eventRating.getEventRatingPK());
            Events eventsOld = persistentEventRating.getEvents();
            Events eventsNew = eventRating.getEvents();
            UserAccount userAccountOld = persistentEventRating.getUserAccount();
            UserAccount userAccountNew = eventRating.getUserAccount();
            if (eventsNew != null) {
                eventsNew = em.getReference(eventsNew.getClass(), eventsNew.getEventIdPk());
                eventRating.setEvents(eventsNew);
            }
            if (userAccountNew != null) {
                userAccountNew = em.getReference(userAccountNew.getClass(), userAccountNew.getUserIdPk());
                eventRating.setUserAccount(userAccountNew);
            }
            eventRating = em.merge(eventRating);
            if (eventsOld != null && !eventsOld.equals(eventsNew)) {
                eventsOld.getEventRatingList().remove(eventRating);
                eventsOld = em.merge(eventsOld);
            }
            if (eventsNew != null && !eventsNew.equals(eventsOld)) {
                eventsNew.getEventRatingList().add(eventRating);
                eventsNew = em.merge(eventsNew);
            }
            if (userAccountOld != null && !userAccountOld.equals(userAccountNew)) {
                userAccountOld.getEventRatingList().remove(eventRating);
                userAccountOld = em.merge(userAccountOld);
            }
            if (userAccountNew != null && !userAccountNew.equals(userAccountOld)) {
                userAccountNew.getEventRatingList().add(eventRating);
                userAccountNew = em.merge(userAccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EventRatingPK id = eventRating.getEventRatingPK();
                if (findEventRating(id) == null) {
                    throw new NonexistentEntityException("The eventRating with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EventRatingPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventRating eventRating;
            try {
                eventRating = em.getReference(EventRating.class, id);
                eventRating.getEventRatingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventRating with id " + id + " no longer exists.", enfe);
            }
            Events events = eventRating.getEvents();
            if (events != null) {
                events.getEventRatingList().remove(eventRating);
                events = em.merge(events);
            }
            UserAccount userAccount = eventRating.getUserAccount();
            if (userAccount != null) {
                userAccount.getEventRatingList().remove(eventRating);
                userAccount = em.merge(userAccount);
            }
            em.remove(eventRating);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventRating> findEventRatingEntities() {
        return findEventRatingEntities(true, -1, -1);
    }

    public List<EventRating> findEventRatingEntities(int maxResults, int firstResult) {
        return findEventRatingEntities(false, maxResults, firstResult);
    }

    private List<EventRating> findEventRatingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventRating.class));
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

    public EventRating findEventRating(EventRatingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventRating.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventRatingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventRating> rt = cq.from(EventRating.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
