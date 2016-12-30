package controller;

import golfdetails.CreateEntityManager;
import golfdetails.GolfDevice;
import golfdetails.GolfDeviceJpaControllerImplementation;
import golfdetails.GolfUser;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

DeviceLogOn:
DeviceName
user_name
user_pwd

DeviceCreateUser:
DeviceName
user_name
user_pwd
UserID
UserEmail
UserFirstName
UserLastName
UserName
UserPwd
UserRole


*/

public class GolfDeviceUserControllerHelper extends HelperBase{
    private GolfDevice golfDeviceDetails = new GolfDevice();
    private String deviceName = "";   
    
    String unit_name = "GolfStatsApplicationJSPPU";
    
    public GolfDeviceUserControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    } 
    
    public Object getData() {
        return golfDeviceDetails;
    }
    
    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        
        deviceName = request.getParameter("DeviceName");

        if (request.getParameter("DeviceLogOn") == null
                && request.getParameter("DeviceCreateUser") == null) {
            throw new ServletException("method is not supported.");
        }  
        
        addHelperToSession(golfDeviceUserDetailsHelper(), SessionData.READ);

        if (request.getParameter("DeviceLogOn") != null)  {
            address = logOnAsUser();            
        } 
        
        if (request.getParameter("DeviceCreateUser") != null)  {
            address = createAndLogOnAsUser();            
        }         

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
        
    }
    
    protected void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            golfDeviceDetails = (GolfDevice)((GolfDeviceUserControllerHelper)sessionHelper).getData();
        }
    }
    
    public String createAndLogOnAsUser() {

        boolean success = CurrentUser.setCurrentUser(request);
        
        if (CurrentUser.getCurrentUser() == null) {
           GolfUserControllerHelper helper = new GolfUserControllerHelper(request, response);
           helper.saveUserDetails();
        }
        
        return logOnAsUser();
    }       
    
    public String logOnAsUser() {
        boolean success = CurrentUser.setCurrentUser(request);
        
        if (success) {
        
            GolfUser golfUserDetails = CurrentUser.getCurrentUser();

            EntityManager em = CreateEntityManager.getEntityManager();
            EntityTransaction etx = em.getTransaction();
            GolfDeviceJpaControllerImplementation golfDeviceJpa = new GolfDeviceJpaControllerImplementation(em.getEntityManagerFactory());         

            golfDeviceDetails = golfDeviceJpa.findDeviceByUserIDDeviceID(golfUserDetails.getUserName(), deviceName);

            if (golfDeviceDetails == null || golfDeviceDetails.getDeviceId() == null) {
                golfDeviceDetails.setDeviceIdentification(golfUserDetails.getUserName() + "+" + deviceName);
                golfDeviceDetails.setDeviceName(deviceName);
                golfDeviceDetails.setDeviceUserId(golfUserDetails);
                golfDeviceJpa.create(golfDeviceDetails);
            }

            addHelperToSession(golfDeviceUserDetailsHelper(), SessionData.IGNORE);
        }

        return deviceDetails();

    }    

    
    public String golfDeviceUserDetailsHelper() {
        return "golfDeviceUserDetailsHelper";
    }
    
    public String deviceDetails() {
        return "./device_user/deviceuser.jspx";
    }    
    
    
}
