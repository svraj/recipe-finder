package com.saj.recipefinder.service;

import com.saj.recipefinder.domain.Recipe;

import java.util.List;

/**
 * @author Sajan
 * @description Class contains methods for managing Recipes
 */
public interface RecipeService {
	
    public List<Recipe> getAllRecipes();

    public Recipe getRecipeById(Long id);

    public void addRecipe(Recipe recipe);

    public void deleteRecipeById(Long id);

    public void deleteAll();

    public void updateRecipe(Recipe recipe);

    /**
     * @description Adds the List of Recipes specified as Json List to the RecipeList managed by the service
     */
	public void addRecipesFromRecipeArray(Recipe[] recipeListJson);

    
}
