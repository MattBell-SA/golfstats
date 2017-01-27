package controller;

import golfdetails.GolfUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GolfUserControllerHelperTest {
    
    public GolfUserControllerHelperTest() {
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
     * Test of runRequest("saveUserDetails") method, of class GolfUserControllerHelper.
     */
    @Test
    public void testNewUserDetails() {
        String operation = "saveUserDetails";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfUser golfUserDetails = null;
        
        String email = "1234567@hotmail.com";
        String fname = "FName";
        String lname = "LName";
        String userID = "";
        String userName = "123456";
        String pswd = "ABCDE";
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
        
        // clean up
        deleteUser(golfUserControllerData);
        
        assertEquals(golfUserDetails.getUserEmail(), golfUserControllerData.getUserEmail());
        assertEquals(golfUserDetails.getUserFirstName(), golfUserControllerData.getUserFirstName());
        assertEquals(golfUserDetails.getUserLastName(), golfUserControllerData.getUserLastName());
        assertEquals(golfUserDetails.getUserName(), golfUserControllerData.getUserName());
        assertEquals(golfUserDetails.getUserPwd(), golfUserControllerData.getUserPwd());
        assertEquals(golfUserDetails.getUserRole(), golfUserControllerData.getUserRole());    
    
        assertEquals(true, success);
    }

    /**
     * Test of runRequest("deleteUserDetails") method, of class GolfUserControllerHelper.
     */
    @Test
    public void testDeleteUserDetails() {
        String operation = "deleteUserDetails";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        
        String email = "abcdefgh@hotmail.com";
        String fname = "Test1";
        String lname = "Test2";
        String userID = "";
        String userName = "AAAA20AA";
        String pswd = "123456";
        String role = "ADMIN";
        
        System.out.println("runRequest deleteUserDetails");
        
        golfUserControllerData.setOperation(operation);
        golfUserControllerData.setUserEmail(email);
        golfUserControllerData.setUserFirstName(fname);
        golfUserControllerData.setUserLastName(lname);
        golfUserControllerData.setUserID(userID);
        golfUserControllerData.setUserName(userName);
        golfUserControllerData.setUserPwd(pswd);
        golfUserControllerData.setUserRole(role);
        
        saveUser(golfUserControllerData);
        deleteUser(golfUserControllerData);
        
        // Now select and check the user was deleted
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());

        assertEquals(false, success);
    }

    
    /**
     * Test of runRequest("saveUserDetails") method, of class GolfUserControllerHelper.
     */
    @Test
    public void testUpdateUserDetails() {
        String operation = "saveUserDetails";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfUser golfUserDetails = null;
        
        String email = "1234567@hotmail.com";
        String fname = "FName";
        String lname = "LName";
        String userID = "";
        String userName = "123456";
        String pswd = "ABCDE";
        String role = "ADMIN";
        
        System.out.println("runRequest saveUserDetails - testUpdateUserDetails");
        
        golfUserControllerData.setOperation(operation);
        golfUserControllerData.setUserEmail(email);
        golfUserControllerData.setUserFirstName(fname);
        golfUserControllerData.setUserLastName(lname);
        golfUserControllerData.setUserID(userID);
        golfUserControllerData.setUserName(userName);
        golfUserControllerData.setUserPwd(pswd);
        golfUserControllerData.setUserRole(role);

        saveUser(golfUserControllerData);
        
        // Now select and check the user was saved
        CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfUserDetails = CurrentUser.getCurrentUser();

        email = "abcdefg@hotmail.com";
        fname = "Emane";
        lname = "Enamel";
        userID = golfUserDetails.getUserId().toString();
        userName = "XXXXXX";
        pswd = "QQQQQQQQ";
        role = "USER";
        
        System.out.println("runRequest saveUserDetails - testUpdateUserDetails");
        
        golfUserControllerData.setOperation(operation);
        golfUserControllerData.setUserEmail(email);
        golfUserControllerData.setUserFirstName(fname);
        golfUserControllerData.setUserLastName(lname);
        golfUserControllerData.setUserID(userID);
        golfUserControllerData.setUserName(userName);
        golfUserControllerData.setUserPwd(pswd);
        golfUserControllerData.setUserRole(role);
        
        saveUser(golfUserControllerData);
        
        // Now select and check the user was saved
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfUserDetails = CurrentUser.getCurrentUser();        
        
        // clean up
        deleteUser(golfUserControllerData);
        
        assertEquals(golfUserDetails.getUserEmail(), golfUserControllerData.getUserEmail());
        assertEquals(golfUserDetails.getUserFirstName(), golfUserControllerData.getUserFirstName());
        assertEquals(golfUserDetails.getUserLastName(), golfUserControllerData.getUserLastName());
        assertEquals(golfUserDetails.getUserName(), golfUserControllerData.getUserName());
        assertEquals(golfUserDetails.getUserPwd(), golfUserControllerData.getUserPwd());
        assertEquals(golfUserDetails.getUserRole(), golfUserControllerData.getUserRole());    
    
        assertEquals(true, success);
    }    
    
    

    public String saveUser(GolfUserControllerData golfUserControllerData) {
        String operation = "saveUserDetails";
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        String result = "";
        golfUserControllerHelper.setGolfUserControllerData(golfUserControllerData);
        result = golfUserControllerHelper.runRequest(operation);
        return result;
    }
    
    public String deleteUser(GolfUserControllerData golfUserControllerData) {
        String operation = "deleteUserDetails";
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        GolfUser golfUserDetails = null;
        String result = "";
        CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        golfUserDetails = CurrentUser.getCurrentUser();
        golfUserControllerHelper.setGolfUserDetails(golfUserDetails);
        result = golfUserControllerHelper.runRequest(operation);
        return result;
    }
    
    
    
 
    
}
