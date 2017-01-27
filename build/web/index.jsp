<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Golf Stats Application</title>

        <link type="text/css" rel="stylesheet" href="css/main.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body>
        <input type="hidden" id="stats_id" />
        <div id="container">
          <img src="images/golf_banner.jpg" alt="no picture"/>
          <section id="left-column">
            <h1 id="app-title">Golf Stats Application</h1>
            <div>
                <table border="0">
                    <tr>
                        <td><font color="red" size="3">${golfCurrentUserDetailsError}</font></td>
                    </tr>
                </table>

             <form id="SearchUserDetails" action="/GolfStatsApplicationJSP/GolfUserController" method="POST">
              
                <table border="0">
                  <tr>
                    <td>User Name:</td>
                    <td><input type="text" name="UserName" autofocus /></td>
                  </tr>
                  <tr>
                    <td>Password:</td>
                    <td><input type="password" name="UserPwd" /></td>
                  </tr>
                  <tr>
                    <td></td>
                    <td><input type="submit" name="LogOn" value="Logon" /></td>
                  </tr>
                  <tr>
                    <td><input type="submit" name="newUserDetails" value="New User" /></td>
                    <td><a href="#">Forgot Password?</a></td>
                  </tr>
                </table>
              </form>

            </div>  
          </section>

        </div>
  
    </body>
</html>
