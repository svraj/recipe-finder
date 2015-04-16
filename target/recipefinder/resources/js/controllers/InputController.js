'use strict';

/**
 * InputController
 * @constructor
 */


var InputController = function($scope, $http) {
    $scope.itemsCSV = '';
    $scope.recipeJson = '';
    $scope.editMode = false;
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
    
    var sampleFridgeItemsCSV = "\
    	bread,10,slices,25/12/2014\n\
    	cheese,10,slices,25/12/2014\n\
    	butter,250,grams,25/12/2014\n\
    	peanut butter,250,grams,2/12/2014\n\
    	mixed salad,150,grams,26/12/2013";
    
    
    $scope.submitRecipeJson = function(recipe) {
        $scope.resetError();

        $http.post('getinput/submitRecipeJson', recipe).success(function() {
            $scope.recipeJson = '';
            $scope.itemsCSV = '';
        }).error(function() {
            $scope.setError('Could not Submit Recipe Json List');
        });
    };
    
    $scope.submitFridgeItemCSV = function(recipe) {
        $scope.resetError();

        $http.post('getinput/submitFridgeItemCSV', recipe).success(function() {
            $scope.recipeJson = '';
            $scope.itemsCSV = '';
        }).error(function() {
            $scope.setError('Could not Submit Fridge Item CSV');
        });
    };
    
    $scope.setSampleRecipeJson = function() {
        $scope.resetError();
        $scope.recipeJson = JSON.stringify(sampleRecipe, undefined, 2);
    };
    
    $scope.setSampleFridgeItemsCSV = function() {
        $scope.resetError();
        $scope.itemsCSV = JSON.stringify(sampleFridgeItemsCSV, undefined, 2);
    };
    
  
    $scope.resetRecipeForm = function() {
        $scope.resetError();
        $scope.recipe = {};
        $scope.recipeJson ='';
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.predicate = 'id';
};