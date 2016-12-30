package golfdetails.service;

import golfdetails.CreateEntityManager;
import golfdetails.GolfDevice;
import golfdetails.GolfDeviceJpaControllerImplementation;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("golfdetails.golfuser")
public class GolfUserFacadeREST extends AbstractFacade<GolfUser> {

    @PersistenceContext(unitName = "GolfStatsApplicationJSPPU")
    private EntityManager em;

    public GolfUserFacadeREST() {
        super(GolfUser.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(GolfUser entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, GolfUser entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("users/{userid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GolfUser find(@PathParam("userid") Integer userid) {
        GolfUser golfUserDetails = new GolfUser();
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());         
        golfUserDetails = golfUser.findGolfUser(userid);
        golfUserDetails.setUserPwd("");
        return golfUserDetails;
    }
    
    @GET
    @Path("{deviceid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GolfUser findDeviceID(@PathParam("deviceid") Integer deviceID) {
        GolfDevice golfDeviceDetails = new GolfDevice();
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfDeviceJpaControllerImplementation golfDeviceJpa = new GolfDeviceJpaControllerImplementation(em.getEntityManagerFactory());         
        golfDeviceDetails = golfDeviceJpa.findGolfDevice(deviceID);

        GolfUser golfUserDetails = new GolfUser();
        GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());         
        golfUserDetails = golfUser.findGolfUser(golfDeviceDetails.getDeviceUserId().getUserId());
        golfUserDetails.setUserPwd("");
        return golfUserDetails;
    }    
    

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GolfUser> findAll() {
        return super.findAll();
    }


    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());         
        List<GolfUser> golfUserDetails = golfUser.findGolfUserEntities();        
        
        return String.valueOf(golfUserDetails.size());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}