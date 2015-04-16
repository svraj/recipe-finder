package com.saj.recipefinder.controller;

import com.saj.recipefinder.domain.Recipe;
import com.saj.recipefinder.service.RecipeSuggestionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
/**
 * @author Sajan
 * @Description Controller for Recipe Suggestion
 */
@Controller
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private RecipeSuggestionService recipeSuggestionService;
    
    final static Logger logger = Logger.getLogger(SuggestionController.class);

    @RequestMapping("/recipeSuggestion")
    public @ResponseBody Recipe getRecipeSuggestion() {
    	logger.debug("Inside getRecipeSuggestion()");
    	Recipe suggestedRecipe = null;
    	suggestedRecipe = recipeSuggestionService.getSuggestedRecipe();
    	if(suggestedRecipe!=null){
    		logger.debug("Recepie suggestion is-"+suggestedRecipe);
    	}else{
    		logger.debug("Recepie suggestion is-bull");
    	}
    	
        return recipeSuggestionService.getSuggestedRecipe();
    }
    

    @RequestMapping("/layout")
    public String getRecipePartialPage(ModelMap modelMap) {
        return "suggestion/layout";
    }
}
