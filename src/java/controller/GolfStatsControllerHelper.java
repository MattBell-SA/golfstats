package controller;

import controller.CurrentUser.GolfStatsAverage;
import static controller.CurrentUser.golfCurrentUserDetailsError;
import golfdetails.CreateEntityManager;
import golfdetails.GolfStats;
import golfdetails.GolfStatsJpaController;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GolfStatsControllerHelper extends HelperBase{
    
    protected GolfStats golfStatsDetails = new GolfStats();
    String unit_name = "GolfStatsApplicationJSPPU";
    
    public GolfStatsControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    } 
    
    public Object getData() {
        return golfStatsDetails;
    }
    
    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        String errorMsg = "";
        
        if (CurrentUser.getCurrentUser() == null) {
            address = displayHomePage();
            errorMsg = "**No current user";
            // add the error message to the session
            request.getSession().setAttribute(golfCurrentUserDetailsError(), errorMsg);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher(address);
            dispatcher2.forward(request, response);            
            return;
        }

        addHelperToSession(golfStatsDetailsHelper(), SessionData.READ);
       
        if (request.getParameter("newStatsDetails") != null)  {
            address = newStatsDetails();
        } 
        
        if (request.getParameter("editStatsDetails") != null)  {
            address = editStatsDetails();
        } 
        
        if (request.getParameter("saveStatsDetails") != null)  {
            address = saveStatsDetails();            
        }
        
        if (request.getParameter("saveStatsDetailsBackViewAll") != null)  {
            saveStatsDetails(); 
            address = viewStatsDetails();  
        }        
        
        if (request.getParameter("deleteStatsDetails") != null)  {
            address = deleteStatsDetails();            
        }
        
        if (request.getParameter("viewAllStatsDetails") != null)  {
            address = viewStatsDetails();            
        }
        
        if (request.getParameter("editStatsBack") != null)  {
            address = viewStatsDetails();            
        }
        
        if (request.getParameter("displayHomePage") != null)  {
            address = viewStatsDetails();            
        }
        
        if (request.getParameter("logoff") != null)  {
            address = displayHomePage(); 
            CurrentUser.logOff();
        }
        
        CurrentUser.viewTop10StatsDetails(request);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
        
    }
    
    protected void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            golfStatsDetails = (GolfStats)((GolfStatsControllerHelper)sessionHelper).getData();
        }
    }
    
    protected String getjspLocation(String page) {
        return "/GolfStatsDetails/" + page;
    }
    
    public String newStatsDetails() {
        golfStatsDetails = new GolfStats();
        addHelperToSession(golfStatsDetailsHelper(), SessionData.IGNORE);
        return displayEnterStatsPage();
    }
    
    
    public String editStatsDetails() {
        EntityManager em = CreateEntityManager.getEntityManager();
        GolfStatsJpaController golfStats = new GolfStatsJpaController(em.getEntityManagerFactory());        
        
        golfStatsDetails = golfStats.findGolfStats(new Integer(request.getParameter("StatsID")));
        addHelperToSession(golfStatsDetailsHelper(), SessionData.IGNORE);
        return displayEditStatsPage();
    }

    public String saveStatsDetails() {
        String address = "";
        // If no stats id is provided then we are inserting
        try {
          golfStatsDetails.setStatsId(new Integer(request.getParameter("StatsID")));
        } catch(NumberFormatException e) {
          golfStatsDetails.setStatsId(null);
        }

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfUserJpaController golfUserJPA = new GolfUserJpaController(em.getEntityManagerFactory());         

        GolfUser golfUser = null;
        
        try {
          golfUser = golfUserJPA.findGolfUser(new Integer(request.getParameter("StatsUserID")));
        } catch(NumberFormatException e) {
          golfStatsDetails.setStatsUserId(null);
        }
        
        // If a user was not passed in then this must be the current user
       golfUser = (GolfUser)request.getSession().getAttribute(CurrentUser.golfCurrentUserDetailsHelper());
        
        golfStatsDetails.setStatsUserId(golfUser);
        golfStatsDetails.setStatsCourseName(request.getParameter("StatsCourseName"));
        
        String time = request.getParameter("StatsDatetime");
        
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
        golfStatsDetails.setStatsScore(new Integer(request.getParameter("StatsScore")));
        golfStatsDetails.setStatsCompType(request.getParameter("StatsCompType"));
        golfStatsDetails.setStatsPutts(new Integer(request.getParameter("StatsPutts")));
        golfStatsDetails.setStatsGreens(new Integer(request.getParameter("StatsGreens")));
        golfStatsDetails.setStatsFairways(new Integer(request.getParameter("StatsFairways")));
        golfStatsDetails.setStatsTemp(new Double(request.getParameter("StatsTemp")));
        golfStatsDetails.setStatsWind(new Double(request.getParameter("StatsWind")));
        golfStatsDetails.setStatsCondition(request.getParameter("StatsCondition"));
        
        addHelperToSession(golfStatsDetailsHelper(), SessionData.IGNORE);

        try { 
            etx.begin(); 
            if (golfStatsDetails.getStatsId() != null) {
              em.merge(golfStatsDetails);
              address = viewAllStatsDetails();
            } else {
              em.persist(golfStatsDetails);
              address = displayEnterStatsPage();
            }
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return address;
    }
    
    public String backIndexDetails() {
        return displayHomePage();
    }

    public String viewStatsDetails() {
        golfStatsDetails = new GolfStats();
        addHelperToSession(golfStatsDetailsHelper(), SessionData.READ);

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfStatsJpaController golfStatsJPA = new GolfStatsJpaController(em.getEntityManagerFactory());          
        

        List<GolfStats> golfStatsList = golfStatsJPA.findGolfStatsByUserName(CurrentUser.getCurrentUser());
        GolfStatsAverage golfStatsAverage = new GolfStatsAverage();
        golfStatsAverage.setGolfStatsAverage(golfStatsList);

        // add averages to the request
        request.setAttribute(golfStatsAverageDetails(), golfStatsAverage);

        // add list to the request
        request.setAttribute(golfStatsDetailsList(), golfStatsList);
        
        return viewAllStatsDetails();
    }
    
    public String deleteStatsDetails() {

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();

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
        
        return viewStatsDetails();
    }   

    public String displayHomePage() {
        return "./index.jsp";
    }
    
    public String displayEditStatsPage() {
        return "./edit_stats/editstats.jsp";
    }
    
    public String displayEnterStatsPage() {
        return "./enter_stats/enterstats.jsp";
    }
    
    public String viewAllStatsDetails() {
        return "./view_stats/viewallstats.jsp";
    }
    
    public String golfStatsDetailsHelper() {
        return "golfStatsDetailsHelper";
    }
    
    public String golfStatsDetailsList() {
        return "golfStatsDetailsList";
    }
   
    public static String golfStatsAverageDetails() {
        return "golfStatsAverageDetails";
    } 

}
