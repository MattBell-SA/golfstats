package controller;

import golfdetails.CreateEntityManager;
import golfdetails.GolfStats;
import golfdetails.GolfStatsJpaController;
import golfdetails.GolfUser;
import golfdetails.GolfUserJpaController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

public final class CurrentUser {

    protected static GolfUser golfUserDetails;
    static String unit_name = "GolfStatsApplicationJSPPU";
    static String errorMsg = "";
    
    public CurrentUser () {
    }
    
    public static GolfUser getCurrentUser() {
        return golfUserDetails;
    }
    
    public static void logOff() {
        golfUserDetails = null;
    }
    
    public static boolean setCurrentUser(HttpServletRequest request) {
        String userName = "";
        String password = "";
        boolean validCredentials = false;
        
        errorMsg = "";
        userName = request.getParameter("user_name");
        password = request.getParameter("user_pwd");

        if (userName != null && password != null) {
            EntityManager em = CreateEntityManager.getEntityManager();
            GolfUserJpaController golfUser = new GolfUserJpaController(em.getEntityManagerFactory());
            golfUserDetails = golfUser.findGolfUserByUserName(userName);
            
            if (golfUserDetails != null && golfUserDetails.getUserPwd().equals(password)){
                request.getSession().setAttribute(golfCurrentUserDetailsHelper(), golfUserDetails);
                validCredentials = true; // log on was sucessful
            } else {
                errorMsg = "**Logon credentials are incorrect";
            }

        } else {
            errorMsg = "**Logon credentials are incorrect";
        }
        
        // add the error message to the session
        request.getSession().setAttribute(golfCurrentUserDetailsError(), errorMsg);
        
        return validCredentials;// log on was not sucessful
    }
    
    public static String golfCurrentUserDetailsHelper() {
        return "golfCurrentUserDetailsHelper";
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
    
    public static void viewTop10StatsDetails(HttpServletRequest request) {
        
        if (CurrentUser.getCurrentUser() == null) {
            return;
        }

        EntityManager em = CreateEntityManager.getEntityManager();
        GolfStatsJpaController golfStatsJPA = new GolfStatsJpaController(em.getEntityManagerFactory());
        
        
        List<GolfStats> golfStatsList = golfStatsJPA.findTop10GolfStatsByUserName(CurrentUser.getCurrentUser());
        GolfStatsAverage golfStatsTop10Average = new GolfStatsAverage();
        golfStatsTop10Average.setGolfStatsAverage(golfStatsList);

        // add averages to the request
        request.setAttribute(golfStatsTop10AverageDetails(), golfStatsTop10Average);
        
        // add list to the request
        request.setAttribute(golfStatsTop10DetailsList(), golfStatsList);

    }
    
    public static class GolfStatsAverage {
        double scoreTotal;
        double puttsTotal;
        double greensTotal;
        double fairwaysTotal;
        double tempTotal;
        double windTotal;
        
        double scoreAvg;
        double puttsAvg;
        double greensAvg;
        double fairwaysAvg;
        double tempAvg;
        double windAvg;

        BigDecimal bd;

        public double getScoreTotal() {
            return scoreTotal;
        }

        public void setScoreTotal(double scoreTotal) {
            this.scoreTotal = scoreTotal;
        }

        public double getPuttsTotal() {
            return puttsTotal;
        }

        public void setPuttsTotal(double puttsTotal) {
            this.puttsTotal = puttsTotal;
        }

        public double getGreensTotal() {
            return greensTotal;
        }

        public void setGreensTotal(double greensTotal) {
            this.greensTotal = greensTotal;
        }

        public double getFairwaysTotal() {
            return fairwaysTotal;
        }

        public void setFairwaysTotal(double fairwaysTotal) {
            this.fairwaysTotal = fairwaysTotal;
        }
        
        public double getScoreAvg() {
            return scoreAvg;
        }

        public void setScoreAvg(double scoreAvg) {
            this.scoreAvg = round(scoreAvg);
        }

        public double getPuttsAvg() {
            return puttsAvg;
        }

        public void setPuttsAvg(double puttsAvg) {
            this.puttsAvg = round(puttsAvg);
        }

        public double getGreensAvg() {
            return greensAvg;
        }

        public void setGreensAvg(double greensAvg) {
            this.greensAvg = round(greensAvg);
        }

        public double getFairwaysAvg() {
            return fairwaysAvg;
        }

        public void setFairwaysAvg(double fairwaysAvg) {
            this.fairwaysAvg = round(fairwaysAvg);
        }
        
        public double getTempTotal() {
            return tempTotal;
        }

        public void setTempTotal(double tempTotal) {
            this.tempTotal = tempTotal;
        }

        public double getWindTotal() {
            return windTotal;
        }

        public void setWindTotal(double windTotal) {
            this.windTotal = windTotal;
        }

        public double getTempAvg() {
            return tempAvg;
        }

        public void setTempAvg(double tempAvg) {
            this.tempAvg = round(tempAvg);
        }

        public double getWindAvg() {
            return windAvg;
        }

        public void setWindAvg(double windAvg) {
            this.windAvg = round(windAvg);
        }
        
        public double round(double value) {
            int places = 2;
            bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            //bd = bd.setScale(places);
            return bd.doubleValue();
        }
        
        
        public void setGolfStatsAverage(List<GolfStats> golfStatsList) {
            double count = 0;
            for (GolfStats golfStats : golfStatsList) {
                // calculate the average
                setScoreTotal(getScoreTotal() + golfStats.getStatsScore());
                setPuttsTotal(getPuttsTotal() + golfStats.getStatsPutts());
                setGreensTotal(getGreensTotal() + golfStats.getStatsGreens());
                setFairwaysTotal(getFairwaysTotal() + golfStats.getStatsFairways());
                setTempTotal(getTempTotal() + golfStats.getStatsTemp());
                setWindTotal(getWindTotal() + golfStats.getStatsWind());
                count++;
            }

            if (count > 0) {
                setScoreAvg(getScoreTotal() / count);
                setPuttsAvg(getPuttsTotal() / count);
                setGreensAvg(getGreensTotal() / count);
                setFairwaysAvg(getFairwaysTotal() / count);
                setTempAvg(getTempTotal() / count);
                setWindAvg(getWindTotal() / count);
            }
        }
        
        
    }
    
    
}
