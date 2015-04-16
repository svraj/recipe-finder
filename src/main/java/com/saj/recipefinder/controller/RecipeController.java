package com.saj.recipefinder.controller;

import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sajan
 * @Description Controller for Recipes
 */
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping("recipeslist.json")
    public @ResponseBody List<Recipe> getRecipeList() {
        return recipeService.getAllRecipes();
    }
    
    
    
   

    @RequestMapping(value = "/addRecipe", method = RequestMethod.POST)
    public @ResponseBody void addRecipe(@RequestBody Recipe recipe) {
    	System.out.println("Inside addRecipe");
        recipeService.addRecipe(recipe);
    }

    @RequestMapping(value = "/updateRecipe", method = RequestMethod.PUT)
    public @ResponseBody void updateRecipe(@RequestBody Recipe recipe) {
        recipeService.updateRecipe(recipe);
    }

    @RequestMapping(value = "/removeRecipe/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipeById(id);
    }

    @RequestMapping(value = "/removeAllRecipes", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllRecipes() {
        recipeService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getRecipePartialPage(ModelMap modelMap) {
        return "recipes/layout";
    }
}
