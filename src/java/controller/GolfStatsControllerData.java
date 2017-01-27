package controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class GolfStatsControllerData {
 
    private String operation = "";
    private String statsID = "";
    private String statsUserID = "";
    private String statsCourseName = "";
    private String statsDatetime = "";
    private String statsScore = "";
    private String statsCompType = "";
    private String statsPutts = "";     
    private String statsGreens = "";      
    private String statsFairways = "";        
    private String statsTemp = "";        
    private String statsWind = "";
    private String statsCondition = "";     
    
    String ops[] = {"newStatsDetails",
                    "editStatsDetails",
                    "saveStatsDetails",
                    "saveStatsDetailsBackViewAll",
                    "deleteStatsDetails",
                    "viewAllStatsDetails",
                    "editStatsBack",
                    "displayHomePage",
                    "logoff"};

    
    public void getRequestDetails(HttpServletRequest request) {
            
        if (request == null) {
            return;
        }
        
         String operation = Arrays.asList(ops).stream()
                            .filter((String op) -> request.getParameter(op) != null)
                            .collect(Collectors.toList())
                            .get(0);
         
         System.out.println("GolfStatsControllerData.defineOperation - operation :" + operation);
        
         setOperation(operation);
         setStatsID(request.getParameter("StatsID"));
         setStatsUserID(request.getParameter("StatsUserID"));
         setStatsCourseName(request.getParameter("StatsCourseName"));
         setStatsDatetime(request.getParameter("StatsDatetime"));
         setStatsScore(request.getParameter("StatsScore"));
         setStatsCompType(request.getParameter("StatsCompType"));
         setStatsPutts(request.getParameter("StatsPutts"));
         setStatsGreens(request.getParameter("StatsGreens"));
         setStatsFairways(request.getParameter("StatsFairways"));
         setStatsTemp(request.getParameter("StatsTemp"));
         setStatsWind(request.getParameter("StatsWind"));
         setStatsCondition(request.getParameter("StatsCondition"));
    }   
    
    public void setOperation(String operation) {
        this.operation = operation;
    } 
        
    public String getOperation() {
        return this.operation;
    }
    
    /**
     * @return the statsID
     */
    public String getStatsID() {
        return statsID;
    }

    /**
     * @param statsID the statsID to set
     */
    public void setStatsID(String statsID) {
        this.statsID = statsID;
    }

    /**
     * @return the statsUserID
     */
    public String getStatsUserID() {
        return statsUserID;
    }

    /**
     * @param statsUserID the statsUserID to set
     */
    public void setStatsUserID(String statsUserID) {
        this.statsUserID = statsUserID;
    }

    /**
     * @return the statsCourseName
     */
    public String getStatsCourseName() {
        return statsCourseName;
    }

    /**
     * @param statsCourseName the statsCourseName to set
     */
    public void setStatsCourseName(String statsCourseName) {
        this.statsCourseName = statsCourseName;
    }

    /**
     * @return the statsDatetime
     */
    public String getStatsDatetime() {
        return statsDatetime;
    }

    /**
     * @param statsDatetime the statsDatetime to set
     */
    public void setStatsDatetime(String statsDatetime) {
        this.statsDatetime = statsDatetime;
    }

    /**
     * @return the statsScore
     */
    public String getStatsScore() {
        return statsScore;
    }

    /**
     * @param statsScore the statsScore to set
     */
    public void setStatsScore(String statsScore) {
        this.statsScore = statsScore;
    }

    /**
     * @return the statsCompType
     */
    public String getStatsCompType() {
        return statsCompType;
    }

    /**
     * @param statsCompType the statsCompType to set
     */
    public void setStatsCompType(String statsCompType) {
        this.statsCompType = statsCompType;
    }

    /**
     * @return the statsPutts
     */
    public String getStatsPutts() {
        return statsPutts;
    }

    /**
     * @param statsPutts the statsPutts to set
     */
    public void setStatsPutts(String statsPutts) {
        this.statsPutts = statsPutts;
    }

    /**
     * @return the statsGreens
     */
    public String getStatsGreens() {
        return statsGreens;
    }

    /**
     * @param statsGreens the statsGreens to set
     */
    public void setStatsGreens(String statsGreens) {
        this.statsGreens = statsGreens;
    }

    /**
     * @return the statsFairways
     */
    public String getStatsFairways() {
        return statsFairways;
    }

    /**
     * @param statsFairways the statsFairways to set
     */
    public void setStatsFairways(String statsFairways) {
        this.statsFairways = statsFairways;
    }

    /**
     * @return the statsTemp
     */
    public String getStatsTemp() {
        return statsTemp;
    }

    /**
     * @param statsTemp the statsTemp to set
     */
    public void setStatsTemp(String statsTemp) {
        this.statsTemp = statsTemp;
    }

    /**
     * @return the statsWind
     */
    public String getStatsWind() {
        return statsWind;
    }

    /**
     * @param statsWind the statsWind to set
     */
    public void setStatsWind(String statsWind) {
        this.statsWind = statsWind;
    }

    /**
     * @return the statsCondition
     */
    public String getStatsCondition() {
        return statsCondition;
    }

    /**
     * @param statsCondition the statsCondition to set
     */
    public void setStatsCondition(String statsCondition) {
        this.statsCondition = statsCondition;
    }
    
}
