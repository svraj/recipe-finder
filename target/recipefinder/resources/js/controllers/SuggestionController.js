'use strict';

/**
 * SuggestionController
 * @constructor
 */
var SuggestionController = function($scope, $http,$filter) {
    $scope.recipe = {};

    $scope.fetchRecipeSuggestion = function() {
        $http.get('suggestion/recipeSuggestion').success(function(recipeSuggestion){
            $scope.recipe = recipeSuggestion;
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