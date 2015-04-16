package com.saj.recipefinder.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saj.recipefinder.constants.RecipeFinderConstants;
import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.domain.Ingredient;
import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.enums.Unit;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public class RecipeSuggestionTest {

	@Autowired
	private RecipeSuggestionService recipeSuggestionService;

	@Autowired
	private FridgeItemService fridgeItemService;

	@Autowired
	private RecipeService recipeService;
	
	@Resource(name="recipeFinderProperties")
	private Properties recipeFinderProperties;

	@Test
	public void testContainsUsuableIngredient() {
		// Clear Fridge and add one item
		fridgeItemService.deleteAll();
		FridgeItem fridgeItem = getDummyFridgeItem();
		fridgeItemService.addFridgeItem(fridgeItem);

		// Test with invalid Amount
		Ingredient ingredient = new Ingredient(0l, "Chicken", 200d, Unit.GRAMS);
		assertFalse(recipeSuggestionService.validIngredientInFridgeItems(
				fridgeItemService.getAllFridgeItems(), ingredient));

		// Test with Valid Amount
		Ingredient ingredient2 = new Ingredient(0l, "Chicken", 50d, Unit.GRAMS);
		assertTrue(recipeSuggestionService.validIngredientInFridgeItems(
				fridgeItemService.getAllFridgeItems(), ingredient2));
	}

	@Test
	public void testGetIngredientUseBy() {
		// Clear Fridge and add one item
		fridgeItemService.deleteAll();
		FridgeItem fridgeItem = getDummyFridgeItem();
		fridgeItemService.addFridgeItem(fridgeItem);
		Ingredient ingredient = new Ingredient(0l, "Chicken", 200d, Unit.GRAMS);
		
		Date useBy = recipeSuggestionService.getIngredientExpiryDate(fridgeItemService.getAllFridgeItems(), ingredient);
		assertTrue(DateUtils.isSameDay(useBy, new Date()));

	}
	
	@Test
	public void testGetSuggestedRecipe() {
		//Add recipes using JSON 
		recipeService.deleteAll();
		Recipe recipeForGrilledCheese = getDummyRecipeForGrilledCheese();
		Recipe recipeForSaladSandwich = getDummyRecipeForSaladSandwich();
		recipeService.addRecipe(recipeForGrilledCheese);
		recipeService.addRecipe(recipeForSaladSandwich);
		
		fridgeItemService.addFridgeItem(getDummyBreadFridgeItem());
		fridgeItemService.addFridgeItem(getDummyCheeseFridgeItem());
		fridgeItemService.addFridgeItem(getDummyButterFridgeItem());
		fridgeItemService.addFridgeItem(getDummyPeanutButterFridgeItem());
		fridgeItemService.addFridgeItem(getDummyMixedSaladFridgeItem());
		
		Recipe suggestedRecipe = recipeSuggestionService.getSuggestedRecipe();
		assertNotNull(suggestedRecipe);
		assertEquals(recipeForGrilledCheese.getName(), suggestedRecipe.getName());
		
	}

	private Recipe getDummyRecipeForSaladSandwich() {
		Recipe recipe = new Recipe();
		String itemName = "Salad Sandwich";
		recipe.setName(itemName);

		Ingredient ingredient1 = new Ingredient(0l, "Bread", 2d, Unit.SLICES);
		Ingredient ingredient2 = new Ingredient(1l, "Mized Salad", 100d,Unit.GRAMS);
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);

		recipe.setIngredients(ingredients);
		return recipe;
	}

	private Recipe getDummyRecipeForGrilledCheese() {
		Recipe recipe = new Recipe();
		String itemName = "Grilled Cheese on Toast";
		recipe.setName(itemName);

		Ingredient ingredient1 = new Ingredient(0l, "Bread", 2d, Unit.SLICES);
		Ingredient ingredient2 = new Ingredient(1l, "Cheese", 2d,Unit.SLICES);
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);

		recipe.setIngredients(ingredients);
		return recipe;
	}

	private FridgeItem getDummyFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Chicken";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(75d);
		fridgeItem.setUnit(Unit.GRAMS);
		fridgeItem.setUseBy(new Date());
		return fridgeItem;
	}
	
	private FridgeItem getDummyBreadFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Bread";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(10d);
		fridgeItem.setUnit(Unit.SLICES);
		fridgeItem.setUseBy(new Date());
		return fridgeItem;
	}
	
	private FridgeItem getDummyCheeseFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Cheese";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(10d);
		fridgeItem.setUnit(Unit.SLICES);
		fridgeItem.setUseBy(new Date());
		return fridgeItem;
	}
	
	private FridgeItem getDummyMixedSaladFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Mixed Salad";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(10d);
		fridgeItem.setUnit(Unit.SLICES);
		fridgeItem.setUseBy(new Date());
		return fridgeItem;
	}
	
	private FridgeItem getDummyButterFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Butter";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(250d);
		fridgeItem.setUnit(Unit.GRAMS);
		Date useBy = null;
		DateFormat dateFormat = new SimpleDateFormat(RecipeFinderConstants.RECIPE_FINDER_DATE_FORMAT);
		try {
			useBy = dateFormat.parse("25/12/2014");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fridgeItem.setUseBy(useBy);
		return fridgeItem;
	}
	
	private FridgeItem getDummyPeanutButterFridgeItem() {
		FridgeItem fridgeItem = new FridgeItem();
		String itemName = "Peanut Butter";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(250d);
		fridgeItem.setUnit(Unit.GRAMS);
		Date useBy = null;
		DateFormat dateFormat = new SimpleDateFormat(RecipeFinderConstants.RECIPE_FINDER_DATE_FORMAT);
		try {
			useBy = dateFormat.parse("2/12/2014");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fridgeItem.setUseBy(useBy);
		return fridgeItem;
	}



}
