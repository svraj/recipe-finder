package com.saj.recipefinder.controller;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.service.FridgeItemService;
import com.saj.recipefinder.service.RecipeService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
/**
 * @author Sajan
 * @Description Controller for Input 
 */
@Controller
@RequestMapping("/getinput")
public class InputController {

    @Autowired
    private FridgeItemService fridgeItemService;
    
    @Autowired
    private RecipeService recipeService;
    
    private static final Logger LOGGER = Logger.getLogger(InputController.class);
 
    @RequestMapping(value = "/submitFridgeItemCSV", method = RequestMethod.POST)
    public @ResponseBody String submitFridgeItemCSV(@RequestBody String fridgeItemCSVList) {
    	String responseStatus="SUCCESS";
        try {
			fridgeItemService.addFridgeItemsFromItemsCSVList(fridgeItemCSVList);
		} catch (IllegalArgumentException e) {
			responseStatus="FAILED";
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			responseStatus="FAILED";
			LOGGER.error(e.getMessage());
		}
        LOGGER.debug("Response Status is "+responseStatus);
       return responseStatus;
    }
    
    @RequestMapping(value = "/submitRecipeJson", method = RequestMethod.POST)
    public @ResponseBody void submitRecipeJson(@RequestBody Recipe[] recipeListJson) {
    	recipeService.addRecipesFromRecipeArray(recipeListJson);
    }

    @RequestMapping("/layout")
    public String getFridgeItemPartialPage(ModelMap modelMap) {
        return "getinput/layout";
    }
}
