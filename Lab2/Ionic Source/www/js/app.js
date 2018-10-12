// Ionic Starter App
// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic','ngCordova','starter.controllers','ngStorage','ngMessages'])
.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs).
    // The reason we default this to hidden is that native apps don't usually show an accessory bar, at
    // least on iOS. It's a dead giveaway that an app is using a Web View. However, it's sometimes
    // useful especially with forms, though we would prefer giving the user a little more room
    // to interact with the app.
    if (window.cordova && window.Keyboard) {
      window.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if (window.StatusBar) {
      // Set the statusbar to use the default style, tweak this to
      // remove the status bar on iOS or change it to use white instead of dark colors.
      StatusBar.styleDefault();
    }
  });
})
.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('app', {
    url: '/app',
    abstract: true,
    templateUrl: 'templates/menu.html',
    controller: 'AppCtrl'
  })
  .state('app.search', {
    url: '/search',
    views: {
      'menuContent': {
        templateUrl: 'templates/search.html'
      }
    }
  })
  .state('app.browse', {
      url: '/browse',
      views: {
        'menuContent': {
          templateUrl: 'templates/browse.html'
        }
      }
    })
    .state('app.playlists', {
      url: '/playlists',
      views: {
        'menuContent': {
          templateUrl: 'templates/playlists.html',
          controller: 'PlaylistsCtrl'
        }
      }
    })
  .state('app.single', {
    url: '/playlists/:playlistId',
    views: {
      'menuContent': {
        templateUrl: 'templates/playlist.html',
        controller: 'PlaylistCtrl'
      }
    }
  })
    .state('app.contacts', {
      url: '/contacts',
      views: {
        'menuContent': {
          templateUrl: 'templates/contacts.html'
        }
      }
    })
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/playlists');
})
.controller('loginRegister', function ($scope,$ionicModal,$localStorage,$ionicPopup,$window) {
  console.log("local value User "+ $localStorage.userNameLogin);
    $scope.loginPage=function () {
      console.log($scope.userName);
      console.log($scope.password);
      $scope.errortag=" ";
      console.log( $scope.errortag);
      x=true;
      if ($scope.userName==null||!$scope.password)
      {
        console.log("null");
        $scope.errortag="Mandatory Columns should be entered";
        x=false;
      }
      if ($localStorage.userNameLogin==$scope.userName && $localStorage.passwordLogin==$scope.password && $scope.userName!=null ) {
        console.log("inside why") ;
        $window.location.href = 'homePage.html';
      }
      else if(x==true) {
        $scope.errortag="Invalid Credentials";
      }
      else
      {
        console.log("no issue");
      }
    }
  })
  .controller('register', function ($scope, $ionicModal,$localStorage,$window) {
    $scope.registerPage=function () {
      $localStorage.userNameLogin="";
      $localStorage.userNameLogin = $scope.userName;
      $localStorage.passwordLogin = $scope.password;
      check=true;
      var x=$localStorage.userNameLogin;
      // console.log("local value User "+ $localStorage.userNameLogin);
      // console.log($scope.password);
      if (!$scope.userName || !$scope.password)
      {
        $scope.errortag="Mandatory Columns should be entered";
        check=false;
      }
      if($scope.password!=$scope.cPassword)
      {
        $scope.errortag="Passwords should be same";
        check=false;
      }
      if (check==true) {
        $window.location.href = 'index.html';
      }
    }
  })
  .controller('indexctrl', function($scope, $http) {
    $scope.getSearchResult = function() {
      $http.get("https://kgsearch.googleapis.com/v1/entities:search?query="+$scope.searchDestination+"&key=AIzaSyCWvzC7Mbg13R7bUOc2NLdBcFtEJfQse0k&limit=1&indent=True").then(function(data)
      {
        //alert("success triggered");
        try {
          console.log(data);
          $scope.searchDescription = data.data.itemListElement[0].result.detailedDescription.articleBody;
          $scope.description = "Description:";
          $scope.wiki = data.data.itemListElement[0].result.detailedDescription.url;
          $scope.wikiheading = "Explore " + $scope.searchDestination + " wiki in the following link";
          $scope.searchimage = data.data.itemListElement[0].result.image.contentUrl;
          document.getElementById("errormsg").innerHTML ="";
        }
        catch(err){
          // document.getElementById("errormsg").innerHTML = "Please Correct your search item";
        }
      })
    }
  })
  .controller('numctrl', function($scope, $http) {
        $scope.getResult = function () {
          $http.get("http://numbersapi.com/" + $scope.number + "/" + $scope.type).success(function (data) {
            try {
              console.log("hello");
              $scope.result = data;
            }
            catch (err) {
              // document.getElementById("errormsg").innerHTML = "Please Correct your search item";
            }
          })
        }
  })
  .controller("ExampleController", function($scope, $cordovaBarcodeScanner,$ionicPopup) {
    $scope.scanBarcode = function() {
      $cordovaBarcodeScanner.scan().then(function(imageData) {
        // alert(imageData.text);
        // var alertPopup = $ionicPopup.alert({title:imageData.text});
        $scope.searchDescription="Description: "+imageData.text;
        console.log("Barcode Format -> " + imageData.format);
        console.log("Cancelled -> " + imageData.cancelled);
      }, function(error) {
        console.log("An error happened -> " + error);
      });
    };
  });
function hideFunction() {
  var x = document.getElementById("resDIV");
  if (x.style.display === "none" ) {
    x.style.display = "block";
  }
}
function signOut() {
  console.log("in sign out");
  window.location.href="/index.html";
}
// document.getElementById("getPosition").addEventListener("click", createContact);
