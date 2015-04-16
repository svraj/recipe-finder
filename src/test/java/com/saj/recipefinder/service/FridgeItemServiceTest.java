package com.saj.recipefinder.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saj.recipefinder.constants.RecipeFinderConstants;
import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.enums.Unit;

import static org.junit.Assert.*;

/**
 * @author Sajan
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-config.xml"})
public class FridgeItemServiceTest {
	
	@Autowired
	private FridgeItemService fridgeItemService;
	
	@Resource(name="recipeFinderProperties")
	private Properties recipeFinderProperties;
	
	@Test
	public void testAddFridgeItem(){
		fridgeItemService.deleteAll();
		FridgeItem fridgeItem = getDummyFridgeItem();
		fridgeItemService.addFridgeItem(fridgeItem);
		assertEquals(fridgeItem.getItem(), fridgeItemService.getAllFridgeItems().get(0).getItem());
	}
	
	
	@Test
	public void testUpdateFridgeItem(){
		fridgeItemService.deleteAll();
		FridgeItem fridgeItem = getDummyFridgeItem();
		fridgeItemService.addFridgeItem(fridgeItem);
		Double newAmount = 500d;
		fridgeItem= fridgeItemService.getAllFridgeItems().get(0);
		fridgeItem.setAmount(newAmount);
		fridgeItemService.updateFridgeItem(fridgeItem);
		fridgeItem= fridgeItemService.getAllFridgeItems().get(0);
		assertEquals(fridgeItem.getAmount(),newAmount);
	}
	
	@Test
	public void testDeleteFridgeItem(){
		fridgeItemService.deleteAll();
		FridgeItem fridgeItem = getDummyFridgeItem();
		fridgeItemService.addFridgeItem(fridgeItem);
		fridgeItem= fridgeItemService.getAllFridgeItems().get(0);
		Long fridgeItemId=fridgeItem.getId();
		fridgeItemService.deleteFridgeItemById(fridgeItemId);
		assertEquals(fridgeItemService.getAllFridgeItems().size(),0);
	}
	
	@Test
	public void testAddFridgeItemsFromItemsCSVList(){
		String itemsCSVList = (String) recipeFinderProperties.get("fridgeitems.sample");
		try {
			fridgeItemService.deleteAll();
			fridgeItemService.addFridgeItemsFromItemsCSVList(itemsCSVList);
			assertEquals(fridgeItemService.getAllFridgeItems().size(), 5);
		} catch (IllegalArgumentException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	public void testCheckExpiryDateForFridgeItem(){
		FridgeItem fridgeItem = getDummyFridgeItem();
		DateFormat dateFormat = new SimpleDateFormat(RecipeFinderConstants.RECIPE_FINDER_DATE_FORMAT);
		Date useBy=null;
		try {
			useBy = dateFormat.parse("12/05/2014");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fridgeItem.setUseBy(useBy);
		//Check for Previous Dates
		assertTrue(fridgeItemService.checkExpiryDate(fridgeItem, true));
		
		useBy = new Date();
		fridgeItem.setUseBy(useBy);
		//Test for Expiry including currentDate
		assertFalse(fridgeItemService.checkExpiryDate(fridgeItem, true));
		
		//Test for Expiry excluding currentDate
		assertTrue(fridgeItemService.checkExpiryDate(fridgeItem, false));
		
		try {
			useBy = dateFormat.parse("12/05/2150");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fridgeItem.setUseBy(useBy);
		
		//Test for Future Date
		assertFalse(fridgeItemService.checkExpiryDate(fridgeItem, true));
	}
	
	private FridgeItem getDummyFridgeItem(){
		FridgeItem fridgeItem = new FridgeItem();
		String itemName ="Sugar";
		fridgeItem.setItem(itemName);
		fridgeItem.setAmount(250d);
		fridgeItem.setUnit(Unit.GRAMS);
		fridgeItem.setUseBy(new Date());
		return fridgeItem;
	}
	

}
