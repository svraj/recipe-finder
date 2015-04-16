package com.saj.recipefinder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.opencsv.CSVReader;
import com.saj.recipefinder.constants.RecipeFinderConstants;
import com.saj.recipefinder.domain.FridgeItem;
import com.saj.recipefinder.enums.Unit;
/**
 * @author Sajan
 *
 */
public class FridgeItemsParser {

    private static final Logger logger =  Logger.getLogger(FridgeItemsParser.class);
   
    private FridgeItemsParser() {}
    
    public static List<FridgeItem> parseFridgeItems(String items) throws IllegalArgumentException, IOException {
    	logger.debug("Items is "+items);
        List<FridgeItem> fridgeItems = new ArrayList<FridgeItem>();
        CSVReader csvReader = new CSVReader(new StringReader(items));
        String [] nextRow;
        
        while ((nextRow = csvReader.readNext()) != null) {
        	logger.debug("next row is -");
        	printItem(nextRow);
        	FridgeItem fridgeItem = parseFridgeItem(nextRow);
        	fridgeItems.add(fridgeItem);
        }
        
        return fridgeItems;
    }
    
    private static void printItem(String [] itemCSV){
    	if(itemCSV!=null){
    		for(int i=0;i<itemCSV.length;i++){
    			logger.debug(itemCSV[i]);
    		}
    	}else{
    		logger.debug("itemCSV is null");
    	}
    }
    
    public static FridgeItem parseFridgeItem(String[] itemCSV) throws IllegalArgumentException{

    	if(validate(itemCSV)){
    		String itemName = itemCSV[0].trim();
    		Double amount = getAmount(itemCSV[1].trim());
    		Unit unit = getUnit(itemCSV[2].trim());
    		Date useByDate;
			try {
				useByDate = getDate(itemCSV[3].trim());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new IllegalArgumentException("Bad format for Fridge Item! Bad Date Format -"+e.getMessage());
			}
    		return new FridgeItem(itemName,amount,unit,useByDate);
    	}else{
    		logger.debug("Validation Failed for row-");
        	printItem(itemCSV);
    		throw new IllegalArgumentException("Bad format for Fridge Item!!!");
    	}
        
    }

    private static boolean validate(String[] itemCSV) {
    	logger.debug("Inside Validate, ItemCSV length is  "+itemCSV.length);
    	printItem(itemCSV);
    	boolean validate = false;
    	if(itemCSV!=null && itemCSV.length==4){
    		validate = true;
    	}
    	
    	return validate;
	}

	private static Double getAmount(String amount) {
        return Double.parseDouble(amount);
    }

    private static Unit getUnit(String unit) {
        return Unit.valueOf(unit.toUpperCase());
    }

    private static Date getDate(String date) throws ParseException {
    	Date formattedDate = null;
        DateFormat simpleDateFormat = new SimpleDateFormat(RecipeFinderConstants.RECIPE_FINDER_DATE_FORMAT);
        formattedDate = simpleDateFormat.parse(date);
        return formattedDate;
    }

}
