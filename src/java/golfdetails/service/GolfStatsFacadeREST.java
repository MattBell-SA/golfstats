package golfdetails.service;

import golfdetails.CreateEntityManager;
import golfdetails.GolfDevice;
import golfdetails.GolfDeviceJpaControllerImplementation;
import golfdetails.GolfStats;
import golfdetails.GolfStatsJpaController;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Path("golfdetails.golfstats")
public class GolfStatsFacadeREST extends AbstractFacade<GolfStats> {

    @PersistenceContext(unitName = "GolfStatsApplicationJSPPU")
    private EntityManager em;

    public GolfStatsFacadeREST() {
        super(GolfStats.class);
    }

    @POST
    @Path("{create}/{deviceid}/{statsCourseName}/{statsDatetime}/{statsScore}/{statsCompType}/{statsPutts}/{statsGreens}/statsFairways}/{statsTemp}/{statsWind}/{statsCondition}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@PathParam("create") String create
                      ,@PathParam("deviceid") Integer deviceID
                      ,@PathParam("statsCourseName") String statsCourseName
                      ,@PathParam("statsDatetime") String statsDatetime
                      ,@PathParam("statsScore") Integer statsScore
                      ,@PathParam("statsCompType") String statsCompType
                      ,@PathParam("statsPutts") Integer statsPutts
                      ,@PathParam("statsGreens") Integer statsGreens
                      ,@PathParam("statsFairways") Integer statsFairways
                      ,@PathParam("statsTemp") String statsTemp
                      ,@PathParam("statsWind") String statsWind
                      ,@PathParam("statsCondition") String statsCondition) {
 
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        
        GolfDevice golfDeviceDetails = new GolfDevice();
        GolfDeviceJpaControllerImplementation golfDeviceJpa = new GolfDeviceJpaControllerImplementation(em.getEntityManagerFactory());         
        golfDeviceDetails = golfDeviceJpa.findGolfDevice(deviceID);

        GolfUser golfUserDetails = new GolfUser();
        GolfUserJpaController golfUserJpa = new GolfUserJpaController(em.getEntityManagerFactory());         
        golfUserDetails = golfUserJpa.findGolfUser(golfDeviceDetails.getDeviceUserId().getUserId());        
        
        GolfStats golfStatsDetails = new GolfStats();
        
        golfStatsDetails.setStatsUserId(golfUserDetails);
        
        try {
            golfStatsDetails.setStatsCourseName(java.net.URLDecoder.decode(statsCourseName, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String time = statsDatetime;
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
          date = format.parse(time);
        } catch (ParseException e) {
          System.out.println(e.getLocalizedMessage());
        }
        
        pattern = "yyyy-MM-dd";
        format = new SimpleDateFormat(pattern);
        if(date == null) {
            try {
              date = format.parse(time);
            } catch (ParseException e) {
              System.out.println(e.getLocalizedMessage());
            }
        }

        golfStatsDetails.setStatsDatetime(date);
        golfStatsDetails.setStatsScore(statsScore);
        try {
            golfStatsDetails.setStatsCompType(java.net.URLDecoder.decode(statsCompType, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        golfStatsDetails.setStatsPutts(statsPutts);
        golfStatsDetails.setStatsGreens(statsGreens);
        golfStatsDetails.setStatsFairways(statsFairways);
        golfStatsDetails.setStatsTemp(new Double(statsTemp));
        golfStatsDetails.setStatsWind(new Double(statsWind));
        try {
            golfStatsDetails.setStatsCondition(java.net.URLDecoder.decode(statsCondition, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        try { 
            etx.begin(); 
            em.persist(golfStatsDetails);
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }        
    }

    @PUT
    @Path("{statsid}/{statsCourseName}/{statsDatetime}/{statsScore}/{statsCompType}/{statsPutts}/{statsGreens}/{statsFairways}/{statsTemp}/{statsWind}/{statsCondition}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("statsid") Integer statsid
                    ,@PathParam("statsCourseName") String statsCourseName
                    ,@PathParam("statsDatetime") String statsDatetime
                    ,@PathParam("statsScore") Integer statsScore
                    ,@PathParam("statsCompType") String statsCompType
                    ,@PathParam("statsPutts") Integer statsPutts
                    ,@PathParam("statsGreens") Integer statsGreens
                    ,@PathParam("statsFairways") Integer statsFairways
                    ,@PathParam("statsTemp") String statsTemp
                    ,@PathParam("statsWind") String statsWind
                    ,@PathParam("statsCondition") String statsCondition) {
        
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();

        GolfStats golfStatsDetails = new GolfStats();
        GolfStatsJpaController golfStatsJpa = new GolfStatsJpaController(em.getEntityManagerFactory()); 
        golfStatsDetails = golfStatsJpa.findGolfStats(statsid);  

        try {
            golfStatsDetails.setStatsCourseName(java.net.URLDecoder.decode(statsCourseName, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String time = statsDatetime;
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
          date = format.parse(time);
        } catch (ParseException e) {
          System.out.println(e.getLocalizedMessage());
        }
        
        pattern = "yyyy-MM-dd";
        format = new SimpleDateFormat(pattern);
        if(date == null) {
            try {
              date = format.parse(time);
            } catch (ParseException e) {
              System.out.println(e.getLocalizedMessage());
            }
        }

        golfStatsDetails.setStatsDatetime(date);
        
        golfStatsDetails.setStatsScore(statsScore);
        try {
            golfStatsDetails.setStatsCompType(java.net.URLDecoder.decode(statsCompType, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        golfStatsDetails.setStatsPutts(statsPutts);
        golfStatsDetails.setStatsGreens(statsGreens);
        golfStatsDetails.setStatsFairways(statsFairways);
        golfStatsDetails.setStatsTemp(new Double(statsTemp));
        golfStatsDetails.setStatsWind(new Double(statsWind));
        try {
            golfStatsDetails.setStatsCondition(java.net.URLDecoder.decode(statsCondition, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GolfStatsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        try { 
            etx.begin(); 
            if (golfStatsDetails.getStatsId() != null) {
              em.merge(golfStatsDetails);
            } 
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    @DELETE
    @Path("{statsid}")
    public void remove(@PathParam("statsid") Integer statsid) {
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();

        GolfStats golfStatsDetails = new GolfStats();
        GolfStatsJpaController golfStatsJpa = new GolfStatsJpaController(em.getEntityManagerFactory()); 
        golfStatsDetails = golfStatsJpa.findGolfStats(statsid);        
        
        try {  
            etx.begin(); 
            if (golfStatsDetails.getStatsId() != null) {
              em.remove(em.merge(golfStatsDetails));
            }
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println("deleteStatsDetails - remove : Exception : " + ex.getMessage());
        }
    }


    @GET
    @Path("stats/{deviceid}/{statsid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GolfStats> findAll(@PathParam("deviceid") Integer deviceID
                                  ,@PathParam("statsid") Integer statsID) {
        
        GolfDevice golfDeviceDetails = new GolfDevice();
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfDeviceJpaControllerImplementation golfDeviceJpa = new GolfDeviceJpaControllerImplementation(em.getEntityManagerFactory());         
        golfDeviceDetails = golfDeviceJpa.findGolfDevice(deviceID);

        GolfUser golfUserDetails = new GolfUser();
        GolfUserJpaController golfUserJpa = new GolfUserJpaController(em.getEntityManagerFactory());         
        golfUserDetails = golfUserJpa.findGolfUser(golfDeviceDetails.getDeviceUserId().getUserId());
        
        List<GolfStats> golfStatsDetails = new ArrayList();
        GolfStatsJpaController golfStatsJpa = new GolfStatsJpaController(em.getEntityManagerFactory()); 
        
        if (statsID != -1) {
          golfStatsDetails.add(golfStatsJpa.findGolfStats(statsID));
        } else {
          golfStatsDetails = golfStatsJpa.findGolfStatsByUserName(golfUserDetails);
        }
        
        for(GolfStats golfStats : golfStatsDetails) {
            golfStats.getStatsUserId().setUserPwd("");
        }
                
        return golfStatsDetails;
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GolfStats> findAll() {
        return super.findAll();
    }    

    @GET 
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
