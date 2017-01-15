<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View All Users</title>
    </head>
    <body>

        <div>View All Users</div>
        <form id="SearchUserDetails" action="/GolfStatsApplicationJSP/GolfUserController" method="POST">
            
            <input type="hidden" name="UserID" value="1">
           
            <br>
            
            <table>
                <tr>
                    <th>User ID</th>
                    <th>User Name</th> 
                    <th>First Name</th> 
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Edit</th>
                </tr>
                
                <c:forEach items="${golfUserDetailsList}" var="user">
                    <tr>
                        <td><c:out value="${user.userId}"/></td>
                        <td><c:out value="${user.userName}"/></td>  
                        <td><c:out value="${user.userFirstName}"/></td> 
                        <td><c:out value="${user.userLastName}"/></td> 
                        <td><c:out value="${user.userEmail}"/></td> 
                        <td><c:out value="${user.userPwd}"/></td> 
                        <td><c:out value="${user.userRole}"/></td> 
                        <td>
                            <form id="EditUser" action="/GolfStatsApplicationJSP/GolfUserController" method="POST">
                                <input type="hidden" name="UserID" value="${user.userId}">
                                <input type="submit" name="editUserDetails" value="Edit">
                            </form>
                        </td> 
                    </tr>
                </c:forEach>
                
            </table>

            <br><br>
            <input type="submit" name="displayHomePage" value="Back" />
            <input type="submit" name="newUserDetails" value="New" />
            
        </form>
        
    </body>
</html>
