'use strict';

/**
 * SuggestionController
 * @constructor
 */
var SuggestionController = function($scope, $http,$filter) {
    $scope.recipe = {};
    $scope.showResult=false;
    var noRecipeFoundMessage='Order Takeout';
    $scope.fetchRecipeSuggestion = function() {
        $http.get('suggestion/recipeSuggestion').success(function(recipeSuggestion){
            
            if(recipeSuggestion == ''){
            	$scope.setError(noRecipeFoundMessage);
            	$scope.showResult=false;
            }else{
            	$scope.recipe = recipeSuggestion;
            	$scope.resetError();
            	$scope.showResult=true;
            }
        });
    };

    $scope.resetFridgeItemForm = function() {
        $scope.resetError();
        $scope.recipe ={};
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