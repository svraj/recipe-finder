package com.saj.recipefinder.service;

import java.util.Date;
import java.util.List;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.domain.Ingredient;
import com.saj.recipefinder.domain.Recipe;

/**
 * @author Sajan
 * @description Class containing methods for finding out the suggested recipe
 */
public interface RecipeSuggestionService {

	/**
	 * Suggests the Recipe based on the List of FridgeItems and Recipes. An
	 * ingredient that is past its use-by date will not be used for cooking. If
	 * more than one recipe is found, then preference will be given to the
	 * recipe with the closest use-by item
	 */
	public Recipe getSuggestedRecipe();

	/**
	 * Returns true if a valid FridgeItem corresponding to the Ingredient is present in the passed
	 * FridgeItem List. 
	 * 
	 * @param fridgeItems
	 *            - List of all FridgeItems
	 * @param ingredient
	 */
	public Boolean validIngredientInFridgeItems(List<FridgeItem> fridgeItems,Ingredient ingredient);

	/**
	 * Returns the Expiry Date of the FridgeItem corresponding to the Ingredient
	 * 
	 * @param fridgeItems
	 *            - List of all FridgeItems
	 * @param ingredient
	 */
	public Date getIngredientExpiryDate(List<FridgeItem> fridgeItems,
			Ingredient ingredient);

}
