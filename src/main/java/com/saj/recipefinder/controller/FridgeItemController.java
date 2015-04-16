package com.saj.recipefinder.controller;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.service.FridgeItemService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Sajan
 * @Description Controller for FridgeItems
 */
@Controller
@RequestMapping("/fridgeitems")
public class FridgeItemController {

    @Autowired
    private FridgeItemService fridgeItemService;
    
    private static final Logger logger = Logger.getLogger(FridgeItemController.class);

    @RequestMapping("fridgeItemslist.json")
    public @ResponseBody List<FridgeItem> getFridgeItemList() {
        return fridgeItemService.getAllFridgeItems();
    }

    @RequestMapping(value = "/addFridgeItem", method = RequestMethod.POST)
    public @ResponseBody void addFridgeItem(@RequestBody FridgeItem fridgeItem) {
        fridgeItemService.addFridgeItem(fridgeItem);
    }
    
    @RequestMapping(value = "/addFridgeItems", method = RequestMethod.POST)
    public @ResponseBody void addFridgeItems(@RequestBody String fridgeItem) {
        try {
			fridgeItemService.addFridgeItemsFromItemsCSVList(fridgeItem);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
    }

    @RequestMapping(value = "/updateFridgeItem", method = RequestMethod.PUT)
    public @ResponseBody void updateFridgeItem(@RequestBody FridgeItem fridgeItem) {
        fridgeItemService.updateFridgeItem(fridgeItem);
    }

    @RequestMapping(value = "/removeFridgeItem/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeFridgeItem(@PathVariable("id") Long id) {
        fridgeItemService.deleteFridgeItemById(id);
    }

    @RequestMapping(value = "/removeAllFridgeItems", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllFridgeItems() {
        fridgeItemService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getFridgeItemPartialPage(ModelMap modelMap) {
        return "fridgeItems/layout";
    }
}
