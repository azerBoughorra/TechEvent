/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entites.controllers.exceptions.PreexistingEntityException;
import edu.esprit.entities.EventSession;
import edu.esprit.entities.EventSessionPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.Events;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EventSessionController implements Serializable {

    public EventSessionController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventSession eventSession) throws PreexistingEntityException, Exception {
        if (eventSession.getEventSessionPK() == null) {
            eventSession.setEventSessionPK(new EventSessionPK());
        }
        eventSession.getEventSessionPK().setEventSessionIdFkPk(eventSession.getEvents().getEventIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events events = eventSession.getEvents();
            if (events != null) {
                events = em.getReference(events.getClass(), events.getEventIdPk());
                eventSession.setEvents(events);
            }
            em.persist(eventSession);
            if (events != null) {
                events.getEventSessionList().add(eventSession);
                events = em.merge(events);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEventSession(eventSession.getEventSessionPK()) != null) {
                throw new PreexistingEntityException("EventSession " + eventSession + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventSession eventSession) throws NonexistentEntityException, Exception {
        eventSession.getEventSessionPK().setEventSessionIdFkPk(eventSession.getEvents().getEventIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventSession persistentEventSession = em.find(EventSession.class, eventSession.getEventSessionPK());
            Events eventsOld = persistentEventSession.getEvents();
            Events eventsNew = eventSession.getEvents();
            if (eventsNew != null) {
                eventsNew = em.getReference(eventsNew.getClass(), eventsNew.getEventIdPk());
                eventSession.setEvents(eventsNew);
            }
            eventSession = em.merge(eventSession);
            if (eventsOld != null && !eventsOld.equals(eventsNew)) {
                eventsOld.getEventSessionList().remove(eventSession);
                eventsOld = em.merge(eventsOld);
            }
            if (eventsNew != null && !eventsNew.equals(eventsOld)) {
                eventsNew.getEventSessionList().add(eventSession);
                eventsNew = em.merge(eventsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EventSessionPK id = eventSession.getEventSessionPK();
                if (findEventSession(id) == null) {
                    throw new NonexistentEntityException("The eventSession with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EventSessionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventSession eventSession;
            try {
                eventSession = em.getReference(EventSession.class, id);
                eventSession.getEventSessionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventSession with id " + id + " no longer exists.", enfe);
            }
            Events events = eventSession.getEvents();
            if (events != null) {
                events.getEventSessionList().remove(eventSession);
                events = em.merge(events);
            }
            em.remove(eventSession);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventSession> findEventSessionEntities() {
        return findEventSessionEntities(true, -1, -1);
    }

    public List<EventSession> findEventSessionEntities(int maxResults, int firstResult) {
        return findEventSessionEntities(false, maxResults, firstResult);
    }

    private List<EventSession> findEventSessionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventSession.class));
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

    public EventSession findEventSession(EventSessionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventSession.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventSessionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventSession> rt = cq.from(EventSession.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
