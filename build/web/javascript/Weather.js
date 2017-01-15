function getWeather() {

    //var locationQuery = escape("select item from weather.forecast where woeid in (select woeid from geo.places where text='GB-LND') and u='c'"),
    // locationUrl = "http://query.yahooapis.com/v1/public/yql?q=" + locationQuery + "&format=json&callback
    //AIzaSyAs07-tRpUncok1w97BU95BwLWeHc6xqTo
    //var zipcode = $('#input-box').val();
    var queryString =
        "https://query.yahooapis.com/v1/public/yql?q=" +
        "select+*+from+weather.forecast+where+woeid=1099805 and u='c'" + "&format=json";

    $.getJSON(queryString, function (results) {
        if (results.query.count > 0) {
            var weather = results.query.results.channel;

            $('#description').text(weather.description);

            var wind = weather.wind;
            $('#wind').text(wind.speed);
            
            var conditions = weather.item.condition;
            $('#temp').text(conditions.temp);
            $('#text').text(conditions.text); 

            var atmosphere = weather.atmosphere;
            $('#humidity').text(atmosphere.humidity);
            $('#visibility').text(atmosphere.visibility);

            var astronomy = weather.astronomy;
            $('#sunrise').text(astronomy.sunrise);
            $('#sunset').text(astronomy.sunset);

            document.getElementById("StatsTemp").value = conditions.temp;
            document.getElementById("StatsWind").value = wind.speed;
            document.getElementById("StatsCondition").value = conditions.text;

        }

    });
    
}
