package golfdetails;

import golfdetails.exceptions.IllegalOrphanException;
import golfdetails.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GolfUserJpaController implements Serializable {

    public GolfUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = CreateEntityManager.getEntityManager().getEntityManagerFactory();
        return emf.createEntityManager();
    }

    public void create(GolfUser golfUser) {
        if (golfUser.getGolfStatsCollection() == null) {
            golfUser.setGolfStatsCollection(new ArrayList<GolfStats>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<GolfStats> attachedGolfStatsCollection = new ArrayList<GolfStats>();
            for (GolfStats golfStatsCollectionGolfStatsToAttach : golfUser.getGolfStatsCollection()) {
                golfStatsCollectionGolfStatsToAttach = em.getReference(golfStatsCollectionGolfStatsToAttach.getClass(), golfStatsCollectionGolfStatsToAttach.getStatsId());
                attachedGolfStatsCollection.add(golfStatsCollectionGolfStatsToAttach);
            }
            golfUser.setGolfStatsCollection(attachedGolfStatsCollection);
            em.persist(golfUser);
            for (GolfStats golfStatsCollectionGolfStats : golfUser.getGolfStatsCollection()) {
                GolfUser oldStatsUserIdOfGolfStatsCollectionGolfStats = golfStatsCollectionGolfStats.getStatsUserId();
                golfStatsCollectionGolfStats.setStatsUserId(golfUser);
                golfStatsCollectionGolfStats = em.merge(golfStatsCollectionGolfStats);
                if (oldStatsUserIdOfGolfStatsCollectionGolfStats != null) {
                    oldStatsUserIdOfGolfStatsCollectionGolfStats.getGolfStatsCollection().remove(golfStatsCollectionGolfStats);
                    oldStatsUserIdOfGolfStatsCollectionGolfStats = em.merge(oldStatsUserIdOfGolfStatsCollectionGolfStats);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GolfUser golfUser) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GolfUser persistentGolfUser = em.find(GolfUser.class, golfUser.getUserId());
            Collection<GolfStats> golfStatsCollectionOld = persistentGolfUser.getGolfStatsCollection();
            Collection<GolfStats> golfStatsCollectionNew = golfUser.getGolfStatsCollection();
            List<String> illegalOrphanMessages = null;
            for (GolfStats golfStatsCollectionOldGolfStats : golfStatsCollectionOld) {
                if (!golfStatsCollectionNew.contains(golfStatsCollectionOldGolfStats)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GolfStats " + golfStatsCollectionOldGolfStats + " since its statsUserId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<GolfStats> attachedGolfStatsCollectionNew = new ArrayList<GolfStats>();
            for (GolfStats golfStatsCollectionNewGolfStatsToAttach : golfStatsCollectionNew) {
                golfStatsCollectionNewGolfStatsToAttach = em.getReference(golfStatsCollectionNewGolfStatsToAttach.getClass(), golfStatsCollectionNewGolfStatsToAttach.getStatsId());
                attachedGolfStatsCollectionNew.add(golfStatsCollectionNewGolfStatsToAttach);
            }
            golfStatsCollectionNew = attachedGolfStatsCollectionNew;
            golfUser.setGolfStatsCollection(golfStatsCollectionNew);
            golfUser = em.merge(golfUser);
            for (GolfStats golfStatsCollectionNewGolfStats : golfStatsCollectionNew) {
                if (!golfStatsCollectionOld.contains(golfStatsCollectionNewGolfStats)) {
                    GolfUser oldStatsUserIdOfGolfStatsCollectionNewGolfStats = golfStatsCollectionNewGolfStats.getStatsUserId();
                    golfStatsCollectionNewGolfStats.setStatsUserId(golfUser);
                    golfStatsCollectionNewGolfStats = em.merge(golfStatsCollectionNewGolfStats);
                    if (oldStatsUserIdOfGolfStatsCollectionNewGolfStats != null && !oldStatsUserIdOfGolfStatsCollectionNewGolfStats.equals(golfUser)) {
                        oldStatsUserIdOfGolfStatsCollectionNewGolfStats.getGolfStatsCollection().remove(golfStatsCollectionNewGolfStats);
                        oldStatsUserIdOfGolfStatsCollectionNewGolfStats = em.merge(oldStatsUserIdOfGolfStatsCollectionNewGolfStats);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = golfUser.getUserId();
                if (findGolfUser(id) == null) {
                    throw new NonexistentEntityException("The golfUser with id " + id + " no longer exists.");
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
            GolfUser golfUser;
            try {
                golfUser = em.getReference(GolfUser.class, id);
                golfUser.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The golfUser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GolfStats> golfStatsCollectionOrphanCheck = golfUser.getGolfStatsCollection();
            for (GolfStats golfStatsCollectionOrphanCheckGolfStats : golfStatsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GolfUser (" + golfUser + ") cannot be destroyed since the GolfStats " + golfStatsCollectionOrphanCheckGolfStats + " in its golfStatsCollection field has a non-nullable statsUserId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(golfUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GolfUser> findGolfUserEntities() {
        return findGolfUserEntities(true, -1, -1);
    }

    public List<GolfUser> findGolfUserEntities(int maxResults, int firstResult) {
        return findGolfUserEntities(false, maxResults, firstResult);
    }

    private List<GolfUser> findGolfUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GolfUser.class));
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

    public GolfUser findGolfUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GolfUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getGolfUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GolfUser> rt = cq.from(GolfUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public GolfUser findGolfUserByUserName(String userName) {
        EntityManager em = getEntityManager();
        GolfUser golfUser = null;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GolfUser> rt = cq.from(GolfUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);            
            
            List results = em.createNamedQuery("GolfUser.findByUserName")
                             .setParameter("userName", userName)
                             .getResultList();
            
            if (!results.isEmpty()) {
              golfUser = (GolfUser)results.get(0);
            }
            
            return golfUser;
        } finally {
            em.close();
        }
    }
}
