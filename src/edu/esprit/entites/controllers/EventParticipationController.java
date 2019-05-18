/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entites.controllers.exceptions.PreexistingEntityException;
import edu.esprit.entities.EventParticipation;
import edu.esprit.entities.EventParticipationPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.Events;
import edu.esprit.entities.RoleParticipation;
import edu.esprit.entities.UserAccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EventParticipationController implements Serializable {

    public EventParticipationController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventParticipation eventParticipation) throws PreexistingEntityException, Exception {
        if (eventParticipation.getEventParticipationPK() == null) {
            eventParticipation.setEventParticipationPK(new EventParticipationPK());
        }
        eventParticipation.getEventParticipationPK().setEventIdFkPk(eventParticipation.getEvents().getEventIdPk());
        eventParticipation.getEventParticipationPK().setUserIdFkPk(eventParticipation.getUserAccount().getUserIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events events = eventParticipation.getEvents();
            if (events != null) {
                events = em.getReference(events.getClass(), events.getEventIdPk());
                eventParticipation.setEvents(events);
            }
            RoleParticipation roleParticipationFk = eventParticipation.getRoleParticipationFk();
            if (roleParticipationFk != null) {
                roleParticipationFk = em.getReference(roleParticipationFk.getClass(), roleParticipationFk.getRoleParticipationId());
                eventParticipation.setRoleParticipationFk(roleParticipationFk);
            }
            UserAccount userAccount = eventParticipation.getUserAccount();
            if (userAccount != null) {
                userAccount = em.getReference(userAccount.getClass(), userAccount.getUserIdPk());
                eventParticipation.setUserAccount(userAccount);
            }
            em.persist(eventParticipation);
            if (events != null) {
                events.getEventParticipationList().add(eventParticipation);
                events = em.merge(events);
            }
            if (roleParticipationFk != null) {
                roleParticipationFk.getEventParticipationList().add(eventParticipation);
                roleParticipationFk = em.merge(roleParticipationFk);
            }
            if (userAccount != null) {
                userAccount.getEventParticipationList().add(eventParticipation);
                userAccount = em.merge(userAccount);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEventParticipation(eventParticipation.getEventParticipationPK()) != null) {
                throw new PreexistingEntityException("EventParticipation " + eventParticipation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventParticipation eventParticipation) throws NonexistentEntityException, Exception {
        eventParticipation.getEventParticipationPK().setEventIdFkPk(eventParticipation.getEvents().getEventIdPk());
        eventParticipation.getEventParticipationPK().setUserIdFkPk(eventParticipation.getUserAccount().getUserIdPk());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventParticipation persistentEventParticipation = em.find(EventParticipation.class, eventParticipation.getEventParticipationPK());
            Events eventsOld = persistentEventParticipation.getEvents();
            Events eventsNew = eventParticipation.getEvents();
            RoleParticipation roleParticipationFkOld = persistentEventParticipation.getRoleParticipationFk();
            RoleParticipation roleParticipationFkNew = eventParticipation.getRoleParticipationFk();
            UserAccount userAccountOld = persistentEventParticipation.getUserAccount();
            UserAccount userAccountNew = eventParticipation.getUserAccount();
            if (eventsNew != null) {
                eventsNew = em.getReference(eventsNew.getClass(), eventsNew.getEventIdPk());
                eventParticipation.setEvents(eventsNew);
            }
            if (roleParticipationFkNew != null) {
                roleParticipationFkNew = em.getReference(roleParticipationFkNew.getClass(), roleParticipationFkNew.getRoleParticipationId());
                eventParticipation.setRoleParticipationFk(roleParticipationFkNew);
            }
            if (userAccountNew != null) {
                userAccountNew = em.getReference(userAccountNew.getClass(), userAccountNew.getUserIdPk());
                eventParticipation.setUserAccount(userAccountNew);
            }
            eventParticipation = em.merge(eventParticipation);
            if (eventsOld != null && !eventsOld.equals(eventsNew)) {
                eventsOld.getEventParticipationList().remove(eventParticipation);
                eventsOld = em.merge(eventsOld);
            }
            if (eventsNew != null && !eventsNew.equals(eventsOld)) {
                eventsNew.getEventParticipationList().add(eventParticipation);
                eventsNew = em.merge(eventsNew);
            }
            if (roleParticipationFkOld != null && !roleParticipationFkOld.equals(roleParticipationFkNew)) {
                roleParticipationFkOld.getEventParticipationList().remove(eventParticipation);
                roleParticipationFkOld = em.merge(roleParticipationFkOld);
            }
            if (roleParticipationFkNew != null && !roleParticipationFkNew.equals(roleParticipationFkOld)) {
                roleParticipationFkNew.getEventParticipationList().add(eventParticipation);
                roleParticipationFkNew = em.merge(roleParticipationFkNew);
            }
            if (userAccountOld != null && !userAccountOld.equals(userAccountNew)) {
                userAccountOld.getEventParticipationList().remove(eventParticipation);
                userAccountOld = em.merge(userAccountOld);
            }
            if (userAccountNew != null && !userAccountNew.equals(userAccountOld)) {
                userAccountNew.getEventParticipationList().add(eventParticipation);
                userAccountNew = em.merge(userAccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EventParticipationPK id = eventParticipation.getEventParticipationPK();
                if (findEventParticipation(id) == null) {
                    throw new NonexistentEntityException("The eventParticipation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EventParticipationPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventParticipation eventParticipation;
            try {
                eventParticipation = em.getReference(EventParticipation.class, id);
                eventParticipation.getEventParticipationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventParticipation with id " + id + " no longer exists.", enfe);
            }
            Events events = eventParticipation.getEvents();
            if (events != null) {
                events.getEventParticipationList().remove(eventParticipation);
                events = em.merge(events);
            }
            RoleParticipation roleParticipationFk = eventParticipation.getRoleParticipationFk();
            if (roleParticipationFk != null) {
                roleParticipationFk.getEventParticipationList().remove(eventParticipation);
                roleParticipationFk = em.merge(roleParticipationFk);
            }
            UserAccount userAccount = eventParticipation.getUserAccount();
            if (userAccount != null) {
                userAccount.getEventParticipationList().remove(eventParticipation);
                userAccount = em.merge(userAccount);
            }
            em.remove(eventParticipation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventParticipation> findEventParticipationEntities() {
        return findEventParticipationEntities(true, -1, -1);
    }

    public List<EventParticipation> findEventParticipationEntities(int maxResults, int firstResult) {
        return findEventParticipationEntities(false, maxResults, firstResult);
    }

    private List<EventParticipation> findEventParticipationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventParticipation.class));
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

    public EventParticipation findEventParticipation(EventParticipationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventParticipation.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventParticipationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventParticipation> rt = cq.from(EventParticipation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
