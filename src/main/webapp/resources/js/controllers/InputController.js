'use strict';

/**
 * InputController
 * @constructor
 */


var InputController = function($scope, $http) {
    $scope.itemsCSV = '';
    $scope.recipeJson = '';
    $scope.showStatus=false;
    var successStatusMessage='Submitted Successfully';
    var failedStatus = 'Submit Failed! - Please check the data format';
    var sampleRecipe = [ {
    	  "name": "grilled cheese on toast",
    	  "ingredients": [
    	   { "item":"bread", "amount":"2", "unit":"slices"},
    	   { "item":"cheese", "amount":"2", "unit":"slices"}
    	  ]
    	} , {
    	  "name": "salad sandwich",
    	  "ingredients": [
    	   { "item":"bread", "amount":"2", "unit":"slices"},
    	   { "item":"mixed salad", "amount":"100", "unit":"grams"}
    	  ]
    	} ];
    
    
    
    $scope.submitRecipeJson = function(recipe) {
        $scope.resetError();

        $http.post('getinput/submitRecipeJson', recipe).success(function() {
            $scope.recipeJson = '';
            $scope.statusMessage=successStatusMessage;
            $scope.showStatus=true;
            $scope.resetError();
        }).error(function() {
            $scope.setError(failedStatus);
            $scope.showStatus=false;
        });
    };
    
    $scope.submitFridgeItemCSV = function(recipe) {
        $scope.resetError();

        $http.post('getinput/submitFridgeItemCSV', recipe).success(function(submitStatus) {
            $scope.recipeJson = '';
            if(submitStatus=='FAILED'){
            	$scope.setError(failedStatus);
            	$scope.showStatus=false;
            }else{
            	$scope.statusMessage=successStatusMessage;
                 $scope.showStatus=true;
                 $scope.resetError();
            }
        }).error(function() {
            $scope.setError(failedStatus);
        });
    };
    
    $scope.setSampleRecipeJson = function() {
    	$scope.resetRecipeForm();
        $scope.resetError();
        $scope.recipeJson = JSON.stringify(sampleRecipe, undefined, 2);
    };
    
  
    $scope.resetRecipeForm = function() {
        $scope.resetError();
        $scope.recipe = {};
        $scope.recipeJson ='';
        $scope.showStatus=false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
        $scope.submitStatus = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.predicate = 'id';
};