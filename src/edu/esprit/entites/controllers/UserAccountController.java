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
import edu.esprit.entities.Entreprise;
import edu.esprit.entities.RoleUser;
import edu.esprit.entities.EventParticipation;
import java.util.ArrayList;
import java.util.List;
import edu.esprit.entities.Report;
import edu.esprit.entities.EventRating;
import edu.esprit.entities.EventComment;
import edu.esprit.entities.Events;
import edu.esprit.entities.UserAccount;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class UserAccountController implements Serializable {

    public UserAccountController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserAccount userAccount) {
        if (userAccount.getEventParticipationList() == null) {
            userAccount.setEventParticipationList(new ArrayList<EventParticipation>());
        }
        if (userAccount.getReportList() == null) {
            userAccount.setReportList(new ArrayList<Report>());
        }
        if (userAccount.getEventRatingList() == null) {
            userAccount.setEventRatingList(new ArrayList<EventRating>());
        }
        if (userAccount.getEventCommentList() == null) {
            userAccount.setEventCommentList(new ArrayList<EventComment>());
        }
        if (userAccount.getEventsList() == null) {
            userAccount.setEventsList(new ArrayList<Events>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entreprise userEntrepriseIdFk = userAccount.getUserEntrepriseIdFk();
            if (userEntrepriseIdFk != null) {
                userEntrepriseIdFk = em.getReference(userEntrepriseIdFk.getClass(), userEntrepriseIdFk.getEntrepriseIdPk());
                userAccount.setUserEntrepriseIdFk(userEntrepriseIdFk);
            }
            RoleUser userRoleIdFk = userAccount.getUserRoleIdFk();
            if (userRoleIdFk != null) {
                userRoleIdFk = em.getReference(userRoleIdFk.getClass(), userRoleIdFk.getRoleIdPk());
                userAccount.setUserRoleIdFk(userRoleIdFk);
            }
            List<EventParticipation> attachedEventParticipationList = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListEventParticipationToAttach : userAccount.getEventParticipationList()) {
                eventParticipationListEventParticipationToAttach = em.getReference(eventParticipationListEventParticipationToAttach.getClass(), eventParticipationListEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationList.add(eventParticipationListEventParticipationToAttach);
            }
            userAccount.setEventParticipationList(attachedEventParticipationList);
            List<Report> attachedReportList = new ArrayList<Report>();
            for (Report reportListReportToAttach : userAccount.getReportList()) {
                reportListReportToAttach = em.getReference(reportListReportToAttach.getClass(), reportListReportToAttach.getReportIdPk());
                attachedReportList.add(reportListReportToAttach);
            }
            userAccount.setReportList(attachedReportList);
            List<EventRating> attachedEventRatingList = new ArrayList<EventRating>();
            for (EventRating eventRatingListEventRatingToAttach : userAccount.getEventRatingList()) {
                eventRatingListEventRatingToAttach = em.getReference(eventRatingListEventRatingToAttach.getClass(), eventRatingListEventRatingToAttach.getEventRatingPK());
                attachedEventRatingList.add(eventRatingListEventRatingToAttach);
            }
            userAccount.setEventRatingList(attachedEventRatingList);
            List<EventComment> attachedEventCommentList = new ArrayList<EventComment>();
            for (EventComment eventCommentListEventCommentToAttach : userAccount.getEventCommentList()) {
                eventCommentListEventCommentToAttach = em.getReference(eventCommentListEventCommentToAttach.getClass(), eventCommentListEventCommentToAttach.getEventCommentIdPk());
                attachedEventCommentList.add(eventCommentListEventCommentToAttach);
            }
            userAccount.setEventCommentList(attachedEventCommentList);
            List<Events> attachedEventsList = new ArrayList<Events>();
            for (Events eventsListEventsToAttach : userAccount.getEventsList()) {
                eventsListEventsToAttach = em.getReference(eventsListEventsToAttach.getClass(), eventsListEventsToAttach.getEventIdPk());
                attachedEventsList.add(eventsListEventsToAttach);
            }
            userAccount.setEventsList(attachedEventsList);
            em.persist(userAccount);
            if (userEntrepriseIdFk != null) {
                userEntrepriseIdFk.getUserAccountList().add(userAccount);
                userEntrepriseIdFk = em.merge(userEntrepriseIdFk);
            }
            if (userRoleIdFk != null) {
                userRoleIdFk.getUserAccountList().add(userAccount);
                userRoleIdFk = em.merge(userRoleIdFk);
            }
            for (EventParticipation eventParticipationListEventParticipation : userAccount.getEventParticipationList()) {
                UserAccount oldUserAccountOfEventParticipationListEventParticipation = eventParticipationListEventParticipation.getUserAccount();
                eventParticipationListEventParticipation.setUserAccount(userAccount);
                eventParticipationListEventParticipation = em.merge(eventParticipationListEventParticipation);
                if (oldUserAccountOfEventParticipationListEventParticipation != null) {
                    oldUserAccountOfEventParticipationListEventParticipation.getEventParticipationList().remove(eventParticipationListEventParticipation);
                    oldUserAccountOfEventParticipationListEventParticipation = em.merge(oldUserAccountOfEventParticipationListEventParticipation);
                }
            }
            for (Report reportListReport : userAccount.getReportList()) {
                UserAccount oldReporterIdFkOfReportListReport = reportListReport.getReporterIdFk();
                reportListReport.setReporterIdFk(userAccount);
                reportListReport = em.merge(reportListReport);
                if (oldReporterIdFkOfReportListReport != null) {
                    oldReporterIdFkOfReportListReport.getReportList().remove(reportListReport);
                    oldReporterIdFkOfReportListReport = em.merge(oldReporterIdFkOfReportListReport);
                }
            }
            for (EventRating eventRatingListEventRating : userAccount.getEventRatingList()) {
                UserAccount oldUserAccountOfEventRatingListEventRating = eventRatingListEventRating.getUserAccount();
                eventRatingListEventRating.setUserAccount(userAccount);
                eventRatingListEventRating = em.merge(eventRatingListEventRating);
                if (oldUserAccountOfEventRatingListEventRating != null) {
                    oldUserAccountOfEventRatingListEventRating.getEventRatingList().remove(eventRatingListEventRating);
                    oldUserAccountOfEventRatingListEventRating = em.merge(oldUserAccountOfEventRatingListEventRating);
                }
            }
            for (EventComment eventCommentListEventComment : userAccount.getEventCommentList()) {
                UserAccount oldEventCommentUserIdFkOfEventCommentListEventComment = eventCommentListEventComment.getEventCommentUserIdFk();
                eventCommentListEventComment.setEventCommentUserIdFk(userAccount);
                eventCommentListEventComment = em.merge(eventCommentListEventComment);
                if (oldEventCommentUserIdFkOfEventCommentListEventComment != null) {
                    oldEventCommentUserIdFkOfEventCommentListEventComment.getEventCommentList().remove(eventCommentListEventComment);
                    oldEventCommentUserIdFkOfEventCommentListEventComment = em.merge(oldEventCommentUserIdFkOfEventCommentListEventComment);
                }
            }
            for (Events eventsListEvents : userAccount.getEventsList()) {
                UserAccount oldEventOrganisatorIdFkOfEventsListEvents = eventsListEvents.getEventOrganisatorIdFk();
                eventsListEvents.setEventOrganisatorIdFk(userAccount);
                eventsListEvents = em.merge(eventsListEvents);
                if (oldEventOrganisatorIdFkOfEventsListEvents != null) {
                    oldEventOrganisatorIdFkOfEventsListEvents.getEventsList().remove(eventsListEvents);
                    oldEventOrganisatorIdFkOfEventsListEvents = em.merge(oldEventOrganisatorIdFkOfEventsListEvents);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserAccount userAccount) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount persistentUserAccount = em.find(UserAccount.class, userAccount.getUserIdPk());
            Entreprise userEntrepriseIdFkOld = persistentUserAccount.getUserEntrepriseIdFk();
            Entreprise userEntrepriseIdFkNew = userAccount.getUserEntrepriseIdFk();
            RoleUser userRoleIdFkOld = persistentUserAccount.getUserRoleIdFk();
            RoleUser userRoleIdFkNew = userAccount.getUserRoleIdFk();
            List<EventParticipation> eventParticipationListOld = persistentUserAccount.getEventParticipationList();
            List<EventParticipation> eventParticipationListNew = userAccount.getEventParticipationList();
            List<Report> reportListOld = persistentUserAccount.getReportList();
            List<Report> reportListNew = userAccount.getReportList();
            List<EventRating> eventRatingListOld = persistentUserAccount.getEventRatingList();
            List<EventRating> eventRatingListNew = userAccount.getEventRatingList();
            List<EventComment> eventCommentListOld = persistentUserAccount.getEventCommentList();
            List<EventComment> eventCommentListNew = userAccount.getEventCommentList();
            List<Events> eventsListOld = persistentUserAccount.getEventsList();
            List<Events> eventsListNew = userAccount.getEventsList();
            List<String> illegalOrphanMessages = null;
            for (EventParticipation eventParticipationListOldEventParticipation : eventParticipationListOld) {
                if (!eventParticipationListNew.contains(eventParticipationListOldEventParticipation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventParticipation " + eventParticipationListOldEventParticipation + " since its userAccount field is not nullable.");
                }
            }
            for (Report reportListOldReport : reportListOld) {
                if (!reportListNew.contains(reportListOldReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Report " + reportListOldReport + " since its reporterIdFk field is not nullable.");
                }
            }
            for (EventRating eventRatingListOldEventRating : eventRatingListOld) {
                if (!eventRatingListNew.contains(eventRatingListOldEventRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventRating " + eventRatingListOldEventRating + " since its userAccount field is not nullable.");
                }
            }
            for (EventComment eventCommentListOldEventComment : eventCommentListOld) {
                if (!eventCommentListNew.contains(eventCommentListOldEventComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventComment " + eventCommentListOldEventComment + " since its eventCommentUserIdFk field is not nullable.");
                }
            }
            for (Events eventsListOldEvents : eventsListOld) {
                if (!eventsListNew.contains(eventsListOldEvents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Events " + eventsListOldEvents + " since its eventOrganisatorIdFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userEntrepriseIdFkNew != null) {
                userEntrepriseIdFkNew = em.getReference(userEntrepriseIdFkNew.getClass(), userEntrepriseIdFkNew.getEntrepriseIdPk());
                userAccount.setUserEntrepriseIdFk(userEntrepriseIdFkNew);
            }
            if (userRoleIdFkNew != null) {
                userRoleIdFkNew = em.getReference(userRoleIdFkNew.getClass(), userRoleIdFkNew.getRoleIdPk());
                userAccount.setUserRoleIdFk(userRoleIdFkNew);
            }
            List<EventParticipation> attachedEventParticipationListNew = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListNewEventParticipationToAttach : eventParticipationListNew) {
                eventParticipationListNewEventParticipationToAttach = em.getReference(eventParticipationListNewEventParticipationToAttach.getClass(), eventParticipationListNewEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationListNew.add(eventParticipationListNewEventParticipationToAttach);
            }
            eventParticipationListNew = attachedEventParticipationListNew;
            userAccount.setEventParticipationList(eventParticipationListNew);
            List<Report> attachedReportListNew = new ArrayList<Report>();
            for (Report reportListNewReportToAttach : reportListNew) {
                reportListNewReportToAttach = em.getReference(reportListNewReportToAttach.getClass(), reportListNewReportToAttach.getReportIdPk());
                attachedReportListNew.add(reportListNewReportToAttach);
            }
            reportListNew = attachedReportListNew;
            userAccount.setReportList(reportListNew);
            List<EventRating> attachedEventRatingListNew = new ArrayList<EventRating>();
            for (EventRating eventRatingListNewEventRatingToAttach : eventRatingListNew) {
                eventRatingListNewEventRatingToAttach = em.getReference(eventRatingListNewEventRatingToAttach.getClass(), eventRatingListNewEventRatingToAttach.getEventRatingPK());
                attachedEventRatingListNew.add(eventRatingListNewEventRatingToAttach);
            }
            eventRatingListNew = attachedEventRatingListNew;
            userAccount.setEventRatingList(eventRatingListNew);
            List<EventComment> attachedEventCommentListNew = new ArrayList<EventComment>();
            for (EventComment eventCommentListNewEventCommentToAttach : eventCommentListNew) {
                eventCommentListNewEventCommentToAttach = em.getReference(eventCommentListNewEventCommentToAttach.getClass(), eventCommentListNewEventCommentToAttach.getEventCommentIdPk());
                attachedEventCommentListNew.add(eventCommentListNewEventCommentToAttach);
            }
            eventCommentListNew = attachedEventCommentListNew;
            userAccount.setEventCommentList(eventCommentListNew);
            List<Events> attachedEventsListNew = new ArrayList<Events>();
            for (Events eventsListNewEventsToAttach : eventsListNew) {
                eventsListNewEventsToAttach = em.getReference(eventsListNewEventsToAttach.getClass(), eventsListNewEventsToAttach.getEventIdPk());
                attachedEventsListNew.add(eventsListNewEventsToAttach);
            }
            eventsListNew = attachedEventsListNew;
            userAccount.setEventsList(eventsListNew);
            userAccount = em.merge(userAccount);
            if (userEntrepriseIdFkOld != null && !userEntrepriseIdFkOld.equals(userEntrepriseIdFkNew)) {
                userEntrepriseIdFkOld.getUserAccountList().remove(userAccount);
                userEntrepriseIdFkOld = em.merge(userEntrepriseIdFkOld);
            }
            if (userEntrepriseIdFkNew != null && !userEntrepriseIdFkNew.equals(userEntrepriseIdFkOld)) {
                userEntrepriseIdFkNew.getUserAccountList().add(userAccount);
                userEntrepriseIdFkNew = em.merge(userEntrepriseIdFkNew);
            }
            if (userRoleIdFkOld != null && !userRoleIdFkOld.equals(userRoleIdFkNew)) {
                userRoleIdFkOld.getUserAccountList().remove(userAccount);
                userRoleIdFkOld = em.merge(userRoleIdFkOld);
            }
            if (userRoleIdFkNew != null && !userRoleIdFkNew.equals(userRoleIdFkOld)) {
                userRoleIdFkNew.getUserAccountList().add(userAccount);
                userRoleIdFkNew = em.merge(userRoleIdFkNew);
            }
            for (EventParticipation eventParticipationListNewEventParticipation : eventParticipationListNew) {
                if (!eventParticipationListOld.contains(eventParticipationListNewEventParticipation)) {
                    UserAccount oldUserAccountOfEventParticipationListNewEventParticipation = eventParticipationListNewEventParticipation.getUserAccount();
                    eventParticipationListNewEventParticipation.setUserAccount(userAccount);
                    eventParticipationListNewEventParticipation = em.merge(eventParticipationListNewEventParticipation);
                    if (oldUserAccountOfEventParticipationListNewEventParticipation != null && !oldUserAccountOfEventParticipationListNewEventParticipation.equals(userAccount)) {
                        oldUserAccountOfEventParticipationListNewEventParticipation.getEventParticipationList().remove(eventParticipationListNewEventParticipation);
                        oldUserAccountOfEventParticipationListNewEventParticipation = em.merge(oldUserAccountOfEventParticipationListNewEventParticipation);
                    }
                }
            }
            for (Report reportListNewReport : reportListNew) {
                if (!reportListOld.contains(reportListNewReport)) {
                    UserAccount oldReporterIdFkOfReportListNewReport = reportListNewReport.getReporterIdFk();
                    reportListNewReport.setReporterIdFk(userAccount);
                    reportListNewReport = em.merge(reportListNewReport);
                    if (oldReporterIdFkOfReportListNewReport != null && !oldReporterIdFkOfReportListNewReport.equals(userAccount)) {
                        oldReporterIdFkOfReportListNewReport.getReportList().remove(reportListNewReport);
                        oldReporterIdFkOfReportListNewReport = em.merge(oldReporterIdFkOfReportListNewReport);
                    }
                }
            }
            for (EventRating eventRatingListNewEventRating : eventRatingListNew) {
                if (!eventRatingListOld.contains(eventRatingListNewEventRating)) {
                    UserAccount oldUserAccountOfEventRatingListNewEventRating = eventRatingListNewEventRating.getUserAccount();
                    eventRatingListNewEventRating.setUserAccount(userAccount);
                    eventRatingListNewEventRating = em.merge(eventRatingListNewEventRating);
                    if (oldUserAccountOfEventRatingListNewEventRating != null && !oldUserAccountOfEventRatingListNewEventRating.equals(userAccount)) {
                        oldUserAccountOfEventRatingListNewEventRating.getEventRatingList().remove(eventRatingListNewEventRating);
                        oldUserAccountOfEventRatingListNewEventRating = em.merge(oldUserAccountOfEventRatingListNewEventRating);
                    }
                }
            }
            for (EventComment eventCommentListNewEventComment : eventCommentListNew) {
                if (!eventCommentListOld.contains(eventCommentListNewEventComment)) {
                    UserAccount oldEventCommentUserIdFkOfEventCommentListNewEventComment = eventCommentListNewEventComment.getEventCommentUserIdFk();
                    eventCommentListNewEventComment.setEventCommentUserIdFk(userAccount);
                    eventCommentListNewEventComment = em.merge(eventCommentListNewEventComment);
                    if (oldEventCommentUserIdFkOfEventCommentListNewEventComment != null && !oldEventCommentUserIdFkOfEventCommentListNewEventComment.equals(userAccount)) {
                        oldEventCommentUserIdFkOfEventCommentListNewEventComment.getEventCommentList().remove(eventCommentListNewEventComment);
                        oldEventCommentUserIdFkOfEventCommentListNewEventComment = em.merge(oldEventCommentUserIdFkOfEventCommentListNewEventComment);
                    }
                }
            }
            for (Events eventsListNewEvents : eventsListNew) {
                if (!eventsListOld.contains(eventsListNewEvents)) {
                    UserAccount oldEventOrganisatorIdFkOfEventsListNewEvents = eventsListNewEvents.getEventOrganisatorIdFk();
                    eventsListNewEvents.setEventOrganisatorIdFk(userAccount);
                    eventsListNewEvents = em.merge(eventsListNewEvents);
                    if (oldEventOrganisatorIdFkOfEventsListNewEvents != null && !oldEventOrganisatorIdFkOfEventsListNewEvents.equals(userAccount)) {
                        oldEventOrganisatorIdFkOfEventsListNewEvents.getEventsList().remove(eventsListNewEvents);
                        oldEventOrganisatorIdFkOfEventsListNewEvents = em.merge(oldEventOrganisatorIdFkOfEventsListNewEvents);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userAccount.getUserIdPk();
                if (findUserAccount(id) == null) {
                    throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.");
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
            UserAccount userAccount;
            try {
                userAccount = em.getReference(UserAccount.class, id);
                userAccount.getUserIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EventParticipation> eventParticipationListOrphanCheck = userAccount.getEventParticipationList();
            for (EventParticipation eventParticipationListOrphanCheckEventParticipation : eventParticipationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the EventParticipation " + eventParticipationListOrphanCheckEventParticipation + " in its eventParticipationList field has a non-nullable userAccount field.");
            }
            List<Report> reportListOrphanCheck = userAccount.getReportList();
            for (Report reportListOrphanCheckReport : reportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the Report " + reportListOrphanCheckReport + " in its reportList field has a non-nullable reporterIdFk field.");
            }
            List<EventRating> eventRatingListOrphanCheck = userAccount.getEventRatingList();
            for (EventRating eventRatingListOrphanCheckEventRating : eventRatingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the EventRating " + eventRatingListOrphanCheckEventRating + " in its eventRatingList field has a non-nullable userAccount field.");
            }
            List<EventComment> eventCommentListOrphanCheck = userAccount.getEventCommentList();
            for (EventComment eventCommentListOrphanCheckEventComment : eventCommentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the EventComment " + eventCommentListOrphanCheckEventComment + " in its eventCommentList field has a non-nullable eventCommentUserIdFk field.");
            }
            List<Events> eventsListOrphanCheck = userAccount.getEventsList();
            for (Events eventsListOrphanCheckEvents : eventsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the Events " + eventsListOrphanCheckEvents + " in its eventsList field has a non-nullable eventOrganisatorIdFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entreprise userEntrepriseIdFk = userAccount.getUserEntrepriseIdFk();
            if (userEntrepriseIdFk != null) {
                userEntrepriseIdFk.getUserAccountList().remove(userAccount);
                userEntrepriseIdFk = em.merge(userEntrepriseIdFk);
            }
            RoleUser userRoleIdFk = userAccount.getUserRoleIdFk();
            if (userRoleIdFk != null) {
                userRoleIdFk.getUserAccountList().remove(userAccount);
                userRoleIdFk = em.merge(userRoleIdFk);
            }
            em.remove(userAccount);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserAccount> findUserAccountEntities() {
        return findUserAccountEntities(true, -1, -1);
    }

    public List<UserAccount> findUserAccountEntities(int maxResults, int firstResult) {
        return findUserAccountEntities(false, maxResults, firstResult);
    }

    private List<UserAccount> findUserAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserAccount.class));
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

    public UserAccount findUserAccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserAccount.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserAccount> rt = cq.from(UserAccount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
