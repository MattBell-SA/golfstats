<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Enter Golf Stats</title>

        <link type="text/css" rel="stylesheet" href="css/main.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <script src="javascript/Dexie.js"></script>
        <script src="javascript/EnterStats.js"></script>
        <script src="javascript/Weather.js"></script>
        <script src="javascript/jquery-2.1.4.js"></script>

    </head>
    <body>
        <input type="hidden" id="stats_id" />
        <input type="hidden" id="user_id" />
        <div id="container">
          <img src="images/golf_banner.jpg" alt="no picture"/>
          <section id="left-column">
            <h1 id="app-title">Golf Stats Application</h1>
            <div>
                
            <form id="MaintainStatsDetails" action="/GolfStatsApplicationJSP/GolfStatsController" method="POST">

              <table id=myTable border=0>
                <tr>
                  <td>Course Name:</td>
                  <td><input type="text" name="StatsCourseName" id="StatsCourseName" style="width: 200px"/></td>
                  <td></td>
                </tr>  
                <tr> 
                  <td></td>  
                  <td>
                      <form>
                        <input type="button" value="Select Golf Course" onclick="viewGolfCourses()" > 
                      </form>
                  </td>
                  <td></td>
                </tr>

                <tr>
                  <td>Date:</td>
                    <td><input type="date" name="StatsDatetime" id="StatsDatetime" /></td>
                  <td></td>
                </tr>

                <tr>
                  <td>Stroke Score:</td>
                  <td><select name="StatsScore" id="StatsScore"></select></td>
                  <td></td>
                </tr>

                <tr>
                  <td>Competition Type:</td>
                  <td><select name="StatsCompType" id="StatsCompType">
                      <option value="Stableford" selected>Stableford</option>
                      <option value="Stroke">Stroke</option>
                      <option value="Par">Par</option>
                      </select>
                  </td>
                  <td></td>
                </tr>

                <tr>
                  <td>No. Putts:</td>
                  <td><select name="StatsPutts" id="StatsPutts"></select></td>  
                  <td></td>
                </tr>

                <tr>
                  <td>No. Greens:</td>
                  <td><select name="StatsGreens" id="StatsGreens"></select></td>
                  <td></td>
                </tr>

                <tr>
                  <td>No. Fairways:</td>
                  <td><select name="StatsFairways" id="StatsFairways"></select></td>
                  <td></td>
                </tr>

                <tr>
                  <td colspan="3"><br /><h3 name="description" id="description">Weather</h3><br /></td>
                </tr>
                <tr>
                  <td>Temp:</td>
                  <td><input type="text" name="StatsTemp" id="StatsTemp" /></td>
                  <td></td>
                </tr>

                <tr>
                  <td>Wind:</td>
                  <td><input type="text" name="StatsWind" id="StatsWind" /></td>
                  <td></td>
                </tr>

                <tr>
                  <td>Conditions:</td>
                  <td><input type="text" name="StatsCondition" id="StatsCondition" /></td>
                  <td></td>
                </tr>               
              </table>
              <br/>
              <table>
                <tr>
                  <td><input type="submit" name="saveStatsDetails" value="Save"/></td>
                  <td><input type="submit" name="viewAllStatsDetails" value="View Stats" /></td>
                  <td><input type="submit" name="logoff" value="Log Off" /></td>
                </tr>                 
              </table>
                
            </form> 

            </div>  
          </section>
        
          <section id="right-column">
            <h3>Top 10 Scores</h3>
            
            
            <table class="top10table" >
                <tr>
                    <th>Course Name</th> 
                    <th>Date</th> 
                    <th>Score</th>
                    <th>No. Putts</th>
                    <th>No. Greens</th>
                    <th>No. Fairways</th>
                </tr>
                
                <c:forEach items="${golfStatsTop10DetailsList}" var="stats">
                    <tr>
                        <td><c:out value="${stats.statsCourseName}"/></td> 
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${stats.statsDatetime}" /> </td> 
                        <td><c:out value="${stats.statsScore}"/></td> 
                        <td><c:out value="${stats.statsPutts}"/></td> 
                        <td><c:out value="${stats.statsGreens}"/></td> 
                        <td><c:out value="${stats.statsFairways}"/></td> 
                    </tr>
                </c:forEach>
                    
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    
                    <td><B>AVG:</B></td>
                    <td></td>
                    <td><B><c:out value="${golfStatsTop10AverageDetails.scoreAvg}"/></B></td>
                    <td><B><c:out value="${golfStatsTop10AverageDetails.puttsAvg}"/></B></td>
                    <td><B><c:out value="${golfStatsTop10AverageDetails.greensAvg}"/></B></td>
                    <td><B><c:out value="${golfStatsTop10AverageDetails.fairwaysAvg}"/></B></td>
                    
                </tr>

            </table>

          </section>
        </div>
  
    </body>
</html>
