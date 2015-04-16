package com.saj.recipefinder.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.domain.Ingredient;
import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.service.FridgeItemService;
import com.saj.recipefinder.service.RecipeService;
import com.saj.recipefinder.service.RecipeSuggestionService;
/**
 * @author Sajan
 *
 */
@Service("recipeSuggestionService")
public class RecipeSuggestionServiceImpl implements RecipeSuggestionService{
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private FridgeItemService fridgeItemService;
	
	private static final Logger logger = Logger.getLogger(RecipeSuggestionServiceImpl.class);
	
	public Recipe getSuggestedRecipe() {
		List<FridgeItem> fridgeItems = fridgeItemService.getAllFridgeItems();
		List<Recipe> recipes = recipeService.getAllRecipes();
		return getBestSuggestedRecipe(fridgeItems,recipes);
	}
	
	/**
	 * Suggests the Recipe based on the List of FridgeItems and Recipes. 
	 */
	private Recipe getBestSuggestedRecipe(List<FridgeItem> fridgeItems,List<Recipe> recipes){
		Recipe suggestedRecipe = null;
		Map<Recipe, Date> foundRecipes = new HashMap<Recipe, Date>();
	    for (Recipe recipe : recipes) {
	      shortlistRecipes(foundRecipes, recipe, fridgeItems);
	    }
	    List<Map.Entry<Recipe, Date>> foundRecipesEntries =
	        new LinkedList<Map.Entry<Recipe, Date>>(foundRecipes.entrySet());

	    Collections.sort(foundRecipesEntries, new Comparator<Map.Entry<Recipe, Date>>() {
	    	public int compare(Entry<Recipe, Date> entry1, Entry<Recipe, Date> entry2) {
	    	  return entry1.getValue().compareTo(entry2.getValue());
		}
	    });

	    if (foundRecipesEntries!=null && !foundRecipesEntries.isEmpty()) {
	    	suggestedRecipe = foundRecipesEntries.get(0).getKey();
	    }
	    
	    return suggestedRecipe;
	}
	
	


	  /**
	   * If all ingredients in the passed Recipe (compared to corresponding fridgeItems)
	   * is valid and in stock, this recipe is shortlisted for the first level.
	   * Shortlisted Recipes are added to shortlistedRecipes Map
	   * 
	   * @param shortlistedRecipes
	   * @param recipe
	   * @param fridgeItems
	   */
	  private void shortlistRecipes(Map<Recipe, Date> shortlistedRecipes, Recipe recipe, List<FridgeItem> fridgeItems) {
		boolean allValidIngredients = true;
	    Date smallestUseBy = null;
	    Iterator<Ingredient> ingredientIterator = recipe.getIngredients().iterator();
	    while (ingredientIterator.hasNext() && allValidIngredients) {
	      Ingredient ingredient = ingredientIterator.next();
	      boolean validIngredient = validIngredientInFridgeItems(fridgeItems,ingredient);
	      if(validIngredient) {
	        Date useBy = getIngredientExpiryDate(fridgeItems, ingredient);
	       logger.debug("Ingredient -"+ingredient.getItem()+" Use By -"+useBy);
	        if (smallestUseBy == null || smallestUseBy.compareTo(useBy) > 0) {
	          smallestUseBy = useBy;
	        }
	      }else{
	    	  allValidIngredients=false;
	    	  logger.debug("Recipe -"+recipe.getName()+" does not have all valid ingredients");
	      }
	    }
	    if (allValidIngredients) {
	    	logger.debug("Recipe -"+recipe.getName()+" has all valid ingredients with lowest date-"+smallestUseBy+"\n");
	       shortlistedRecipes.put(recipe, smallestUseBy);
	    }
	  }


	  public Boolean validIngredientInFridgeItems(List<FridgeItem> fridgeItems, Ingredient ingredient) {
		  Boolean validIngredient =false;
		  for(FridgeItem fridgeItem: fridgeItems){
			  if(ingredient.getItem().equalsIgnoreCase(fridgeItem.getItem()) &&
					fridgeItem.getAmount()>= ingredient.getAmount() &&
						!fridgeItemService.checkExpiryDate(fridgeItem,true)){
				  validIngredient=true;
			  }
		  }
		  
		    return validIngredient;
		  }

	
		public Date getIngredientExpiryDate(List<FridgeItem> fridgeItems,Ingredient ingredient) {
			  Date useBy = null;
			  for(FridgeItem fridgeItem:fridgeItems){
				  if(fridgeItem.getItem().equalsIgnoreCase(ingredient.getItem())){
					  useBy = fridgeItem.getUseBy();
				  }
			  }
		    return useBy;
		  }
	
	
}
