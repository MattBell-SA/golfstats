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

public class GolfDeviceUserControllerHelper extends HelperBase{
    
    private GolfDevice golfDeviceDetails = new GolfDevice();
    private String unit_name = "GolfStatsApplicationJSPPU";
    private GolfDeviceUserControllerData golfDeviceUserControllerData = new GolfDeviceUserControllerData();
    private GolfUserControllerHelper helper = null;
    
    public GolfDeviceUserControllerHelper (HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        golfDeviceUserControllerData.getRequestDetails(request);
        helper = new GolfUserControllerHelper(request, response);
    }
    
    public Object getData() {
        return golfDeviceDetails;
    }

    public GolfDeviceUserControllerData getGolfDeviceUserControllerData() {
        return golfDeviceUserControllerData;
    }

    public void setGolfDeviceUserControllerData(GolfDeviceUserControllerData golfDeviceUserControllerData) {
        this.golfDeviceUserControllerData = golfDeviceUserControllerData;
    }

    public GolfUserControllerHelper getHelper() {
        return helper;
    }

    public void setHelper(GolfUserControllerHelper helper) {
        this.helper = helper;
    }
    
    public String runRequest(String operation) {
        
        String page = "";
        
        switch (operation) {
            case "DeviceLogOn":
                page = logOnAsUser();
                break;
            case "DeviceCreateUser":
                page = createAndLogOnAsUser();
                break;
            default:
                page = logOnAsUser(); 
        }
        
        return page;
    }        
    
    
    protected void doGet() throws ServletException, IOException {
        
        String address = "";
        
        if (!"DeviceLogOn".equals(golfDeviceUserControllerData.getOperation())
                && (!"DeviceCreateUser".equals(golfDeviceUserControllerData.getOperation()))) {
            throw new ServletException("method is not supported.");
        }
        
        addHelperToSession(golfDeviceUserDetailsHelper(), SessionData.READ);
        
        address = runRequest(golfDeviceUserControllerData.getOperation());

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
        
    }
    
    protected void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            golfDeviceDetails = (GolfDevice)((GolfDeviceUserControllerHelper)sessionHelper).getData();
        }
    }
    
    public String createAndLogOnAsUser() {
        boolean success = CurrentUser.setCurrentUser(golfDeviceUserControllerData.getUserName(), golfDeviceUserControllerData.getUserPwd());
        
        if (!success) {
           helper.saveUserDetails();
        }
        
        return logOnAsUser();
    }       
    
    public String logOnAsUser() {
        boolean success = CurrentUser.setCurrentUser(golfDeviceUserControllerData.getUserName(), golfDeviceUserControllerData.getUserPwd());
        
        if (success) {
        
            GolfUser golfUserDetails = CurrentUser.getCurrentUser();

            EntityManager em = CreateEntityManager.getEntityManager();
            EntityTransaction etx = em.getTransaction();
            GolfDeviceJpaControllerImplementation golfDeviceJpa = new GolfDeviceJpaControllerImplementation(em.getEntityManagerFactory());         

            golfDeviceDetails = golfDeviceJpa.findDeviceByUserIDDeviceID(golfUserDetails.getUserName(), golfDeviceUserControllerData.getDeviceName());

            if (golfDeviceDetails == null || golfDeviceDetails.getDeviceId() == null) {
                golfDeviceDetails.setDeviceIdentification(golfUserDetails.getUserName() + "+" + golfDeviceUserControllerData.getDeviceName());
                golfDeviceDetails.setDeviceName(golfDeviceUserControllerData.getDeviceName());
                golfDeviceDetails.setDeviceUserId(golfUserDetails);
                golfDeviceJpa.create(golfDeviceDetails);
            }

            addHelperToSession(golfDeviceUserDetailsHelper(), SessionData.IGNORE);
        }

        return deviceDetails();

    }

    public String deleteDeviceDetails() {
        // Save details to the database

        EntityManager em = CreateEntityManager.getEntityManager();
        EntityTransaction etx = em.getTransaction();        

        try {  
            etx.begin(); 
            if (golfDeviceDetails.getDeviceId() != null) {
              em.remove(em.merge(golfDeviceDetails));
            }
            etx.commit();
            em.close();
        } catch (Exception ex) {
            System.out.println("deleteCourseDetails - remove : Exception : " + ex.getMessage());
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
