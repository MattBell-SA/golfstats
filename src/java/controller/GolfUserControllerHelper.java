package controller;

import golfdetails.CreateEntityManager;
import golfdetails.GolfStats;
import golfdetails.GolfStatsAverage;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GolfUserControllerHelper extends HelperBase{
    
    private GolfUser golfUserDetails = new GolfUser();
    private GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
    private String unit_name = "GolfStatsApplicationJSPPU";
    private List<GolfUser> golfUserList = null;
    private List<GolfStats> golfStatsList = null;
    private GolfStatsAverage golfStatsTop10Average = null;
    
    public List<GolfUser> getGolfUserList() {
        return golfUserList;
    }
    
    public GolfUserControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        golfUserControllerData.getRequestDetails(request);
     } 
    
    public Object getData() {
        return golfUserDetails;
    }
    
    public void setGolfUserDetails(GolfUser golfUserDetails) {
        this.golfUserDetails = golfUserDetails;
    }
    
    public GolfUserControllerData getGolfUserControllerData() {
        return golfUserControllerData;
    }

    public void setGolfUserControllerData(GolfUserControllerData golfUserControllerData) {
        this.golfUserControllerData = golfUserControllerData;
    }    

    public boolean isValidateCurrentUser(String operation)  {
      if ((CurrentUser.getCurrentUser() == null) && 
         (("editUserDetails".equals(operation)) 
         || ("deleteUserDetails".equals(operation))
         || ("viewAllUserDetails".equals(operation)))) {
        return true;
      }  
      return false;
    }
    
    public String runRequest(String operation) {
        String page = "";
        
        switch (operation) {
            case "newUserDetails":
                page = newUserDetails();
                break;
            case "editUserDetails":
                page = editUserDetails();
                break;
            case "saveUserDetails":
                page = saveUserDetails(); 
                break;
            case "deleteUserDetails":
                page = deleteUserDetails(); 
                break;
            case "viewAllUserDetails":
                page = viewUserDetails();    
                break;
            case "editUserBack":  
                page = displayHomePage(); 
                break;
            case "displayHomePage": 
                page = displayHomePage(); 
                break;
            case "LogOn":  
                page = logOnAsCurrentUser(); 
                break;
            default:
                page = displayHomePage(); 
        }
        
        return page;    
    }

    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        String errorMsg = "";
        String operation = golfUserControllerData.getOperation();
        
        if (isValidateCurrentUser(operation)) {
            address = displayHomePage();
            errorMsg = "**No current user";
            // add the error message to the session
            request.getSession().setAttribute(golfCurrentUserDetailsError(), errorMsg);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher(address);
            dispatcher2.forward(request, response);            
            return;
        } 
        
        if (CurrentUser.getCurrentUser() == null) {
          CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
            
          // add the current user to the session
          request.getSession().setAttribute(golfCurrentUserDetailsHelper(), CurrentUser.golfUserDetails);
          
          // if its still null add a message to the session
          if (CurrentUser.getCurrentUser() == null) {
            // add the error message to the session
            request.getSession().setAttribute(golfCurrentUserDetailsError(), CurrentUser.getErrorMessage());
          }
         }

        addHelperToSession(golfUserDetailsHelper(), SessionData.READ);
       
        address = runRequest(operation);

        // add list to the request
        request.setAttribute(golfUserDetailsList(), getGolfUserList());
        
        // add averages to the request
        request.setAttribute(golfStatsTop10AverageDetails(), golfStatsTop10Average);
        
        // add list to the request
        request.setAttribute(golfStatsTop10DetailsList(), golfStatsList);    
        

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
        
    }
    
    protected void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            golfUserDetails = (GolfUser)((GolfUserControllerHelper)sessionHelper).getData();
        }
    }
    
    protected String getjspLocation(String page) {
        return "/GolfUserDetails/" + page;
    }
    
    public String logOnAsCurrentUser() {
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());

        if (success) {
            golfStatsList = CurrentUser.viewTop10StatsDetails();
            golfStatsTop10Average = CurrentUser.viewGolfStatsAverage(golfStatsList);
            return displayEnterStatsPage();
        } else {
          return displayHomePage();
        }
    }    
    
    public String newUserDetails() {
        golfUserDetails = new GolfUser();
        addHelperToSession(golfUserDetailsHelper(), SessionData.IGNORE);
        return displayEditUserPage();
    }
    
    
    public String editUserDetails() {
        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();
        GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());         
        
        golfUserDetails = golfUser.findGolfUser(new Integer(golfUserControllerData.getUserID()));
        addHelperToSession(golfUserDetailsHelper(), SessionData.IGNORE);
        return displayEditUserPage();
    }
    
    public String saveUserDetails() {
        String address = "";
        try {
          golfUserDetails.setUserId(new Integer(golfUserControllerData.getUserID()));
        } catch(NumberFormatException e) {
          golfUserDetails.setUserId(null);
        }
        golfUserDetails.setUserEmail(golfUserControllerData.getUserEmail());
        golfUserDetails.setUserFirstName(golfUserControllerData.getUserFirstName());
        golfUserDetails.setUserLastName(golfUserControllerData.getUserLastName());
        golfUserDetails.setUserName(golfUserControllerData.getUserName());
        golfUserDetails.setUserPwd(golfUserControllerData.getUserPwd());
        golfUserDetails.setUserRole(golfUserControllerData.getUserRole());
        addHelperToSession(golfUserDetailsHelper(), SessionData.IGNORE);

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();

        try { 
            etx.begin(); 
            if (golfUserDetails.getUserId() != null) {
              em.merge(golfUserDetails);
              address = displayEditUserPage();
            } else {
              em.persist(golfUserDetails);
              address = displayHomePage();
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

    public String viewUserDetails() {
        golfUserDetails = new GolfUser();
        addHelperToSession(golfUserDetailsHelper(), SessionData.READ);

          EntityManager em = CreateEntityManager.getEntityManager();
          EntityTransaction etx = em.getTransaction();
          GolfUserJpaController golfUserJPA = new GolfUserJpaController(em.getEntityManagerFactory());         

          golfUserList = golfUserJPA.findGolfUserEntities();
          for (GolfUser golfUser : golfUserList) {
               System.out.println(golfUser.getUserName());
          }
          
        return viewAllUserDetails();
    }
    
    public String deleteUserDetails() {
        // Save details to the database

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();        

        try {  
            etx.begin(); 
            if (golfUserDetails.getUserId() != null) {
              em.remove(em.merge(golfUserDetails));
            }
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println("deleteCourseDetails - remove : Exception : " + ex.getMessage());
        }
        
        return displayHomePage();
    }   

    public String displayHomePage() {
        return "./index.jsp";
    }

    public String displayStartPage() {
        return "./index.jsp";
    }
    
    public String displayEditUserPage() {
        return "./edit_user/edituser.jsp";
    }

    public String displayEnterStatsPage() {
        return "./enter_stats/enterstats.jsp";
    }    
    
    public String viewAllUserDetails() {
        return "./view_user/viewallusers.jsp";
    }
    
    public String golfUserDetailsHelper() {
        return "golfUserDetailsHelper";
    }

    public String golfUserDetailsList() {
        return "golfUserDetailsList";
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
    
    public static String golfCurrentUserDetailsError() {
        return "golfCurrentUserDetailsError";
    }
    
}
