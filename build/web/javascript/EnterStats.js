 
  
        function newDexieGolfEntry() {

            var db = new Dexie("golfDatabase");
            db.version(1).stores({ golf: 'date,strokescore,comptype,numputts,numgreens,numfairways,temp,wind' });

            db.open();

            db.golf.put({
                date: document.getElementById("datepicker").value
               , strokescore: document.getElementById("stroke-score").value
               , comptype: document.getElementById("competion-type").value
               , numputts: document.getElementById("num-putts").value
               , numgreens: document.getElementById("num-greens").value
               , numfairways: document.getElementById("num-fairways").value
               , temp: document.getElementById("temp").value
               , wind: document.getElementById("wind").value
            }).then(function () {
                return db.golf.get(document.getElementById("datepicker").value);
            }).then(function (golf) {
                document.getElementById("datepicker").value = golf.date;
                document.getElementById("stroke-score").value = golf.strokescore;
                document.getElementById("competion-type").value = golf.comptype;
                document.getElementById("num-putts").value = golf.numputts;
                document.getElementById("num-greens").value = golf.numgreens;
                document.getElementById("num-fairways").value = golf.numfairways;
                document.getElementById("temp").value = golf.temp;
                document.getElementById("wind").value = golf.wind;
                alert("Data saved");
            });
        }

        function viewGolfStats() {
            window.location.href = "./enter_stats/enterstats.jsp";
        }

        function viewGolfCourses() {
            window.location.href = "./findCourse/courseNavigation.html";
        }

        function loadWindow() {
            var db = new Dexie("golfCourseName");
            db.version(1).stores({ golfCourse: 'id,placeName' });
            db.open();

            db.golfCourse.get(1).then(function (golfCourse) {
                if (golfCourse != null) {
                    document.getElementById("StatsCourseName").value = golfCourse.placeName;
                }
            });

          var scoreSelect = document.getElementById('StatsScore');
          for (var z = 20; z <= 150; z++) {
              if (z == 75) {
                scoreSelect.innerHTML += '<option value=' + z + ' selected>' + z + '</option>';
              } else {
                scoreSelect.innerHTML += '<option value=' + z + '>' + z + '</option>';
              }
          }

          var puttsSelect = document.getElementById('StatsPutts');
          for (var z = 0; z <= 50; z++) {
              if (z == 33) {
                puttsSelect.innerHTML += '<option value=' + z + ' selected>' + z + '</option>';
              } else {
                puttsSelect.innerHTML += '<option value=' + z + '>' + z + '</option>';
              }
          } 
          
          var greensSelect = document.getElementById('StatsGreens');
          for (var z = 0; z <= 18; z++) {
              if (z == 10) {
                greensSelect.innerHTML += '<option value=' + z + ' selected>' + z + '</option>';
              } else {
                greensSelect.innerHTML += '<option value=' + z + '>' + z + '</option>';
              }
          }

          var fairwaysSelect = document.getElementById('StatsFairways');
          for (var z = 0; z <= 18; z++) {
              if (z == 10) {
                fairwaysSelect.innerHTML += '<option value=' + z + ' selected>' + z + '</option>';
              } else {
                fairwaysSelect.innerHTML += '<option value=' + z + '>' + z + '</option>';
              }
          }

          getWeather();

        }
        window.onload = loadWindow;

 