package golfdetails;

import golfdetails.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GolfDeviceJpaController implements Serializable {

    public GolfDeviceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GolfDevice golfDevice) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GolfUser deviceUserId = golfDevice.getDeviceUserId();
            if (deviceUserId != null) {
                deviceUserId = em.getReference(deviceUserId.getClass(), deviceUserId.getUserId());
                golfDevice.setDeviceUserId(deviceUserId);
            }
            em.persist(golfDevice);
            if (deviceUserId != null) {
                deviceUserId.getGolfDeviceCollection().add(golfDevice);
                deviceUserId = em.merge(deviceUserId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GolfDevice golfDevice) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GolfDevice persistentGolfDevice = em.find(GolfDevice.class, golfDevice.getDeviceId());
            GolfUser deviceUserIdOld = persistentGolfDevice.getDeviceUserId();
            GolfUser deviceUserIdNew = golfDevice.getDeviceUserId();
            if (deviceUserIdNew != null) {
                deviceUserIdNew = em.getReference(deviceUserIdNew.getClass(), deviceUserIdNew.getUserId());
                golfDevice.setDeviceUserId(deviceUserIdNew);
            }
            golfDevice = em.merge(golfDevice);
            if (deviceUserIdOld != null && !deviceUserIdOld.equals(deviceUserIdNew)) {
                deviceUserIdOld.getGolfDeviceCollection().remove(golfDevice);
                deviceUserIdOld = em.merge(deviceUserIdOld);
            }
            if (deviceUserIdNew != null && !deviceUserIdNew.equals(deviceUserIdOld)) {
                deviceUserIdNew.getGolfDeviceCollection().add(golfDevice);
                deviceUserIdNew = em.merge(deviceUserIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = golfDevice.getDeviceId();
                if (findGolfDevice(id) == null) {
                    throw new NonexistentEntityException("The golfDevice with id " + id + " no longer exists.");
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
            GolfDevice golfDevice;
            try {
                golfDevice = em.getReference(GolfDevice.class, id);
                golfDevice.getDeviceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The golfDevice with id " + id + " no longer exists.", enfe);
            }
            GolfUser deviceUserId = golfDevice.getDeviceUserId();
            if (deviceUserId != null) {
                deviceUserId.getGolfDeviceCollection().remove(golfDevice);
                deviceUserId = em.merge(deviceUserId);
            }
            em.remove(golfDevice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GolfDevice> findGolfDeviceEntities() {
        return findGolfDeviceEntities(true, -1, -1);
    }

    public List<GolfDevice> findGolfDeviceEntities(int maxResults, int firstResult) {
        return findGolfDeviceEntities(false, maxResults, firstResult);
    }

    private List<GolfDevice> findGolfDeviceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GolfDevice.class));
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

    public GolfDevice findGolfDevice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GolfDevice.class, id);
        } finally {
            em.close();
        }
    }

    public int getGolfDeviceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GolfDevice> rt = cq.from(GolfDevice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
