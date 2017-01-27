package controller;

import golfdetails.CreateEntityManager;
import golfdetails.GolfStats;
import golfdetails.GolfStatsAverage;
import golfdetails.GolfStatsJpaController;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.util.List;
import javax.persistence.EntityManager;

public final class CurrentUser {

    protected static GolfUser golfUserDetails;
    static String unit_name = "GolfStatsApplicationJSPPU";
    static String errorMsg = "";
    
    public CurrentUser () {
    }
    
    public static String getErrorMessage() {
        return errorMsg;
    }
    
    public static GolfUser getCurrentUser() {
        return golfUserDetails;
    }
    
    public static void logOff() {
        golfUserDetails = null;
    }
    
    public static boolean setCurrentUser(String userName, String passWord) {
        boolean validCredentials = false;

        if (userName != null && passWord != null) {
            EntityManager em = CreateEntityManager.getEntityManager();
            GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());
            golfUserDetails = golfUser.findGolfUserByUserName(userName);
            
            if (golfUserDetails != null && golfUserDetails.getUserPwd().equals(passWord)){
                validCredentials = true; // log on was sucessful
            } else {
                errorMsg = "**Logon credentials are incorrect";
                golfUserDetails = null;
            }

        } else {
            errorMsg = "**Logon credentials are incorrect";
        }
        return validCredentials;// log on was not sucessful
    }

    public static List<GolfStats> viewTop10StatsDetails() {
        if (CurrentUser.getCurrentUser() == null) {
            return null;
        }
        EntityManager em = CreateEntityManager.getEntityManager();
        GolfStatsJpaController golfStatsJPA = new GolfStatsJpaController(em.getEntityManagerFactory());
        List<GolfStats> golfStatsList = golfStatsJPA.findTop10GolfStatsByUserName(CurrentUser.getCurrentUser());
        return golfStatsList;
    }
    
    public static List<GolfStats> viewAllStatsDetails() {
        if (CurrentUser.getCurrentUser() == null) {
            return null;
        }
        EntityManager em = CreateEntityManager.getEntityManager();
        GolfStatsJpaController golfStatsJPA = new GolfStatsJpaController(em.getEntityManagerFactory());
        List<GolfStats> golfStatsList = golfStatsJPA.findGolfStatsByUserName(CurrentUser.getCurrentUser());
        return golfStatsList;
    }    
    
    public static GolfStatsAverage viewGolfStatsAverage(List<GolfStats> golfStatsList) {
        GolfStatsAverage golfStatsAverage = new GolfStatsAverage();
        golfStatsAverage.setGolfStatsAverage(golfStatsList);
        return golfStatsAverage;
    }   
}
