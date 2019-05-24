/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.EventParticipation;
import edu.esprit.entities.RoleParticipation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class RoleParticipationController implements Serializable {

    public RoleParticipationController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RoleParticipation roleParticipation) {
        if (roleParticipation.getEventParticipationList() == null) {
            roleParticipation.setEventParticipationList(new ArrayList<EventParticipation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EventParticipation> attachedEventParticipationList = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListEventParticipationToAttach : roleParticipation.getEventParticipationList()) {
                eventParticipationListEventParticipationToAttach = em.getReference(eventParticipationListEventParticipationToAttach.getClass(), eventParticipationListEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationList.add(eventParticipationListEventParticipationToAttach);
            }
            roleParticipation.setEventParticipationList(attachedEventParticipationList);
            em.persist(roleParticipation);
            for (EventParticipation eventParticipationListEventParticipation : roleParticipation.getEventParticipationList()) {
                RoleParticipation oldRoleParticipationFkOfEventParticipationListEventParticipation = eventParticipationListEventParticipation.getRoleParticipationFk();
                eventParticipationListEventParticipation.setRoleParticipationFk(roleParticipation);
                eventParticipationListEventParticipation = em.merge(eventParticipationListEventParticipation);
                if (oldRoleParticipationFkOfEventParticipationListEventParticipation != null) {
                    oldRoleParticipationFkOfEventParticipationListEventParticipation.getEventParticipationList().remove(eventParticipationListEventParticipation);
                    oldRoleParticipationFkOfEventParticipationListEventParticipation = em.merge(oldRoleParticipationFkOfEventParticipationListEventParticipation);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RoleParticipation roleParticipation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RoleParticipation persistentRoleParticipation = em.find(RoleParticipation.class, roleParticipation.getRoleParticipationId());
            List<EventParticipation> eventParticipationListOld = persistentRoleParticipation.getEventParticipationList();
            List<EventParticipation> eventParticipationListNew = roleParticipation.getEventParticipationList();
            List<EventParticipation> attachedEventParticipationListNew = new ArrayList<EventParticipation>();
            for (EventParticipation eventParticipationListNewEventParticipationToAttach : eventParticipationListNew) {
                eventParticipationListNewEventParticipationToAttach = em.getReference(eventParticipationListNewEventParticipationToAttach.getClass(), eventParticipationListNewEventParticipationToAttach.getEventParticipationPK());
                attachedEventParticipationListNew.add(eventParticipationListNewEventParticipationToAttach);
            }
            eventParticipationListNew = attachedEventParticipationListNew;
            roleParticipation.setEventParticipationList(eventParticipationListNew);
            roleParticipation = em.merge(roleParticipation);
            for (EventParticipation eventParticipationListOldEventParticipation : eventParticipationListOld) {
                if (!eventParticipationListNew.contains(eventParticipationListOldEventParticipation)) {
                    eventParticipationListOldEventParticipation.setRoleParticipationFk(null);
                    eventParticipationListOldEventParticipation = em.merge(eventParticipationListOldEventParticipation);
                }
            }
            for (EventParticipation eventParticipationListNewEventParticipation : eventParticipationListNew) {
                if (!eventParticipationListOld.contains(eventParticipationListNewEventParticipation)) {
                    RoleParticipation oldRoleParticipationFkOfEventParticipationListNewEventParticipation = eventParticipationListNewEventParticipation.getRoleParticipationFk();
                    eventParticipationListNewEventParticipation.setRoleParticipationFk(roleParticipation);
                    eventParticipationListNewEventParticipation = em.merge(eventParticipationListNewEventParticipation);
                    if (oldRoleParticipationFkOfEventParticipationListNewEventParticipation != null && !oldRoleParticipationFkOfEventParticipationListNewEventParticipation.equals(roleParticipation)) {
                        oldRoleParticipationFkOfEventParticipationListNewEventParticipation.getEventParticipationList().remove(eventParticipationListNewEventParticipation);
                        oldRoleParticipationFkOfEventParticipationListNewEventParticipation = em.merge(oldRoleParticipationFkOfEventParticipationListNewEventParticipation);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roleParticipation.getRoleParticipationId();
                if (findRoleParticipation(id) == null) {
                    throw new NonexistentEntityException("The roleParticipation with id " + id + " no longer exists.");
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
            RoleParticipation roleParticipation;
            try {
                roleParticipation = em.getReference(RoleParticipation.class, id);
                roleParticipation.getRoleParticipationId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roleParticipation with id " + id + " no longer exists.", enfe);
            }
            List<EventParticipation> eventParticipationList = roleParticipation.getEventParticipationList();
            for (EventParticipation eventParticipationListEventParticipation : eventParticipationList) {
                eventParticipationListEventParticipation.setRoleParticipationFk(null);
                eventParticipationListEventParticipation = em.merge(eventParticipationListEventParticipation);
            }
            em.remove(roleParticipation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RoleParticipation> findRoleParticipationEntities() {
        return findRoleParticipationEntities(true, -1, -1);
    }

    public List<RoleParticipation> findRoleParticipationEntities(int maxResults, int firstResult) {
        return findRoleParticipationEntities(false, maxResults, firstResult);
    }

    private List<RoleParticipation> findRoleParticipationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RoleParticipation.class));
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

    public RoleParticipation findRoleParticipation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RoleParticipation.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoleParticipationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RoleParticipation> rt = cq.from(RoleParticipation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
