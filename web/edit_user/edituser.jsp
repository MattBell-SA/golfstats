<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <link type="text/css" rel="stylesheet" href="css/main.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Maintain User</title>
    </head>
    <body>
        <div id="container">
          <img src="images/golf_banner.jpg" alt="no picture"/>
          <section id="left-column">
            <h1 id="app-title">User Details</h1>
            <div>
                <br>

                <form id="MaintainUserDetails" action="/GolfStatsApplicationJSP/GolfUserController" method="POST">

                    <input type="hidden" name="UserID" value="${golfUserDetailsHelper.data.userId}">
                    
                    <table>
                        <tr>
                            <td>User Name :</td>
                            <td><input type="text" name="UserName" value="${golfUserDetailsHelper.data.userName}"></td>
                        </tr>
                        <tr>
                            <td>First Name :</td>
                            <td><input type="text" name="UserFirstName" value="${golfUserDetailsHelper.data.userFirstName}"></td>
                        </tr>                    
                        <tr>
                            <td>Last Name :</td>
                            <td><input type="text" name="UserLastName" value="${golfUserDetailsHelper.data.userLastName}"></td>
                        </tr>                      
                        <tr>
                            <td>Email :</td>
                            <td><input type="text" name="UserEmail" value="${golfUserDetailsHelper.data.userEmail}"></td>
                        </tr>                       
                        <tr>
                            <td>Password :</td>
                            <td><input type="password" name="UserPwd" value="${golfUserDetailsHelper.data.userPwd}"></td>
                        </tr>                       
                    </table>

                    <br><br>
                    <input type="submit" name="saveUserDetails" value="Save"/>
                    <input type="submit" name="editUserBack" value="Back" />
                    <input type="submit" name="deleteUserDetails" value="Delete" />

                </form>
            
            </div>  
          </section>

        </div>
            
    </body>
</html>
