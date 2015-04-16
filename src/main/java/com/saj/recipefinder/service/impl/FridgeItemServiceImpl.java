package com.saj.recipefinder.service.impl;

import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.service.FridgeItemService;
import com.saj.recipefinder.utils.FridgeItemsParser;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sajan
 * 
 */
@Service("fridgeItemService")
public class FridgeItemServiceImpl implements FridgeItemService {

	private static List<FridgeItem> fridgeItemList = new ArrayList<FridgeItem>();
	private static Long id = 0L;
	private static final Logger logger = Logger.getLogger(FridgeItemService.class);

	public List<FridgeItem> getAllFridgeItems() {
		return fridgeItemList;
	}

	public FridgeItem getFridgeItemById(Long id) {
		return findFridgeItemById(id);
	}

	public void addFridgeItem(FridgeItem fridgeItem) {
		logger.debug("Inside addFridgeItem");
		fridgeItem.setId(++id);
		fridgeItemList.add(fridgeItem);
	}

	public void deleteFridgeItemById(Long id) {
		FridgeItem foundFridgeItem = findFridgeItemById(id);
		if (foundFridgeItem != null) {
			fridgeItemList.remove(foundFridgeItem);
			id--;
		}
	}

	public void deleteAll() {
		fridgeItemList.clear();
		id = 0L;
	}

	public void updateFridgeItem(FridgeItem fridgeItem) {
		FridgeItem foundFridgeItem = findFridgeItemById(fridgeItem.getId());
		if (foundFridgeItem != null) {
			fridgeItemList.remove(foundFridgeItem);
			fridgeItemList.add(fridgeItem);
		}
	}

	private FridgeItem findFridgeItemById(Long id) {
		for (FridgeItem fridgeItem : fridgeItemList) {
			if (fridgeItem.getId() == id) {
				return fridgeItem;
			}
		}
		return null;
	}

	public void addFridgeItemsFromItemsCSVList(String itemsCSVList)
			throws IllegalArgumentException, IOException {
		List<FridgeItem> fridgeItems = FridgeItemsParser
				.parseFridgeItems(itemsCSVList);
		logger.debug(fridgeItems);
		for (FridgeItem fridgeItem : fridgeItems) {
			addFridgeItem(fridgeItem);
		}
	}

	public Boolean checkExpiryDate(FridgeItem fridgeItem, Boolean includeToday) {
		logger.debug("Checking Expiry Date for -" + fridgeItem.getItem()
				+ fridgeItem.getUseBy());
		Boolean dateExpired = false;
		Date currentDay = new Date();

		if (DateUtils.isSameDay(fridgeItem.getUseBy(), currentDay)) {
			logger.debug("Today is Expiry Day for " + fridgeItem.getItem());
			if (includeToday) {
				dateExpired = false;
			} else {
				dateExpired = true;
			}

		} else if (fridgeItem.getUseBy().before(currentDay)) {
			dateExpired = true;
		} else {
			dateExpired = false;
		}

		return dateExpired;
	}

}
