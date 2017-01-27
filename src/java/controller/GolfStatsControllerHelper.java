package controller;

import golfdetails.CreateEntityManager;
import golfdetails.GolfStats;
import golfdetails.GolfStatsAverage;
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
    
    private GolfStats golfStatsDetails = new GolfStats();
    private String unit_name = "GolfStatsApplicationJSPPU";
    private List<GolfStats> golfStatsTop10List = null;
    private GolfStatsAverage golfStatsTop10Average = null;
    private List<GolfStats> golfStatsAllList = null;
    private GolfStatsAverage golfStatsAllAverage = null;
    private GolfStatsControllerData golfStatsControllerData = new GolfStatsControllerData();
    private GolfUser golfUser = null;
    
    public GolfStatsControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        golfStatsControllerData.getRequestDetails(request);
    } 
    
    public Object getData() {
        return golfStatsDetails;
    }
    
    public GolfStatsControllerData getGolfStatsControllerData() {
        return golfStatsControllerData;
    }

    public void setGolfStatsControllerData(GolfStatsControllerData golfStatsControllerData) {
        this.golfStatsControllerData = golfStatsControllerData;
    }

    public GolfUser getGolfUser() {
        return golfUser;
    }

    public void setGolfUser(GolfUser golfUser) {
        this.golfUser = golfUser;
    }
    
    public String runRequest(String operation) {
        
        String page = "";
        
        switch (operation) {
            case "newStatsDetails":
                page = newStatsDetails();
                break;
            case "editStatsDetails":
                page = editStatsDetails();
                break;
            case "saveStatsDetails":
                page = saveStatsDetails(); 
                break;
            case "saveStatsDetailsBackViewAll":
                saveStatsDetails(); 
                page = viewStatsDetails(); 
                break;
            case "deleteStatsDetails":
                page = deleteStatsDetails();    
                break;
            case "viewAllStatsDetails":  
                page = viewStatsDetails(); 
                break;
            case "editStatsBack": 
                page = viewStatsDetails(); 
                break;
            case "displayHomePage":  
                page = viewStatsDetails(); 
                break;
            case "logoff":  
                CurrentUser.logOff();
                page = displayHomePage();
                break;
            default:
                page = displayHomePage(); 
        }
        
        return page;
    }    
    
    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        String errorMsg = "";
        String operation = golfStatsControllerData.getOperation();
        
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
        
        golfUser = (GolfUser)request.getSession().getAttribute(golfCurrentUserDetailsHelper());
        
        address = runRequest(operation);

        golfStatsTop10List = CurrentUser.viewTop10StatsDetails();
        golfStatsTop10Average = CurrentUser.viewGolfStatsAverage(golfStatsTop10List);
        
        // add top 10 averages to the request
        request.setAttribute(golfStatsTop10AverageDetails(), golfStatsTop10Average);
        
        // add top 10 list to the request
        request.setAttribute(golfStatsTop10DetailsList(), golfStatsTop10List);         
    
        // add all averages to the request
        request.setAttribute(golfStatsAverageDetails(), golfStatsAllAverage);

        // add all list to the request
        request.setAttribute(golfStatsDetailsList(), golfStatsAllList);    
        
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
        
        golfStatsDetails = golfStats.findGolfStats(new Integer(golfStatsControllerData.getStatsID()));
        addHelperToSession(golfStatsDetailsHelper(), SessionData.IGNORE);
        return displayEditStatsPage();
    }
    
    public void selectStatsDetails() {
        EntityManager em = CreateEntityManager.getEntityManager();
        GolfStatsJpaController golfStats = new GolfStatsJpaController(em.getEntityManagerFactory());        
        try {
            golfStatsDetails = golfStats.findGolfStats(new Integer(golfStatsControllerData.getStatsID()));
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        addHelperToSession(golfStatsDetailsHelper(), SessionData.IGNORE);
    }    

    public String saveStatsDetails() {
        String address = "";
        // If no stats id is provided then we are inserting
        try {
          golfStatsDetails.setStatsId(new Integer(golfStatsControllerData.getStatsID()));
        } catch(NumberFormatException e) {
          golfStatsDetails.setStatsId(null);
        }

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfUserJpaController golfUserJPA = new GolfUserJpaController(em.getEntityManagerFactory());         

        GolfUser golfUserIn = null;
        
        try {
          golfUserIn = golfUserJPA.findGolfUser(new Integer(golfStatsControllerData.getStatsUserID()));
        } catch(NumberFormatException e) {
          golfStatsDetails.setStatsUserId(null);
        }
       
        // If a user was not passed in then this must be the current user
        if (golfUserIn == null) {
          golfUserIn = golfUser;
        } 
        
        golfStatsDetails.setStatsUserId(golfUserIn);
        golfStatsDetails.setStatsCourseName(golfStatsControllerData.getStatsCourseName());
        
        String time = golfStatsControllerData.getStatsDatetime();
        
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
        golfStatsDetails.setStatsScore(new Integer(golfStatsControllerData.getStatsScore()));
        golfStatsDetails.setStatsCompType(golfStatsControllerData.getStatsCompType());
        golfStatsDetails.setStatsPutts(new Integer(golfStatsControllerData.getStatsPutts()));
        golfStatsDetails.setStatsGreens(new Integer(golfStatsControllerData.getStatsGreens()));
        golfStatsDetails.setStatsFairways(new Integer(golfStatsControllerData.getStatsFairways()));
        golfStatsDetails.setStatsTemp(new Double(golfStatsControllerData.getStatsTemp()));
        golfStatsDetails.setStatsWind(new Double(golfStatsControllerData.getStatsWind()));
        golfStatsDetails.setStatsCondition(golfStatsControllerData.getStatsCondition());
        
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
        golfStatsAllList = CurrentUser.viewAllStatsDetails();
        golfStatsAllAverage = CurrentUser.viewGolfStatsAverage(golfStatsAllList);

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
    
    public static String golfCurrentUserDetailsError() {
        return "golfCurrentUserDetailsError";
    }

    public static String golfStatsTop10DetailsList() {
        return "golfStatsTop10DetailsList";
    }
    
    public static String golfStatsTop10AverageDetails() {
        return "golfStatsTop10AverageDetails";
    } 
    
    
    public static String golfCurrentUserDetailsHelper() {
        return "golfCurrentUserDetailsHelper";
    }
}
