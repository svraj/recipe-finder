'use strict';

/**
 * RecipeController
 * @constructor
 */


var RecipeController = function($scope, $http) {
    $scope.recipe = {};
    $scope.recipeJson = '';
    $scope.editMode = false;

    $scope.fetchRecipesList = function() {
        $http.get('recipes/recipeslist.json').success(function(recipeList){
            $scope.recipes = recipeList;
        });
    };
	
    $scope.addNewRecipe = function(recipe) {
        $scope.resetError();

        $http.post('recipes/addRecipe', recipe).success(function() {
            $scope.fetchRecipesList();
            $scope.recipe.name = '';
        }).error(function() {
            $scope.setError('Could not add a new recipe');
        });
    };
    
    $scope.submitRecipeJson = function(recipe) {
        $scope.resetError();

        $http.post('recipes/submitRecipeJson', recipe).success(function() {
            $scope.fetchRecipesList();
            $scope.recipeJson = '';
        }).error(function() {
            $scope.setError('Could not Submit Recipe Json List');
        });
    };

    $scope.updateRecipe = function(recipe) {
        $scope.resetError();

        $http.put('recipes/updateRecipe', recipe).success(function() {
            $scope.fetchRecipesList();
            $scope.recipe.name = '';
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the recipe');
        });
    };

    $scope.editRecipe = function(recipe) {
        $scope.resetError();
        $scope.recipe = recipe;
        $scope.editMode = true;
    };

    $scope.removeRecipe = function(id) {
        $scope.resetError();

        $http.delete('recipes/removeRecipe/' + id).success(function() {
            $scope.fetchRecipesList();
        }).error(function() {
            $scope.setError('Could not remove recipe');
        });
        $scope.recipe.name = '';
    };

    $scope.removeAllRecipes = function() {
        $scope.resetError();

        $http.delete('recipes/removeAllRecipes').success(function() {
            $scope.fetchRecipesList();
        }).error(function() {
            $scope.setError('Could not remove all recipes');
        });

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

    $scope.fetchRecipesList();

    $scope.predicate = 'id';
};