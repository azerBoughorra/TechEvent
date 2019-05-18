/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.IllegalOrphanException;
import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.EventCategory;
import edu.esprit.entities.EventLocation;
import edu.esprit.entities.UserAccount;
import edu.esprit.entities.EventParticipation;
import java.util.ArrayList;
import java.util.List;
import edu.esprit.entities.EventRating;
import edu.esprit.entities.EventComment;
import edu.esprit.entities.EventSession;
import edu.esprit.entities.Events;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EventsController implements Serializable {

    public EventsController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Events events) {
        if (events.getEventParticipationList() == null) {
            events.setEventParticipationList(new ArrayList<EventParticipation>());
        }
        if (events.getEventRatingList() == null) {
            events.setEventRatingList(new ArrayList<EventRating>());
        }
        if (events.getEventCommentList() == null) {
            events.setEventCommentList(new ArrayList<EventComment>());
        }
        if (events.getEventSessionList() == null) {
            events.setEventSessionList(new ArrayList<EventSession>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventCategory eventCategoryIdFk = events.getEventCategoryIdFk();
            if (eventCategoryIdFk != null) {
                eventCategoryIdFk = em.getReference(eventCategoryIdFk.getClass(), eventCategoryIdFk.getEventCategoryIdPk());
                events.setEventCategoryIdFk(eventCategoryIdFk);
            }
            EventLocation eventLocationIdFk = events.getEventLocationIdFk();
            if (eventLocationIdFk != null) {
                eventLocationIdFk = em.getReference(eventLocationIdFk.getClass(), eventLocationIdFk.getEventLocationIdPk());
                events.setEventLocationIdFk(eventLocationIdFk);
            }
            UserAccount eventOrganisatorIdFk = events.getEventOrganisatorIdFk();
            if (eventOrganisatorIdFk != null) {
                eventOrganisatorIdFk = em.getReference(eventOrganisatorIdFk.getClass(), eventOrganisatorIdFk.getUserIdPk());
                events.setEventOrganisatorIdFk(eventOrganisatorIdFk);
            }
            List<EventParticipation> attachedEventParticipationList = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListEventParticipationToAttach : events.getEventParticipationList()) {
                eventParticipationListEventParticipationToAttach = em.getReference(eventParticipationListEventParticipationToAttach.getClass(), eventParticipationListEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationList.add(eventParticipationListEventParticipationToAttach);
            }
            events.setEventParticipationList(attachedEventParticipationList);
            List<EventRating> attachedEventRatingList = new ArrayList<EventRating>();
            for (EventRating eventRatingListEventRatingToAttach : events.getEventRatingList()) {
                eventRatingListEventRatingToAttach = em.getReference(eventRatingListEventRatingToAttach.getClass(), eventRatingListEventRatingToAttach.getEventRatingPK());
                attachedEventRatingList.add(eventRatingListEventRatingToAttach);
            }
            events.setEventRatingList(attachedEventRatingList);
            List<EventComment> attachedEventCommentList = new ArrayList<EventComment>();
            for (EventComment eventCommentListEventCommentToAttach : events.getEventCommentList()) {
                eventCommentListEventCommentToAttach = em.getReference(eventCommentListEventCommentToAttach.getClass(), eventCommentListEventCommentToAttach.getEventCommentIdPk());
                attachedEventCommentList.add(eventCommentListEventCommentToAttach);
            }
            events.setEventCommentList(attachedEventCommentList);
            List<EventSession> attachedEventSessionList = new ArrayList<EventSession>();
            for (EventSession eventSessionListEventSessionToAttach : events.getEventSessionList()) {
                eventSessionListEventSessionToAttach = em.getReference(eventSessionListEventSessionToAttach.getClass(), eventSessionListEventSessionToAttach.getEventSessionPK());
                attachedEventSessionList.add(eventSessionListEventSessionToAttach);
            }
            events.setEventSessionList(attachedEventSessionList);
            em.persist(events);
            if (eventCategoryIdFk != null) {
                eventCategoryIdFk.getEventsList().add(events);
                eventCategoryIdFk = em.merge(eventCategoryIdFk);
            }
            if (eventLocationIdFk != null) {
                eventLocationIdFk.getEventsList().add(events);
                eventLocationIdFk = em.merge(eventLocationIdFk);
            }
            if (eventOrganisatorIdFk != null) {
                eventOrganisatorIdFk.getEventsList().add(events);
                eventOrganisatorIdFk = em.merge(eventOrganisatorIdFk);
            }
            for (EventParticipation eventParticipationListEventParticipation : events.getEventParticipationList()) {
                Events oldEventsOfEventParticipationListEventParticipation = eventParticipationListEventParticipation.getEvents();
                eventParticipationListEventParticipation.setEvents(events);
                eventParticipationListEventParticipation = em.merge(eventParticipationListEventParticipation);
                if (oldEventsOfEventParticipationListEventParticipation != null) {
                    oldEventsOfEventParticipationListEventParticipation.getEventParticipationList().remove(eventParticipationListEventParticipation);
                    oldEventsOfEventParticipationListEventParticipation = em.merge(oldEventsOfEventParticipationListEventParticipation);
                }
            }
            for (EventRating eventRatingListEventRating : events.getEventRatingList()) {
                Events oldEventsOfEventRatingListEventRating = eventRatingListEventRating.getEvents();
                eventRatingListEventRating.setEvents(events);
                eventRatingListEventRating = em.merge(eventRatingListEventRating);
                if (oldEventsOfEventRatingListEventRating != null) {
                    oldEventsOfEventRatingListEventRating.getEventRatingList().remove(eventRatingListEventRating);
                    oldEventsOfEventRatingListEventRating = em.merge(oldEventsOfEventRatingListEventRating);
                }
            }
            for (EventComment eventCommentListEventComment : events.getEventCommentList()) {
                Events oldEventCommentEventIdFkOfEventCommentListEventComment = eventCommentListEventComment.getEventCommentEventIdFk();
                eventCommentListEventComment.setEventCommentEventIdFk(events);
                eventCommentListEventComment = em.merge(eventCommentListEventComment);
                if (oldEventCommentEventIdFkOfEventCommentListEventComment != null) {
                    oldEventCommentEventIdFkOfEventCommentListEventComment.getEventCommentList().remove(eventCommentListEventComment);
                    oldEventCommentEventIdFkOfEventCommentListEventComment = em.merge(oldEventCommentEventIdFkOfEventCommentListEventComment);
                }
            }
            for (EventSession eventSessionListEventSession : events.getEventSessionList()) {
                Events oldEventsOfEventSessionListEventSession = eventSessionListEventSession.getEvents();
                eventSessionListEventSession.setEvents(events);
                eventSessionListEventSession = em.merge(eventSessionListEventSession);
                if (oldEventsOfEventSessionListEventSession != null) {
                    oldEventsOfEventSessionListEventSession.getEventSessionList().remove(eventSessionListEventSession);
                    oldEventsOfEventSessionListEventSession = em.merge(oldEventsOfEventSessionListEventSession);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Events events) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events persistentEvents = em.find(Events.class, events.getEventIdPk());
            EventCategory eventCategoryIdFkOld = persistentEvents.getEventCategoryIdFk();
            EventCategory eventCategoryIdFkNew = events.getEventCategoryIdFk();
            EventLocation eventLocationIdFkOld = persistentEvents.getEventLocationIdFk();
            EventLocation eventLocationIdFkNew = events.getEventLocationIdFk();
            UserAccount eventOrganisatorIdFkOld = persistentEvents.getEventOrganisatorIdFk();
            UserAccount eventOrganisatorIdFkNew = events.getEventOrganisatorIdFk();
            List<EventParticipation> eventParticipationListOld = persistentEvents.getEventParticipationList();
            List<EventParticipation> eventParticipationListNew = events.getEventParticipationList();
            List<EventRating> eventRatingListOld = persistentEvents.getEventRatingList();
            List<EventRating> eventRatingListNew = events.getEventRatingList();
            List<EventComment> eventCommentListOld = persistentEvents.getEventCommentList();
            List<EventComment> eventCommentListNew = events.getEventCommentList();
            List<EventSession> eventSessionListOld = persistentEvents.getEventSessionList();
            List<EventSession> eventSessionListNew = events.getEventSessionList();
            List<String> illegalOrphanMessages = null;
            for (EventParticipation eventParticipationListOldEventParticipation : eventParticipationListOld) {
                if (!eventParticipationListNew.contains(eventParticipationListOldEventParticipation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventParticipation " + eventParticipationListOldEventParticipation + " since its events field is not nullable.");
                }
            }
            for (EventRating eventRatingListOldEventRating : eventRatingListOld) {
                if (!eventRatingListNew.contains(eventRatingListOldEventRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventRating " + eventRatingListOldEventRating + " since its events field is not nullable.");
                }
            }
            for (EventComment eventCommentListOldEventComment : eventCommentListOld) {
                if (!eventCommentListNew.contains(eventCommentListOldEventComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventComment " + eventCommentListOldEventComment + " since its eventCommentEventIdFk field is not nullable.");
                }
            }
            for (EventSession eventSessionListOldEventSession : eventSessionListOld) {
                if (!eventSessionListNew.contains(eventSessionListOldEventSession)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventSession " + eventSessionListOldEventSession + " since its events field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (eventCategoryIdFkNew != null) {
                eventCategoryIdFkNew = em.getReference(eventCategoryIdFkNew.getClass(), eventCategoryIdFkNew.getEventCategoryIdPk());
                events.setEventCategoryIdFk(eventCategoryIdFkNew);
            }
            if (eventLocationIdFkNew != null) {
                eventLocationIdFkNew = em.getReference(eventLocationIdFkNew.getClass(), eventLocationIdFkNew.getEventLocationIdPk());
                events.setEventLocationIdFk(eventLocationIdFkNew);
            }
            if (eventOrganisatorIdFkNew != null) {
                eventOrganisatorIdFkNew = em.getReference(eventOrganisatorIdFkNew.getClass(), eventOrganisatorIdFkNew.getUserIdPk());
                events.setEventOrganisatorIdFk(eventOrganisatorIdFkNew);
            }
            List<EventParticipation> attachedEventParticipationListNew = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListNewEventParticipationToAttach : eventParticipationListNew) {
                eventParticipationListNewEventParticipationToAttach = em.getReference(eventParticipationListNewEventParticipationToAttach.getClass(), eventParticipationListNewEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationListNew.add(eventParticipationListNewEventParticipationToAttach);
            }
            eventParticipationListNew = attachedEventParticipationListNew;
            events.setEventParticipationList(eventParticipationListNew);
            List<EventRating> attachedEventRatingListNew = new ArrayList<EventRating>();
            for (EventRating eventRatingListNewEventRatingToAttach : eventRatingListNew) {
                eventRatingListNewEventRatingToAttach = em.getReference(eventRatingListNewEventRatingToAttach.getClass(), eventRatingListNewEventRatingToAttach.getEventRatingPK());
                attachedEventRatingListNew.add(eventRatingListNewEventRatingToAttach);
            }
            eventRatingListNew = attachedEventRatingListNew;
            events.setEventRatingList(eventRatingListNew);
            List<EventComment> attachedEventCommentListNew = new ArrayList<EventComment>();
            for (EventComment eventCommentListNewEventCommentToAttach : eventCommentListNew) {
                eventCommentListNewEventCommentToAttach = em.getReference(eventCommentListNewEventCommentToAttach.getClass(), eventCommentListNewEventCommentToAttach.getEventCommentIdPk());
                attachedEventCommentListNew.add(eventCommentListNewEventCommentToAttach);
            }
            eventCommentListNew = attachedEventCommentListNew;
            events.setEventCommentList(eventCommentListNew);
            List<EventSession> attachedEventSessionListNew = new ArrayList<EventSession>();
            for (EventSession eventSessionListNewEventSessionToAttach : eventSessionListNew) {
                eventSessionListNewEventSessionToAttach = em.getReference(eventSessionListNewEventSessionToAttach.getClass(), eventSessionListNewEventSessionToAttach.getEventSessionPK());
                attachedEventSessionListNew.add(eventSessionListNewEventSessionToAttach);
            }
            eventSessionListNew = attachedEventSessionListNew;
            events.setEventSessionList(eventSessionListNew);
            events = em.merge(events);
            if (eventCategoryIdFkOld != null && !eventCategoryIdFkOld.equals(eventCategoryIdFkNew)) {
                eventCategoryIdFkOld.getEventsList().remove(events);
                eventCategoryIdFkOld = em.merge(eventCategoryIdFkOld);
            }
            if (eventCategoryIdFkNew != null && !eventCategoryIdFkNew.equals(eventCategoryIdFkOld)) {
                eventCategoryIdFkNew.getEventsList().add(events);
                eventCategoryIdFkNew = em.merge(eventCategoryIdFkNew);
            }
            if (eventLocationIdFkOld != null && !eventLocationIdFkOld.equals(eventLocationIdFkNew)) {
                eventLocationIdFkOld.getEventsList().remove(events);
                eventLocationIdFkOld = em.merge(eventLocationIdFkOld);
            }
            if (eventLocationIdFkNew != null && !eventLocationIdFkNew.equals(eventLocationIdFkOld)) {
                eventLocationIdFkNew.getEventsList().add(events);
                eventLocationIdFkNew = em.merge(eventLocationIdFkNew);
            }
            if (eventOrganisatorIdFkOld != null && !eventOrganisatorIdFkOld.equals(eventOrganisatorIdFkNew)) {
                eventOrganisatorIdFkOld.getEventsList().remove(events);
                eventOrganisatorIdFkOld = em.merge(eventOrganisatorIdFkOld);
            }
            if (eventOrganisatorIdFkNew != null && !eventOrganisatorIdFkNew.equals(eventOrganisatorIdFkOld)) {
                eventOrganisatorIdFkNew.getEventsList().add(events);
                eventOrganisatorIdFkNew = em.merge(eventOrganisatorIdFkNew);
            }
            for (EventParticipation eventParticipationListNewEventParticipation : eventParticipationListNew) {
                if (!eventParticipationListOld.contains(eventParticipationListNewEventParticipation)) {
                    Events oldEventsOfEventParticipationListNewEventParticipation = eventParticipationListNewEventParticipation.getEvents();
                    eventParticipationListNewEventParticipation.setEvents(events);
                    eventParticipationListNewEventParticipation = em.merge(eventParticipationListNewEventParticipation);
                    if (oldEventsOfEventParticipationListNewEventParticipation != null && !oldEventsOfEventParticipationListNewEventParticipation.equals(events)) {
                        oldEventsOfEventParticipationListNewEventParticipation.getEventParticipationList().remove(eventParticipationListNewEventParticipation);
                        oldEventsOfEventParticipationListNewEventParticipation = em.merge(oldEventsOfEventParticipationListNewEventParticipation);
                    }
                }
            }
            for (EventRating eventRatingListNewEventRating : eventRatingListNew) {
                if (!eventRatingListOld.contains(eventRatingListNewEventRating)) {
                    Events oldEventsOfEventRatingListNewEventRating = eventRatingListNewEventRating.getEvents();
                    eventRatingListNewEventRating.setEvents(events);
                    eventRatingListNewEventRating = em.merge(eventRatingListNewEventRating);
                    if (oldEventsOfEventRatingListNewEventRating != null && !oldEventsOfEventRatingListNewEventRating.equals(events)) {
                        oldEventsOfEventRatingListNewEventRating.getEventRatingList().remove(eventRatingListNewEventRating);
                        oldEventsOfEventRatingListNewEventRating = em.merge(oldEventsOfEventRatingListNewEventRating);
                    }
                }
            }
            for (EventComment eventCommentListNewEventComment : eventCommentListNew) {
                if (!eventCommentListOld.contains(eventCommentListNewEventComment)) {
                    Events oldEventCommentEventIdFkOfEventCommentListNewEventComment = eventCommentListNewEventComment.getEventCommentEventIdFk();
                    eventCommentListNewEventComment.setEventCommentEventIdFk(events);
                    eventCommentListNewEventComment = em.merge(eventCommentListNewEventComment);
                    if (oldEventCommentEventIdFkOfEventCommentListNewEventComment != null && !oldEventCommentEventIdFkOfEventCommentListNewEventComment.equals(events)) {
                        oldEventCommentEventIdFkOfEventCommentListNewEventComment.getEventCommentList().remove(eventCommentListNewEventComment);
                        oldEventCommentEventIdFkOfEventCommentListNewEventComment = em.merge(oldEventCommentEventIdFkOfEventCommentListNewEventComment);
                    }
                }
            }
            for (EventSession eventSessionListNewEventSession : eventSessionListNew) {
                if (!eventSessionListOld.contains(eventSessionListNewEventSession)) {
                    Events oldEventsOfEventSessionListNewEventSession = eventSessionListNewEventSession.getEvents();
                    eventSessionListNewEventSession.setEvents(events);
                    eventSessionListNewEventSession = em.merge(eventSessionListNewEventSession);
                    if (oldEventsOfEventSessionListNewEventSession != null && !oldEventsOfEventSessionListNewEventSession.equals(events)) {
                        oldEventsOfEventSessionListNewEventSession.getEventSessionList().remove(eventSessionListNewEventSession);
                        oldEventsOfEventSessionListNewEventSession = em.merge(oldEventsOfEventSessionListNewEventSession);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = events.getEventIdPk();
                if (findEvents(id) == null) {
                    throw new NonexistentEntityException("The events with id " + id + " no longer exists.");
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
            Events events;
            try {
                events = em.getReference(Events.class, id);
                events.getEventIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The events with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EventParticipation> eventParticipationListOrphanCheck = events.getEventParticipationList();
            for (EventParticipation eventParticipationListOrphanCheckEventParticipation : eventParticipationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Events (" + events + ") cannot be destroyed since the EventParticipation " + eventParticipationListOrphanCheckEventParticipation + " in its eventParticipationList field has a non-nullable events field.");
            }
            List<EventRating> eventRatingListOrphanCheck = events.getEventRatingList();
            for (EventRating eventRatingListOrphanCheckEventRating : eventRatingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Events (" + events + ") cannot be destroyed since the EventRating " + eventRatingListOrphanCheckEventRating + " in its eventRatingList field has a non-nullable events field.");
            }
            List<EventComment> eventCommentListOrphanCheck = events.getEventCommentList();
            for (EventComment eventCommentListOrphanCheckEventComment : eventCommentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Events (" + events + ") cannot be destroyed since the EventComment " + eventCommentListOrphanCheckEventComment + " in its eventCommentList field has a non-nullable eventCommentEventIdFk field.");
            }
            List<EventSession> eventSessionListOrphanCheck = events.getEventSessionList();
            for (EventSession eventSessionListOrphanCheckEventSession : eventSessionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Events (" + events + ") cannot be destroyed since the EventSession " + eventSessionListOrphanCheckEventSession + " in its eventSessionList field has a non-nullable events field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EventCategory eventCategoryIdFk = events.getEventCategoryIdFk();
            if (eventCategoryIdFk != null) {
                eventCategoryIdFk.getEventsList().remove(events);
                eventCategoryIdFk = em.merge(eventCategoryIdFk);
            }
            EventLocation eventLocationIdFk = events.getEventLocationIdFk();
            if (eventLocationIdFk != null) {
                eventLocationIdFk.getEventsList().remove(events);
                eventLocationIdFk = em.merge(eventLocationIdFk);
            }
            UserAccount eventOrganisatorIdFk = events.getEventOrganisatorIdFk();
            if (eventOrganisatorIdFk != null) {
                eventOrganisatorIdFk.getEventsList().remove(events);
                eventOrganisatorIdFk = em.merge(eventOrganisatorIdFk);
            }
            em.remove(events);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Events> findEventsEntities() {
        return findEventsEntities(true, -1, -1);
    }

    public List<Events> findEventsEntities(int maxResults, int firstResult) {
        return findEventsEntities(false, maxResults, firstResult);
    }

    private List<Events> findEventsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Events.class));
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

    public Events findEvents(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Events.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Events> rt = cq.from(Events.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
