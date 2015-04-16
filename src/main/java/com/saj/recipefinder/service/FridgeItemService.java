package com.saj.recipefinder.service;

import com.saj.recipefinder.domain.FridgeItem;

import java.io.IOException;
import java.util.List;

/**
 * @author Sajan
 * @Description Methods for Managing FridgeItems
 */
public interface FridgeItemService {

	public List<FridgeItem> getAllFridgeItems();

	public FridgeItem getFridgeItemById(Long id);

	public void addFridgeItem(FridgeItem fridgeItem);

	public void deleteFridgeItemById(Long id);

	public void deleteAll();

	public void updateFridgeItem(FridgeItem fridgeItem);

	/**
	 * @param itemsCSVList
	 *            - CSV List of FridgeItems
	 * 
	 */
	public void addFridgeItemsFromItemsCSVList(String itemsCSVList)
			throws IllegalArgumentException, IOException;

	/**
	 * @param includeToday - to include todays date for checking expiry
	 * @Description This method checks if the fridgeItem passed as parameter is
	 *              expired or not.
	 */
	public Boolean checkExpiryDate(FridgeItem fridgeItem, Boolean includeToday);

}
