'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [])
    .controller('View1Ctrl', function ($scope, $http) {
        $scope.sendmail = function () {
            var placeEntered = $scope.formData.email;
            if (placeEntered != null && placeEntered != "") {
                $http.get('http://127.0.0.1:5000/getData?searchkey='+$scope.formData.email+'&searchkey1='+$scope.formData.mes+'&searchkey2='+$scope.formData.sub).then(function(data)
                {
                    alert(success);

                },function(err)
                {
                    alert("failed");
                    console.log(err);
                })
            }
        }

    });