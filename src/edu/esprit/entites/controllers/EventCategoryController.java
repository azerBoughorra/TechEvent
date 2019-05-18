/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.IllegalOrphanException;
import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.EventCategory;
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
public class EventCategoryController implements Serializable {

    public EventCategoryController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventCategory eventCategory) {
        if (eventCategory.getEventsList() == null) {
            eventCategory.setEventsList(new ArrayList<Events>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Events> attachedEventsList = new ArrayList<Events>();
            for (Events eventsListEventsToAttach : eventCategory.getEventsList()) {
                eventsListEventsToAttach = em.getReference(eventsListEventsToAttach.getClass(), eventsListEventsToAttach.getEventIdPk());
                attachedEventsList.add(eventsListEventsToAttach);
            }
            eventCategory.setEventsList(attachedEventsList);
            em.persist(eventCategory);
            for (Events eventsListEvents : eventCategory.getEventsList()) {
                EventCategory oldEventCategoryIdFkOfEventsListEvents = eventsListEvents.getEventCategoryIdFk();
                eventsListEvents.setEventCategoryIdFk(eventCategory);
                eventsListEvents = em.merge(eventsListEvents);
                if (oldEventCategoryIdFkOfEventsListEvents != null) {
                    oldEventCategoryIdFkOfEventsListEvents.getEventsList().remove(eventsListEvents);
                    oldEventCategoryIdFkOfEventsListEvents = em.merge(oldEventCategoryIdFkOfEventsListEvents);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventCategory eventCategory) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventCategory persistentEventCategory = em.find(EventCategory.class, eventCategory.getEventCategoryIdPk());
            List<Events> eventsListOld = persistentEventCategory.getEventsList();
            List<Events> eventsListNew = eventCategory.getEventsList();
            List<String> illegalOrphanMessages = null;
            for (Events eventsListOldEvents : eventsListOld) {
                if (!eventsListNew.contains(eventsListOldEvents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Events " + eventsListOldEvents + " since its eventCategoryIdFk field is not nullable.");
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
            eventCategory.setEventsList(eventsListNew);
            eventCategory = em.merge(eventCategory);
            for (Events eventsListNewEvents : eventsListNew) {
                if (!eventsListOld.contains(eventsListNewEvents)) {
                    EventCategory oldEventCategoryIdFkOfEventsListNewEvents = eventsListNewEvents.getEventCategoryIdFk();
                    eventsListNewEvents.setEventCategoryIdFk(eventCategory);
                    eventsListNewEvents = em.merge(eventsListNewEvents);
                    if (oldEventCategoryIdFkOfEventsListNewEvents != null && !oldEventCategoryIdFkOfEventsListNewEvents.equals(eventCategory)) {
                        oldEventCategoryIdFkOfEventsListNewEvents.getEventsList().remove(eventsListNewEvents);
                        oldEventCategoryIdFkOfEventsListNewEvents = em.merge(oldEventCategoryIdFkOfEventsListNewEvents);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventCategory.getEventCategoryIdPk();
                if (findEventCategory(id) == null) {
                    throw new NonexistentEntityException("The eventCategory with id " + id + " no longer exists.");
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
            EventCategory eventCategory;
            try {
                eventCategory = em.getReference(EventCategory.class, id);
                eventCategory.getEventCategoryIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventCategory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Events> eventsListOrphanCheck = eventCategory.getEventsList();
            for (Events eventsListOrphanCheckEvents : eventsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EventCategory (" + eventCategory + ") cannot be destroyed since the Events " + eventsListOrphanCheckEvents + " in its eventsList field has a non-nullable eventCategoryIdFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(eventCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventCategory> findEventCategoryEntities() {
        return findEventCategoryEntities(true, -1, -1);
    }

    public List<EventCategory> findEventCategoryEntities(int maxResults, int firstResult) {
        return findEventCategoryEntities(false, maxResults, firstResult);
    }

    private List<EventCategory> findEventCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventCategory.class));
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

    public EventCategory findEventCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventCategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventCategory> rt = cq.from(EventCategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
