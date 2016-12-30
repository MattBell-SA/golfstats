package golfdetails;

import golfdetails.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GolfStatsJpaController implements Serializable {

    public GolfStatsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        
        emf = CreateEntityManager.getEntityManager().getEntityManagerFactory();

        return emf.createEntityManager();
    }

    public void create(GolfStats golfStats) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GolfUser statsUserId = golfStats.getStatsUserId();
            if (statsUserId != null) {
                statsUserId = em.getReference(statsUserId.getClass(), statsUserId.getUserId());
                golfStats.setStatsUserId(statsUserId);
            }
            em.persist(golfStats);
            if (statsUserId != null) {
                statsUserId.getGolfStatsCollection().add(golfStats);
                statsUserId = em.merge(statsUserId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GolfStats golfStats) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GolfStats persistentGolfStats = em.find(GolfStats.class, golfStats.getStatsId());
            GolfUser statsUserIdOld = persistentGolfStats.getStatsUserId();
            GolfUser statsUserIdNew = golfStats.getStatsUserId();
            if (statsUserIdNew != null) {
                statsUserIdNew = em.getReference(statsUserIdNew.getClass(), statsUserIdNew.getUserId());
                golfStats.setStatsUserId(statsUserIdNew);
            }
            golfStats = em.merge(golfStats);
            if (statsUserIdOld != null && !statsUserIdOld.equals(statsUserIdNew)) {
                statsUserIdOld.getGolfStatsCollection().remove(golfStats);
                statsUserIdOld = em.merge(statsUserIdOld);
            }
            if (statsUserIdNew != null && !statsUserIdNew.equals(statsUserIdOld)) {
                statsUserIdNew.getGolfStatsCollection().add(golfStats);
                statsUserIdNew = em.merge(statsUserIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = golfStats.getStatsId();
                if (findGolfStats(id) == null) {
                    throw new NonexistentEntityException("The golfStats with id " + id + " no longer exists.");
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
            GolfStats golfStats;
            try {
                golfStats = em.getReference(GolfStats.class, id);
                golfStats.getStatsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The golfStats with id " + id + " no longer exists.", enfe);
            }
            GolfUser statsUserId = golfStats.getStatsUserId();
            if (statsUserId != null) {
                statsUserId.getGolfStatsCollection().remove(golfStats);
                statsUserId = em.merge(statsUserId);
            }
            em.remove(golfStats);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GolfStats> findGolfStatsEntities() {
        return findGolfStatsEntities(true, -1, -1);
    }

    public List<GolfStats> findGolfStatsEntities(int maxResults, int firstResult) {
        return findGolfStatsEntities(false, maxResults, firstResult);
    }

    private List<GolfStats> findGolfStatsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GolfStats.class));
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

    public GolfStats findGolfStats(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GolfStats.class, id);
        } finally {
            em.close();
        }
    }

    public int getGolfStatsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GolfStats> rt = cq.from(GolfStats.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<GolfStats> findTop10GolfStatsByUserName(GolfUser golfUser) {
        EntityManager em = getEntityManager();
        List<GolfStats> golfStats = null;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GolfUser> rt = cq.from(GolfUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);            
            
            golfStats = em.createNamedQuery("GolfStats.findTop10ByStatsUserId")
                            .setParameter("statsUserId", golfUser.getUserId())
                            .setMaxResults(10)
                            .getResultList();
            
            return golfStats;
        } finally {
            em.close();
        }
    }  
    
    public List<GolfStats> findGolfStatsByUserName(GolfUser golfUser) {
        EntityManager em = getEntityManager();
        List<GolfStats> golfStats = null;
        try {
            golfStats = em.createNamedQuery("GolfStats.findByStatsUserId")
                            .setParameter("statsUserId", golfUser.getUserId())
                            .getResultList();
            
            return golfStats;
        } finally {
            em.close();
        }
    }     
    
    
}
