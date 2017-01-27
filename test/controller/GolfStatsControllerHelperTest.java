package controller;

import golfdetails.GolfStats;
import golfdetails.GolfUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GolfStatsControllerHelperTest {
    
    public GolfStatsControllerHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of runRequest("saveStatsDetails") method, of class GolfStatsControllerHelper.
     */
    @Test
    public void testNewStatDetails() {
        String operation = "saveStatsDetails";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfStatsControllerData golfStatsControllerData = new GolfStatsControllerData();
        GolfUser golfUserDetails = null;
        GolfStats golfStatsDetails = null;
        
        String email = "1234567@hotmail.com";
        String fname = "FName";
        String lname = "LName";
        String userID = "";
        String userName = "NEWSTATS";
        String pswd = "DDDDDD";
        String role = "ADMIN";
        
        System.out.println("runRequest saveUserDetails - testNewUserDetails");
        
        golfUserControllerData.setOperation(operation);
        golfUserControllerData.setUserEmail(email);
        golfUserControllerData.setUserFirstName(fname);
        golfUserControllerData.setUserLastName(lname);
        golfUserControllerData.setUserID(userID);
        golfUserControllerData.setUserName(userName);
        golfUserControllerData.setUserPwd(pswd);
        golfUserControllerData.setUserRole(role);

        saveUser(golfUserControllerData);
        
        // Now select and check the new user was saved
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfUserDetails = CurrentUser.getCurrentUser();
        
        // create some stats and then save them and check they have been saved
        
        String statsID = "";
        String statsUserID = golfUserDetails.getUserId().toString();
        String statsCourseName = "Test 1 Course";
        String statsDatetime = "21/05/2017";
        String statsScore = "88";
        String statsCompType = "STROKE";
        String statsPutts = "33";     
        String statsGreens = "12";      
        String statsFairways = "14";        
        String statsTemp = "33";        
        String statsWind = "25";
        String statsCondition = "SUNNY"; 
        
        golfStatsControllerData.setOperation(operation);
        golfStatsControllerData.setStatsID(statsID);
        golfStatsControllerData.setStatsUserID(statsUserID);
        golfStatsControllerData.setStatsCourseName(statsCourseName);
        golfStatsControllerData.setStatsDatetime(statsDatetime);
        golfStatsControllerData.setStatsScore(statsScore);
        golfStatsControllerData.setStatsCompType(statsCompType);
        golfStatsControllerData.setStatsPutts(statsPutts);
        golfStatsControllerData.setStatsGreens(statsGreens);
        golfStatsControllerData.setStatsFairways(statsFairways);
        golfStatsControllerData.setStatsTemp(statsTemp);
        golfStatsControllerData.setStatsWind(statsWind);
        golfStatsControllerData.setStatsCondition(statsCondition);
        
        // Save new stats and select stats so can check
        golfStatsDetails = (GolfStats)saveStat(golfStatsControllerData);

        statsID = golfStatsDetails.getStatsId().toString();
        
        golfStatsControllerData.setStatsID(statsID);
        
        // clean up
        deleteStat(golfStatsControllerData);
        deleteUser(golfUserControllerData);

        String time = golfStatsControllerData.getStatsDatetime();
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
          date = format.parse(time);
        } catch (ParseException e) {
          System.out.println(e.getLocalizedMessage());
        }
        
        assertEquals(golfStatsDetails.getStatsUserId().getUserId().toString(), golfStatsControllerData.getStatsUserID());
        assertEquals(golfStatsDetails.getStatsCourseName(), golfStatsControllerData.getStatsCourseName());
        assertEquals(golfStatsDetails.getStatsDatetime(), date);
        assertEquals(golfStatsDetails.getStatsScore(), Integer.parseInt(golfStatsControllerData.getStatsScore()));
        assertEquals(golfStatsDetails.getStatsCompType(), golfStatsControllerData.getStatsCompType());
        assertEquals(golfStatsDetails.getStatsPutts(), Integer.parseInt(golfStatsControllerData.getStatsPutts()));    
        assertEquals(golfStatsDetails.getStatsGreens(), Integer.parseInt(golfStatsControllerData.getStatsGreens()));
        assertEquals(golfStatsDetails.getStatsFairways(), Integer.parseInt(golfStatsControllerData.getStatsFairways()));
        assertEquals(golfStatsDetails.getStatsTemp(), Double.parseDouble(golfStatsControllerData.getStatsTemp()), 0.01);
        assertEquals(golfStatsDetails.getStatsWind(), Double.parseDouble(golfStatsControllerData.getStatsWind()), 0.01);
        assertEquals(golfStatsDetails.getStatsCondition(), golfStatsControllerData.getStatsCondition());
    
        assertEquals(true, success);
    }

    /**
     * Test of runRequest("saveStatsDetails") method, of class GolfStatsControllerHelper.
     */
    @Test
    public void testUpdateStatsDetails() {
        String operation = "saveStatsDetails";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfStatsControllerData golfStatsControllerData = new GolfStatsControllerData();
        GolfUser golfUserDetails = null;
        GolfStats golfStatsDetails = null;
        
        String email = "1234567@hotmail.com";
        String fname = "FName";
        String lname = "LName";
        String userID = "";
        String userName = "NEWSTATS";
        String pswd = "DDDDDD";
        String role = "ADMIN";
        
        System.out.println("runRequest saveUserDetails - testNewUserDetails");
        
        golfUserControllerData.setOperation(operation);
        golfUserControllerData.setUserEmail(email);
        golfUserControllerData.setUserFirstName(fname);
        golfUserControllerData.setUserLastName(lname);
        golfUserControllerData.setUserID(userID);
        golfUserControllerData.setUserName(userName);
        golfUserControllerData.setUserPwd(pswd);
        golfUserControllerData.setUserRole(role);

        saveUser(golfUserControllerData);
        
        // Now select and check the new user was saved
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfUserDetails = CurrentUser.getCurrentUser();
        
        // create some stats and then save them and check they have been saved
        
        String statsID = "";
        String statsUserID = golfUserDetails.getUserId().toString();
        String statsCourseName = "Test 1 Course";
        String statsDatetime = "21/05/2017";
        String statsScore = "88";
        String statsCompType = "STROKE";
        String statsPutts = "33";     
        String statsGreens = "12";      
        String statsFairways = "14";        
        String statsTemp = "33";        
        String statsWind = "25";
        String statsCondition = "SUNNY"; 
        
        golfStatsControllerData.setOperation(operation);
        golfStatsControllerData.setStatsID(statsID);
        golfStatsControllerData.setStatsUserID(statsUserID);
        golfStatsControllerData.setStatsCourseName(statsCourseName);
        golfStatsControllerData.setStatsDatetime(statsDatetime);
        golfStatsControllerData.setStatsScore(statsScore);
        golfStatsControllerData.setStatsCompType(statsCompType);
        golfStatsControllerData.setStatsPutts(statsPutts);
        golfStatsControllerData.setStatsGreens(statsGreens);
        golfStatsControllerData.setStatsFairways(statsFairways);
        golfStatsControllerData.setStatsTemp(statsTemp);
        golfStatsControllerData.setStatsWind(statsWind);
        golfStatsControllerData.setStatsCondition(statsCondition);
        
        // Save new stats and select stats so can check
        golfStatsDetails = (GolfStats)saveStat(golfStatsControllerData);

        statsID = golfStatsDetails.getStatsId().toString();
        statsCourseName = "Test 2 Course";
        statsDatetime = "21/01/2017";
        statsScore = "66";
        statsCompType = "PAR";
        statsPutts = "24";     
        statsGreens = "4";      
        statsFairways = "7";        
        statsTemp = "41";        
        statsWind = "44";
        statsCondition = "CLOUDY"; 
        
        // update stats
        golfStatsControllerData.setStatsID(statsID);
        golfStatsControllerData.setStatsCourseName(statsCourseName);
        golfStatsControllerData.setStatsDatetime(statsDatetime);
        golfStatsControllerData.setStatsScore(statsScore);
        golfStatsControllerData.setStatsCompType(statsCompType);
        golfStatsControllerData.setStatsPutts(statsPutts);
        golfStatsControllerData.setStatsGreens(statsGreens);
        golfStatsControllerData.setStatsFairways(statsFairways);
        golfStatsControllerData.setStatsTemp(statsTemp);
        golfStatsControllerData.setStatsWind(statsWind);
        golfStatsControllerData.setStatsCondition(statsCondition);

        // Update new stats and select stats so can check
        golfStatsDetails = (GolfStats)saveStat(golfStatsControllerData);

        // clean up
        deleteStat(golfStatsControllerData);
        deleteUser(golfUserControllerData);

        String time = golfStatsControllerData.getStatsDatetime();
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
          date = format.parse(time);
        } catch (ParseException e) {
          System.out.println(e.getLocalizedMessage());
        }
        
        assertEquals(golfStatsDetails.getStatsUserId().getUserId().toString(), golfStatsControllerData.getStatsUserID());
        assertEquals(golfStatsDetails.getStatsCourseName(), golfStatsControllerData.getStatsCourseName());
        assertEquals(golfStatsDetails.getStatsDatetime(), date);
        assertEquals(golfStatsDetails.getStatsScore(), Integer.parseInt(golfStatsControllerData.getStatsScore()));
        assertEquals(golfStatsDetails.getStatsCompType(), golfStatsControllerData.getStatsCompType());
        assertEquals(golfStatsDetails.getStatsPutts(), Integer.parseInt(golfStatsControllerData.getStatsPutts()));    
        assertEquals(golfStatsDetails.getStatsGreens(), Integer.parseInt(golfStatsControllerData.getStatsGreens()));
        assertEquals(golfStatsDetails.getStatsFairways(), Integer.parseInt(golfStatsControllerData.getStatsFairways()));
        assertEquals(golfStatsDetails.getStatsTemp(), Double.parseDouble(golfStatsControllerData.getStatsTemp()), 0.01);
        assertEquals(golfStatsDetails.getStatsWind(), Double.parseDouble(golfStatsControllerData.getStatsWind()), 0.01);
        assertEquals(golfStatsDetails.getStatsCondition(), golfStatsControllerData.getStatsCondition());
    
        assertEquals(true, success);
    }   

    public Object saveStat(GolfStatsControllerData golfStatsControllerData) {
        String operation = "saveStatsDetails";
        GolfStatsControllerHelper golfStatsControllerHelper = new GolfStatsControllerHelper(null, null);
        golfStatsControllerHelper.setGolfStatsControllerData(golfStatsControllerData);
        golfStatsControllerHelper.runRequest(operation);
        return golfStatsControllerHelper.getData();
    }
    
    public String deleteStat(GolfStatsControllerData golfStatsControllerData) {
        String operation = "deleteStatsDetails";
        GolfStatsControllerHelper golfStatsControllerHelper = new GolfStatsControllerHelper(null, null);
        golfStatsControllerHelper.setGolfStatsControllerData(golfStatsControllerData);
        golfStatsControllerHelper.selectStatsDetails();
        String result = golfStatsControllerHelper.runRequest(operation);
        return result;
    }
    
    public Object selectStat(GolfStatsControllerData golfStatsControllerData) {
        GolfStatsControllerHelper golfStatsControllerHelper = new GolfStatsControllerHelper(null, null);
        golfStatsControllerHelper.setGolfStatsControllerData(golfStatsControllerData);
        golfStatsControllerHelper.selectStatsDetails();
        return golfStatsControllerHelper.getData();
    }    
    
    public String saveUser(GolfUserControllerData golfUserControllerData) {
        String operation = "saveUserDetails";
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        golfUserControllerHelper.setGolfUserControllerData(golfUserControllerData);
        String result = golfUserControllerHelper.runRequest(operation);
        return result;
    }
    
    public String deleteUser(GolfUserControllerData golfUserControllerData) {
        String operation = "deleteUserDetails";
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        GolfUser golfUserDetails = null;
        CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        golfUserDetails = CurrentUser.getCurrentUser();
        golfUserControllerHelper.setGolfUserDetails(golfUserDetails);
        String result = golfUserControllerHelper.runRequest(operation);
        return result;
    }
        
}
