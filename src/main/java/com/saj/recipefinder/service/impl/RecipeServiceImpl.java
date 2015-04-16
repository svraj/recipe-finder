package com.saj.recipefinder.service.impl;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.domain.Ingredient;
import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.service.RecipeService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sajan
 *
 */
@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {
    private static List<Recipe> recipeList = new ArrayList<Recipe>();
    private static Long id = 0L;

    public List<Recipe> getAllRecipes() {
        return recipeList;
    }

    public Recipe getRecipeById(Long id) {
        return findRecipeById(id);
    }

    public void addRecipe(Recipe recipe) {
        recipe.setId(++id);
        recipeList.add(recipe);
    }

    public void deleteRecipeById(Long id) {
        Recipe foundRecipe = findRecipeById(id);
        if (foundRecipe != null) {
            recipeList.remove(foundRecipe);
            id--;
        }
    }

    public void deleteAll() {
        recipeList.clear();
        id = 0L;
    }

    public void updateRecipe(Recipe recipe) {
        Recipe foundRecipe = findRecipeById(recipe.getId());
        if (foundRecipe != null) {
            recipeList.remove(foundRecipe);
            recipeList.add(recipe);
        }
    }

    private Recipe findRecipeById(Long id) {
        for (Recipe recipe : recipeList) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }

        return null;
    }

	public void addRecipesFromRecipeArray(Recipe[] recipeListJson) {
		if(recipeListJson!=null){
			for(Recipe recipe:recipeListJson){
				addRecipe(recipe);
			}
		}
	}

	

	
}
