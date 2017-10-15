var map;
var infowindow;
var position;
var timeout;
var lastTap = 0;
var currLocation;
var directionsDisplay;
var directionsService;
var currMarker;
var listMarkers = [];

function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(geoLocationSucess, geoLocationFailure, { timeout: 50000 });
    } else {
        geoLocationFailure("");
    }

    var moreButton = document.getElementById('more');
    moreButton.disabled = false;
    moreButton.addEventListener('click', function () {
        var location = currMarker.position;
        var service = new google.maps.places.PlacesService(map);
        service.textSearch({
            location: location,
            radius: 9000,
            query: 'golf'
        }, callback);

    });
}

function geoLocationSucess(position) {
    var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    currLocation = location;

  map = new google.maps.Map(document.getElementById('map'), {
    center: location,
    zoom: 12
  });

  var marker = new google.maps.Marker({
      map: map,
      position: location
  });
  google.maps.event.addListener(marker, 'click', function () {
      infowindow.setContent("Current Location v2");
      infowindow.open(map, this);
  });

  infowindow = new google.maps.InfoWindow();

  var service = new google.maps.places.PlacesService(map);

  service.textSearch({
      location: location,
      radius: 9000,
      query: 'golf'
  }, callback);

}

function geoLocationFailure(positionError) {
    var pyrmont = { lat: -33.867, lng: 151.195 };

    map = new google.maps.Map(document.getElementById('map'), {
        center: pyrmont,
        zoom: 15
    });

    infowindow = new google.maps.InfoWindow();

    var service = new google.maps.places.PlacesService(map);
    service.textSearch({
        location: location,
        radius: 9000,
        query: 'golf'
    }, callback);
}

function callback(results, status, pagination) {
  var addtoarray = true;
  var placesList = document.getElementById('places');

  if (status === google.maps.places.PlacesServiceStatus.OK) {
    for (var i = 0; i < results.length; i++) {
      // check name does not already exist
        addtoarray = true;
        for (var z = 0; z < listMarkers.length; z++) {
            if (listMarkers[z].name == results[i].name) {
                addtoarray = false; 
            }
        }


        if (addtoarray) {
            listMarkers.push(results[i]);
        }
    }

    for (var i = 0; i < results.length; i++) {
      createMarker(results[i]);
    }

    placesList.innerHTML = "";
    for (var i = 0; i < listMarkers.length; i++) {
      placesList.innerHTML += '<li>' + listMarkers[i].name + '</li>';
    }

  } else {
    alert('Request failed: ' + status);
  }
}

function createMarker(place) {
  var placeLoc = place.geometry.location;
  var marker = new google.maps.Marker({
    map: map,
    position: place.geometry.location
  });
  

  google.maps.event.addListener(marker, 'click', function () {
      var currentTime = new Date().getTime();
      var tapLength = currentTime - lastTap;

      if (tapLength < 1800 && tapLength > 0) {
          history.go(-1);
      }
      lastTap = currentTime;
  });

  google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(place.name);
        infowindow.open(map, this);

        if (currMarker == null || currMarker != marker) {
          var db = new Dexie("golfCourseName");
          db.version(1).stores({ golfCourse: 'id,placeName' });
          db.open();

          db.golfCourse.put({
              id: 1
             , placeName: place.name
          }).then(function () {
              return db.golfCourse.get(1);
          }).then(function (golfCourse) {
          });

          // display route on map from the current position to selected golf course/store
          if (directionsDisplay == null) {
              directionsDisplay = new google.maps.DirectionsRenderer();
              directionsService = new google.maps.DirectionsService();
          }

          directionsDisplay.setMap(map);
          directionsDisplay.setOptions({ suppressMarkers: true });
          var request = {
              origin: currLocation,
              destination: place.geometry.location,
              travelMode: google.maps.TravelMode.DRIVING
          };
          directionsService.route(request, function (result, status) {
              if (status == google.maps.DirectionsStatus.OK) {
                  directionsDisplay.setDirections(result);
              }

          });
      };

      currMarker = marker;

  });
}