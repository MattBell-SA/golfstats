/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.CurrentUser.golfCurrentUserDetailsError;
import golfdetails.CreateEntityManager;
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
    
    protected GolfUser golfUserDetails = new GolfUser();
    String unit_name = "GolfStatsApplicationJSPPU";
    
    public GolfUserControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    } 
    
    public Object getData() {
        return golfUserDetails;
    }
    
    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        
        String errorMsg = "n";
        
        if ((CurrentUser.getCurrentUser() == null) && 
                ( (request.getParameter("editUserDetails") != null) 
                || (request.getParameter("deleteUserDetails") != null) 
                || (request.getParameter("viewAllUserDetails") != null) )) {
            address = displayHomePage();
            errorMsg = "**No current user";
            // add the error message to the session
            request.getSession().setAttribute(golfCurrentUserDetailsError(), errorMsg);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher(address);
            dispatcher2.forward(request, response);            
            return;
        }  
        
        addHelperToSession(golfUserDetailsHelper(), SessionData.READ);
       
        if (request.getParameter("newUserDetails") != null)  {
            address = newUserDetails();
        } 
        
        if (request.getParameter("editUserDetails") != null)  {
            address = editUserDetails();
        } 
        
        if (request.getParameter("saveUserDetails") != null)  {
            address = saveUserDetails();            
        }
        
        if (request.getParameter("deleteUserDetails") != null)  {
            address = deleteUserDetails();            
        }
        
        if (request.getParameter("viewAllUserDetails") != null)  {
            address = viewUserDetails();            
        }
        
        if (request.getParameter("editUserBack") != null)  {
            address = displayHomePage();            
        }
        
        if (request.getParameter("displayHomePage") != null)  {
            address = displayHomePage();            
        }
        
        if (request.getParameter("LogOn") != null)  {
            address = logOnAsCurrentUser();            
        } 

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
        boolean success = CurrentUser.setCurrentUser(request);

        if (success) {
            CurrentUser.viewTop10StatsDetails(request);
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
        
        golfUserDetails = golfUser.findGolfUser(new Integer(request.getParameter("UserID")));
        addHelperToSession(golfUserDetailsHelper(), SessionData.IGNORE);
        return displayEditUserPage();
    }
    
    public String saveUserDetails() {
        String address = "";
        try {
          golfUserDetails.setUserId(new Integer(request.getParameter("UserID")));
        } catch(NumberFormatException e) {
          golfUserDetails.setUserId(null);
        }
        golfUserDetails.setUserEmail(request.getParameter("UserEmail"));
        golfUserDetails.setUserFirstName(request.getParameter("UserFirstName"));
        golfUserDetails.setUserLastName(request.getParameter("UserLastName"));
        golfUserDetails.setUserName(request.getParameter("UserName"));
        golfUserDetails.setUserPwd(request.getParameter("UserPwd"));
        golfUserDetails.setUserRole(request.getParameter("UserRole"));
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

          List<GolfUser> golfUserList = golfUserJPA.findGolfUserEntities();
          for (GolfUser golfUser : golfUserList) {
               System.out.println(golfUser.getUserName());
          }
          
          // add list to the request
          request.setAttribute(golfUserDetailsList(), golfUserList);
        
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
    
}
