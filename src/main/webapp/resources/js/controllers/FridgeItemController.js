'use strict';

/**
 * FridgeItemController
 * @constructor
 */
var FridgeItemController = function($scope, $http,$filter) {
    $scope.fridgeItem = {};
    $scope.editMode = false;

    $scope.fetchFridgeItemsList = function() {
        $http.get('fridgeitems/fridgeItemslist.json').success(function(fridgeItemList){
            $scope.fridgeItems = fridgeItemList;
        });
    };
	
    $scope.addNewFridgeItem = function(fridgeItem) {
        $scope.resetError();

        $http.post('fridgeitems/addFridgeItem', fridgeItem).success(function() {
            $scope.fetchFridgeItemsList();
            $scope.fridgeItem.item = '';
            $scope.fridgeItem.amount = '';
            $scope.fridgeItem.unit = '';
            $scope.fridgeItem.useBy = '';
        }).error(function() {
            $scope.setError('Could not add a new fridgeItem');
        });
    };

    $scope.updateFridgeItem = function(fridgeItem) {
        $scope.resetError();

        $http.put('fridgeitems/updateFridgeItem', fridgeItem).success(function() {
            $scope.fetchFridgeItemsList();
            $scope.fridgeItem.item = '';
            $scope.fridgeItem.amount = '';
            $scope.fridgeItem.unit = '';
            $scope.fridgeItem.useBy = '';
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the fridgeItem');
        });
    };

    $scope.editFridgeItem = function(fridgeItem) {
        $scope.resetError();
        $scope.fridgeItem = fridgeItem;
        $scope.fridgeItem.useBy=$filter('date')($scope.fridgeItem.useBy,'dd/MM/yyyy');
        $scope.editMode = true;
    };

    $scope.removeFridgeItem = function(id) {
        $scope.resetError();

        $http.delete('fridgeitems/removeFridgeItem/' + id).success(function() {
            $scope.fetchFridgeItemsList();
        }).error(function() {
            $scope.setError('Could not remove fridgeItem');
        });
        $scope.fridgeItem.item = '';
        $scope.fridgeItem.amount = '';
        $scope.fridgeItem.unit = '';
        $scope.fridgeItem.useBy = '';
    };

    $scope.removeAllFridgeItems = function() {
        $scope.resetError();

        $http.delete('fridgeitems/removeAllFridgeItems').success(function() {
            $scope.fetchFridgeItemsList();
        }).error(function() {
            $scope.setError('Could not remove all fridgeItems');
        });

    };

    $scope.resetFridgeItemForm = function() {
        $scope.resetError();
        $scope.fridgeItem = {};
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

    $scope.fetchFridgeItemsList();

    $scope.predicate = 'id';
};