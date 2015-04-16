package com.saj.recipefinder.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saj.recipefinder.domain.Ingredient;
import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.enums.Unit;

import static org.junit.Assert.*;

/**
 * @author Sajan
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-config.xml"})
public class RecipeServiceTest {
	
	@Autowired
	private RecipeService recipeService;
	
	@Resource(name="recipeFinderProperties")
	private Properties recipeFinderProperties;
	
	@Test
	public void testAddRecipe(){
		recipeService.deleteAll();
		Recipe recipe = getDummyRecipe();
		recipeService.addRecipe(recipe);
		assertEquals(recipe.getName(), recipeService.getAllRecipes().get(0).getName());
	}
	
	@Test
	public void testUpdateRecipe(){
		recipeService.deleteAll();
		Recipe recipe = getDummyRecipe();
		recipeService.addRecipe(recipe);
		String newName = "Chicken Hot Soup";
		recipe= recipeService.getAllRecipes().get(0);
		recipe.setName(newName);
		recipeService.updateRecipe(recipe);
		recipe= recipeService.getAllRecipes().get(0);
		assertEquals(newName,recipe.getName());
	}
	
	@Test
	public void testDeleteRecipe(){
		recipeService.deleteAll();
		Recipe recipe = getDummyRecipe();
		recipeService.addRecipe(recipe);
		recipe= recipeService.getAllRecipes().get(0);
		Long recipeId=recipe.getId();
		recipeService.deleteRecipeById(recipeId);
		assertEquals(recipeService.getAllRecipes().size(),0);
	}
	
	@Test
	public void testAddRecipesFromRecipeArray(){
			recipeService.deleteAll();
			recipeService.addRecipesFromRecipeArray(new Recipe[]{getDummyRecipe(),getDummyRecipe()});
			assertEquals(recipeService.getAllRecipes().size(), 2);
	}
	
	private Recipe getDummyRecipe(){
		Recipe recipe = new Recipe();
		String itemName ="Chicken Soup";
		recipe.setName(itemName);
		
		Ingredient ingredient1 = new Ingredient(0l, "Chicken", 200d, Unit.GRAMS);
		Ingredient ingredient2 = new Ingredient(1l, "Soup Powder", 50d, Unit.GRAMS);
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		
		recipe.setIngredients(ingredients);
		return recipe;
	}
	

}
