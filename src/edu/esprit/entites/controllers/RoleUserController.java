/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entites.controllers;

import edu.esprit.entites.controllers.exceptions.NonexistentEntityException;
import edu.esprit.entities.RoleUser;
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
public class RoleUserController implements Serializable {

    public RoleUserController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RoleUser roleUser) {
        if (roleUser.getUserAccountList() == null) {
            roleUser.setUserAccountList(new ArrayList<UserAccount>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserAccount> attachedUserAccountList = new ArrayList<UserAccount>();
            for (UserAccount userAccountListUserAccountToAttach : roleUser.getUserAccountList()) {
                userAccountListUserAccountToAttach = em.getReference(userAccountListUserAccountToAttach.getClass(), userAccountListUserAccountToAttach.getUserIdPk());
                attachedUserAccountList.add(userAccountListUserAccountToAttach);
            }
            roleUser.setUserAccountList(attachedUserAccountList);
            em.persist(roleUser);
            for (UserAccount userAccountListUserAccount : roleUser.getUserAccountList()) {
                RoleUser oldUserRoleIdFkOfUserAccountListUserAccount = userAccountListUserAccount.getUserRoleIdFk();
                userAccountListUserAccount.setUserRoleIdFk(roleUser);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
                if (oldUserRoleIdFkOfUserAccountListUserAccount != null) {
                    oldUserRoleIdFkOfUserAccountListUserAccount.getUserAccountList().remove(userAccountListUserAccount);
                    oldUserRoleIdFkOfUserAccountListUserAccount = em.merge(oldUserRoleIdFkOfUserAccountListUserAccount);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RoleUser roleUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RoleUser persistentRoleUser = em.find(RoleUser.class, roleUser.getRoleIdPk());
            List<UserAccount> userAccountListOld = persistentRoleUser.getUserAccountList();
            List<UserAccount> userAccountListNew = roleUser.getUserAccountList();
            List<UserAccount> attachedUserAccountListNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountListNewUserAccountToAttach : userAccountListNew) {
                userAccountListNewUserAccountToAttach = em.getReference(userAccountListNewUserAccountToAttach.getClass(), userAccountListNewUserAccountToAttach.getUserIdPk());
                attachedUserAccountListNew.add(userAccountListNewUserAccountToAttach);
            }
            userAccountListNew = attachedUserAccountListNew;
            roleUser.setUserAccountList(userAccountListNew);
            roleUser = em.merge(roleUser);
            for (UserAccount userAccountListOldUserAccount : userAccountListOld) {
                if (!userAccountListNew.contains(userAccountListOldUserAccount)) {
                    userAccountListOldUserAccount.setUserRoleIdFk(null);
                    userAccountListOldUserAccount = em.merge(userAccountListOldUserAccount);
                }
            }
            for (UserAccount userAccountListNewUserAccount : userAccountListNew) {
                if (!userAccountListOld.contains(userAccountListNewUserAccount)) {
                    RoleUser oldUserRoleIdFkOfUserAccountListNewUserAccount = userAccountListNewUserAccount.getUserRoleIdFk();
                    userAccountListNewUserAccount.setUserRoleIdFk(roleUser);
                    userAccountListNewUserAccount = em.merge(userAccountListNewUserAccount);
                    if (oldUserRoleIdFkOfUserAccountListNewUserAccount != null && !oldUserRoleIdFkOfUserAccountListNewUserAccount.equals(roleUser)) {
                        oldUserRoleIdFkOfUserAccountListNewUserAccount.getUserAccountList().remove(userAccountListNewUserAccount);
                        oldUserRoleIdFkOfUserAccountListNewUserAccount = em.merge(oldUserRoleIdFkOfUserAccountListNewUserAccount);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roleUser.getRoleIdPk();
                if (findRoleUser(id) == null) {
                    throw new NonexistentEntityException("The roleUser with id " + id + " no longer exists.");
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
            RoleUser roleUser;
            try {
                roleUser = em.getReference(RoleUser.class, id);
                roleUser.getRoleIdPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roleUser with id " + id + " no longer exists.", enfe);
            }
            List<UserAccount> userAccountList = roleUser.getUserAccountList();
            for (UserAccount userAccountListUserAccount : userAccountList) {
                userAccountListUserAccount.setUserRoleIdFk(null);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
            }
            em.remove(roleUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RoleUser> findRoleUserEntities() {
        return findRoleUserEntities(true, -1, -1);
    }

    public List<RoleUser> findRoleUserEntities(int maxResults, int firstResult) {
        return findRoleUserEntities(false, maxResults, firstResult);
    }

    private List<RoleUser> findRoleUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RoleUser.class));
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

    public RoleUser findRoleUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RoleUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoleUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RoleUser> rt = cq.from(RoleUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
