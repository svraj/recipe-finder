'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/getinput', {
        templateUrl: 'getinput/layout',
        controller: InputController
    });

    
    $routeProvider.when('/fridgeitems', {
        templateUrl: 'fridgeitems/layout',
        controller: FridgeItemController
    });
    
    
    $routeProvider.when('/recipes', {
        templateUrl: 'recipes/layout',
        controller: RecipeController
    });
    
    $routeProvider.when('/suggestion', {
        templateUrl: 'suggestion/layout',
        controller: SuggestionController
    });
    

   

    $routeProvider.otherwise({redirectTo: '/getinput'});
}]);
