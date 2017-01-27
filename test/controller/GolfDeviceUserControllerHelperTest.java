package controller;

import golfdetails.GolfDevice;
import golfdetails.GolfUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GolfDeviceUserControllerHelperTest {
    
    public GolfDeviceUserControllerHelperTest() {
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
     * Test of runRequest("DeviceCreateUser") method, of class GolfDeviceUserControllerHelper.
     */
    @Test
    public void testDeviceCreateUser() {
        String operation = "DeviceCreateUser";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfDeviceUserControllerData golfDeviceUserControllerData = new GolfDeviceUserControllerData();
        GolfDeviceUserControllerHelper golfDeviceUserControllerHelper = new GolfDeviceUserControllerHelper(null, null);
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        GolfDevice golfDeviceDetails = new GolfDevice();
        
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
        
        
        // set the device details
        String deviceName = "Samsung Galaxy";
        
        golfDeviceUserControllerData.setDeviceName(deviceName);
        golfDeviceUserControllerData.setUserName(userName);
        golfDeviceUserControllerData.setUserPwd(pswd);
        
        golfDeviceUserControllerHelper.setGolfDeviceUserControllerData(golfDeviceUserControllerData);
        golfUserControllerHelper.setGolfUserControllerData(golfUserControllerData);
        golfDeviceUserControllerHelper.setHelper(golfUserControllerHelper);
        
        golfDeviceUserControllerHelper.runRequest(operation);
        
        golfDeviceDetails = (GolfDevice)golfDeviceUserControllerHelper.getData();

        // Now select and check the user was deleted
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfDeviceUserControllerHelper.deleteDeviceDetails();
        deleteUser(golfUserControllerData);

        assertEquals(golfDeviceDetails.getDeviceName(), golfDeviceUserControllerData.getDeviceName());
        assertEquals(golfDeviceDetails.getDeviceUserId().getUserName(), golfDeviceUserControllerData.getUserName());
        assertEquals(golfDeviceDetails.getDeviceUserId().getUserPwd(), golfDeviceUserControllerData.getUserPwd());
        
        assertEquals(true, success);
    }

    /**
     * Test of runRequest("DeviceLogOn") method, of class GolfDeviceUserControllerHelper.
     */
    @Test
    public void testDeviceLogOn() {
        String operation = "DeviceLogOn";
        GolfUserControllerData golfUserControllerData = new GolfUserControllerData();
        GolfDeviceUserControllerData golfDeviceUserControllerData = new GolfDeviceUserControllerData();
        GolfDeviceUserControllerHelper golfDeviceUserControllerHelper = new GolfDeviceUserControllerHelper(null, null);
        GolfUserControllerHelper golfUserControllerHelper = new GolfUserControllerHelper(null, null);
        GolfDevice golfDeviceDetails = new GolfDevice();
        
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
        
        // set the device details
        String deviceName = "Samsung Galaxy";
        
        golfDeviceUserControllerData.setDeviceName(deviceName);
        golfDeviceUserControllerData.setUserName(userName);
        golfDeviceUserControllerData.setUserPwd(pswd);
        
        golfDeviceUserControllerHelper.setGolfDeviceUserControllerData(golfDeviceUserControllerData);
        golfUserControllerHelper.setGolfUserControllerData(golfUserControllerData);
        golfDeviceUserControllerHelper.setHelper(golfUserControllerHelper);
        
        golfDeviceUserControllerHelper.runRequest(operation);
        
        golfDeviceDetails = (GolfDevice)golfDeviceUserControllerHelper.getData();

        // Now select and check the user was deleted
        boolean success = CurrentUser.setCurrentUser(golfUserControllerData.getUserName(), golfUserControllerData.getUserPwd());
        
        golfDeviceUserControllerHelper.deleteDeviceDetails();
        deleteUser(golfUserControllerData);

        assertEquals(golfDeviceDetails.getDeviceName(), golfDeviceUserControllerData.getDeviceName());
        assertEquals(golfDeviceDetails.getDeviceUserId().getUserName(), golfDeviceUserControllerData.getUserName());
        assertEquals(golfDeviceDetails.getDeviceUserId().getUserPwd(), golfDeviceUserControllerData.getUserPwd());
        
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
