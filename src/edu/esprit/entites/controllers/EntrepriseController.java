/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.Entreprise;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.esprit.entities.UserAccount;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author azer
 */
public class EntrepriseController implements Serializable {

    public EntrepriseController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entreprise entreprise) {
        if (entreprise.getUserAccountList() == null) {
            entreprise.setUserAccountList(new ArrayList<UserAccount>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserAccount> attachedUserAccountList = new ArrayList<UserAccount>();
            for (UserAccount userAccountListUserAccountToAttach : entreprise.getUserAccountList()) {
                userAccountListUserAccountToAttach = em.getReference(userAccountListUserAccountToAttach.getClass(), userAccountListUserAccountToAttach.getUserIdPk());
                attachedUserAccountList.add(userAccountListUserAccountToAttach);
            }
            entreprise.setUserAccountList(attachedUserAccountList);
            em.persist(entreprise);
            for (UserAccount userAccountListUserAccount : entreprise.getUserAccountList()) {
                Entreprise oldUserEntrepriseIdFkOfUserAccountListUserAccount = userAccountListUserAccount.getUserEntrepriseIdFk();
                userAccountListUserAccount.setUserEntrepriseIdFk(entreprise);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
                if (oldUserEntrepriseIdFkOfUserAccountListUserAccount != null) {
                    oldUserEntrepriseIdFkOfUserAccountListUserAccount.getUserAccountList().remove(userAccountListUserAccount);
                    oldUserEntrepriseIdFkOfUserAccountListUserAccount = em.merge(oldUserEntrepriseIdFkOfUserAccountListUserAccount);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entreprise entreprise) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entreprise persistentEntreprise = em.find(Entreprise.class, entreprise.getEntrepriseIdPk());
            List<UserAccount> userAccountListOld = persistentEntreprise.getUserAccountList();
            List<UserAccount> userAccountListNew = entreprise.getUserAccountList();
            List<UserAccount> attachedUserAccountListNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountListNewUserAccountToAttach : userAccountListNew) {
                userAccountListNewUserAccountToAttach = em.getReference(userAccountListNewUserAccountToAttach.getClass(), userAccountListNewUserAccountToAttach.getUserIdPk());
                attachedUserAccountListNew.add(userAccountListNewUserAccountToAttach);
            }
            userAccountListNew = attachedUserAccountListNew;
            entreprise.setUserAccountList(userAccountListNew);
            entreprise = em.merge(entreprise);
            for (UserAccount userAccountListOldUserAccount : userAccountListOld) {
                if (!userAccountListNew.contains(userAccountListOldUserAccount)) {
                    userAccountListOldUserAccount.setUserEntrepriseIdFk(null);
                    userAccountListOldUserAccount = em.merge(userAccountListOldUserAccount);
                }
            }
            for (UserAccount userAccountListNewUserAccount : userAccountListNew) {
                if (!userAccountListOld.contains(userAccountListNewUserAccount)) {
                    Entreprise oldUserEntrepriseIdFkOfUserAccountListNewUserAccount = userAccountListNewUserAccount.getUserEntrepriseIdFk();
                    userAccountListNewUserAccount.setUserEntrepriseIdFk(entreprise);
                    userAccountListNewUserAccount = em.merge(userAccountListNewUserAccount);
                    if (oldUserEntrepriseIdFkOfUserAccountListNewUserAccount != null && !oldUserEntrepriseIdFkOfUserAccountListNewUserAccount.equals(entreprise)) {
                        oldUserEntrepriseIdFkOfUserAccountListNewUserAccount.getUserAccountList().remove(userAccountListNewUserAccount);
                        oldUserEntrepriseIdFkOfUserAccountListNewUserAccount = em.merge(oldUserEntrepriseIdFkOfUserAccountListNewUserAccount);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entreprise.getEntrepriseIdPk();
                if (findEntreprise(id) == null) {
                    throw new NonexistentEntityException("The entreprise with id " + id + " no longer exists.");
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
            Entreprise entreprise;
            try {
                entreprise = em.getReference(Entreprise.class, id);
                entreprise.getEntrepriseIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entreprise with id " + id + " no longer exists.", enfe);
            }
            List<UserAccount> userAccountList = entreprise.getUserAccountList();
            for (UserAccount userAccountListUserAccount : userAccountList) {
                userAccountListUserAccount.setUserEntrepriseIdFk(null);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
            }
            em.remove(entreprise);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entreprise> findEntrepriseEntities() {
        return findEntrepriseEntities(true, -1, -1);
    }

    public List<Entreprise> findEntrepriseEntities(int maxResults, int firstResult) {
        return findEntrepriseEntities(false, maxResults, firstResult);
    }

    private List<Entreprise> findEntrepriseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entreprise.class));
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

    public Entreprise findEntreprise(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entreprise.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntrepriseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entreprise> rt = cq.from(Entreprise.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
