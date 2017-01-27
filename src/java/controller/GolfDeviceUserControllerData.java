package controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class GolfDeviceUserControllerData {

    private String operation = "";
    private String deviceName = "";
    private String userName = "";
    private String userPwd = "";
    
    String ops[] = {"DeviceLogOn",
                    "DeviceCreateUser"};

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
         setDeviceName(request.getParameter("DeviceName"));
         setUserName(request.getParameter("UserName"));
         setUserPwd(request.getParameter("UserPwd"));
    }
    
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }   
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    
}
