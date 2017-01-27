package controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class GolfUserControllerData {
    
    private String operation = "";
    private String userID = "";
    private String userEmail = "";
    private String userFirstName = "";
    private String userLastName = "";
    private String userName = "";
    private String userPwd = "";
    private String userRole = "";

    String ops[] = {"editUserDetails",
                    "deleteUserDetails",
                    "viewAllUserDetails",
                    "newUserDetails",
                    "editUserDetails",
                    "saveUserDetails",
                    "viewAllUserDetails",
                    "editUserBack",
                    "displayHomePage",
                    "LogOn"};
    
    public void getRequestDetails(HttpServletRequest request) {
        
        if (request == null) {
            return;
        }
    
         String operation = Arrays.asList(ops).stream()
                            .filter((String op) -> request.getParameter(op) != null)
                            .collect(Collectors.toList())
                            .get(0);
         
         System.out.println("GolfUserControllerData.defineOperation - operation :" + operation);
        
         setOperation(operation);
         setUserID(request.getParameter("UserID"));
         setUserEmail(request.getParameter("UserEmail"));
         setUserFirstName(request.getParameter("UserFirstName"));
         setUserLastName(request.getParameter("UserLastName"));
         setUserName(request.getParameter("UserName"));
         setUserPwd(request.getParameter("UserPwd"));
         setUserRole(request.getParameter("UserRole"));
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    } 
        
    public String getOperation() {
        return this.operation;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}
