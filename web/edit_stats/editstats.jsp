<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/main.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Maintain Stats Details</title>
    </head>
    <body>
        <div id="container">
          <img src="images/golf_banner.jpg" alt="no picture"/>
            <h1 id="app-title">Maintain Stats Details</h1>
            <div>
                <br>
                <form id="MaintainStatsDetails" action="/GolfStatsApplicationJSP/GolfStatsController" method="POST">
                    <input type="hidden" name="StatsID" value="${golfStatsDetailsHelper.data.statsId}">
                    <input type="hidden" name="StatsUserID" value="${golfStatsDetailsHelper.data.statsUserId}">
                    
                    <fieldset>
                        <legend>Round Details</legend>
                        <label for="StatsCourseName">Course Name :</label>
                        <input type="text" name="StatsCourseName" value="${golfStatsDetailsHelper.data.statsCourseName}"><br>
                        <label for="StatsDatetime">Date :</label>
                        <input type="text" name="StatsDatetime" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${golfStatsDetailsHelper.data.statsDatetime}" />"><br>
                    </fieldset>
                    <fieldset>
                        <legend>Stats</legend>
                        <label for="StatsScore">Score :</label>
                        <input type="text" name="StatsScore" value="${golfStatsDetailsHelper.data.statsScore}"><br>
                        <label for="StatsCompType">Comp Type :</label>
                        <input type="text" name="StatsCompType" value="${golfStatsDetailsHelper.data.statsCompType}"><br>
                        <label for="StatsPutts">No. Putts :</label>
                        <input type="text" name="StatsPutts" value="${golfStatsDetailsHelper.data.statsPutts}"><br>
                        <label for="StatsGreens">No. Greens :</label>
                        <input type="text" name="StatsGreens" value="${golfStatsDetailsHelper.data.statsGreens}"><br>
                        <label for="StatsFairways">No. Fairways :</label>
                        <input type="text" name="StatsFairways" value="${golfStatsDetailsHelper.data.statsFairways}"><br>
                    </fieldset>
                    <fieldset>
                        <legend>Conditions</legend>
                        Temp : <input type="text" name="StatsTemp" value="${golfStatsDetailsHelper.data.statsTemp}"><br>
                        Wind : <input type="text" name="StatsWind" value="${golfStatsDetailsHelper.data.statsWind}"><br>
                        Cond : <input type="text" name="StatsCondition" value="${golfStatsDetailsHelper.data.statsCondition}"><br>
                    </fieldset>

                    <br>
                    <input type="submit" name="saveStatsDetailsBackViewAll" value="Save"/>
                    <input type="submit" name="editStatsBack" value="Back" />
                    <input type="submit" name="deleteStatsDetails" value="Delete" />

                </form>

            </div>  
          </div>
                    
    </body>
</html>
