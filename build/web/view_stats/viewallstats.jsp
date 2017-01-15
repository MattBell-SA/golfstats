<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/main.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View All Stats</title>
    </head>
    <body>

        <div id="container">
          <img src="images/golf_banner.jpg" alt="no picture"/>
          <h1 id="app-title">View All Stats</h1>
          <div>

                <form id="SearchStatsDetails" action="/GolfStatsApplicationJSP/GolfStatsController" method="POST">

                    <table>
                        <tr>
                            <th>Course Name</th> 
                            <th>Date</th> 
                            <th>Score</th>
                            <th>Comp Type</th>
                            <th>No. Putts</th>
                            <th>No. Greens</th>
                            <th>No. Fairways</th>
                            <th>Temp</th>
                            <th>Wind</th>
                            <th>Condition</th>
                            <th>Edit</th>
                        </tr>

                        <c:forEach items="${golfStatsDetailsList}" var="stats">
                            <tr>
                                <td><c:out value="${stats.statsCourseName}"/></td> 
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${stats.statsDatetime}" /></td> 
                                <td><c:out value="${stats.statsScore}"/></td> 
                                <td><c:out value="${stats.statsCompType}"/></td> 
                                <td><c:out value="${stats.statsPutts}"/></td> 
                                <td><c:out value="${stats.statsGreens}"/></td> 
                                <td><c:out value="${stats.statsFairways}"/></td> 
                                <td><c:out value="${stats.statsTemp}"/></td> 
                                <td><c:out value="${stats.statsWind}"/></td> 
                                <td><c:out value="${stats.statsCondition}"/></td> 
                                <td>
                                    <form id="EditStats" action="/GolfStatsApplicationJSP/GolfStatsController" method="POST">
                                        <input type="hidden" name="StatsID" value=<c:out value="${stats.statsId}"/>>
                                        <input type="hidden" name="StatsUserID" value=<c:out value="${stats.statsUserId}"/>>
                                        <input type="submit" name="editStatsDetails" value="Edit">
                                    </form>
                                </td> 
                            </tr>
                        </c:forEach>
                            
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
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
                            <td><B><c:out value="${golfStatsAverageDetails.scoreAvg}"/></B></td>
                            <td></td>
                            <td><B><c:out value="${golfStatsAverageDetails.puttsAvg}"/></B></td>
                            <td><B><c:out value="${golfStatsAverageDetails.greensAvg}"/></B></td>
                            <td><B><c:out value="${golfStatsAverageDetails.fairwaysAvg}"/></B></td>
                            <td><B><c:out value="${golfStatsAverageDetails.tempAvg}"/></B></td>
                            <td><B><c:out value="${golfStatsAverageDetails.windAvg}"/></B></td>
                            <td></td>
                            <td></td>

                        </tr>

                    </table>

                    <br />
                    <input type="submit" name="newStatsDetails" value="New" />

                </form>
            </div>  
          </div>
    </body>
</html>
