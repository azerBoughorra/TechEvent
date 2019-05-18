/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.IllegalOrphanException;
import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.EventLocation;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.Events;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EventLocationController implements Serializable {

    public EventLocationController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventLocation eventLocation) {
        if (eventLocation.getEventsList() == null) {
            eventLocation.setEventsList(new ArrayList<Events>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Events> attachedEventsList = new ArrayList<Events>();
            for (Events eventsListEventsToAttach : eventLocation.getEventsList()) {
                eventsListEventsToAttach = em.getReference(eventsListEventsToAttach.getClass(), eventsListEventsToAttach.getEventIdPk());
                attachedEventsList.add(eventsListEventsToAttach);
            }
            eventLocation.setEventsList(attachedEventsList);
            em.persist(eventLocation);
            for (Events eventsListEvents : eventLocation.getEventsList()) {
                EventLocation oldEventLocationIdFkOfEventsListEvents = eventsListEvents.getEventLocationIdFk();
                eventsListEvents.setEventLocationIdFk(eventLocation);
                eventsListEvents = em.merge(eventsListEvents);
                if (oldEventLocationIdFkOfEventsListEvents != null) {
                    oldEventLocationIdFkOfEventsListEvents.getEventsList().remove(eventsListEvents);
                    oldEventLocationIdFkOfEventsListEvents = em.merge(oldEventLocationIdFkOfEventsListEvents);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventLocation eventLocation) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventLocation persistentEventLocation = em.find(EventLocation.class, eventLocation.getEventLocationIdPk());
            List<Events> eventsListOld = persistentEventLocation.getEventsList();
            List<Events> eventsListNew = eventLocation.getEventsList();
            List<String> illegalOrphanMessages = null;
            for (Events eventsListOldEvents : eventsListOld) {
                if (!eventsListNew.contains(eventsListOldEvents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Events " + eventsListOldEvents + " since its eventLocationIdFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Events> attachedEventsListNew = new ArrayList<Events>();
            for (Events eventsListNewEventsToAttach : eventsListNew) {
                eventsListNewEventsToAttach = em.getReference(eventsListNewEventsToAttach.getClass(), eventsListNewEventsToAttach.getEventIdPk());
                attachedEventsListNew.add(eventsListNewEventsToAttach);
            }
            eventsListNew = attachedEventsListNew;
            eventLocation.setEventsList(eventsListNew);
            eventLocation = em.merge(eventLocation);
            for (Events eventsListNewEvents : eventsListNew) {
                if (!eventsListOld.contains(eventsListNewEvents)) {
                    EventLocation oldEventLocationIdFkOfEventsListNewEvents = eventsListNewEvents.getEventLocationIdFk();
                    eventsListNewEvents.setEventLocationIdFk(eventLocation);
                    eventsListNewEvents = em.merge(eventsListNewEvents);
                    if (oldEventLocationIdFkOfEventsListNewEvents != null && !oldEventLocationIdFkOfEventsListNewEvents.equals(eventLocation)) {
                        oldEventLocationIdFkOfEventsListNewEvents.getEventsList().remove(eventsListNewEvents);
                        oldEventLocationIdFkOfEventsListNewEvents = em.merge(oldEventLocationIdFkOfEventsListNewEvents);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventLocation.getEventLocationIdPk();
                if (findEventLocation(id) == null) {
                    throw new NonexistentEntityException("The eventLocation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventLocation eventLocation;
            try {
                eventLocation = em.getReference(EventLocation.class, id);
                eventLocation.getEventLocationIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventLocation with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Events> eventsListOrphanCheck = eventLocation.getEventsList();
            for (Events eventsListOrphanCheckEvents : eventsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EventLocation (" + eventLocation + ") cannot be destroyed since the Events " + eventsListOrphanCheckEvents + " in its eventsList field has a non-nullable eventLocationIdFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(eventLocation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventLocation> findEventLocationEntities() {
        return findEventLocationEntities(true, -1, -1);
    }

    public List<EventLocation> findEventLocationEntities(int maxResults, int firstResult) {
        return findEventLocationEntities(false, maxResults, firstResult);
    }

    private List<EventLocation> findEventLocationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventLocation.class));
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

    public EventLocation findEventLocation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventLocation.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventLocationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventLocation> rt = cq.from(EventLocation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
