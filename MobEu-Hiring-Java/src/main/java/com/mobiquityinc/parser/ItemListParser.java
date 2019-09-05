package com.mobiquityinc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.ItemsContainer;

public class ItemListParser {

	public static List<ItemsContainer> parse(String filePath) throws APIException {
		File f = new File(filePath);
		List<ItemsContainer> allLineItems = new ArrayList<>();
		int lineNum = 1;
        try (BufferedReader b = new BufferedReader(new FileReader(f))) {			
			String readLine = "";
			while ((readLine = b.readLine()) != null) {
                ItemsContainer lineItems = parseLine(readLine, lineNum);
                allLineItems.add(lineItems);
                lineNum++;
            }
		} catch (FileNotFoundException e) {
			throw new APIException("File: " + filePath + " not found");
		} catch (IOException e) {
			throw new APIException("IOException reading file: " + filePath);
		}
		return allLineItems;
	}

	private static ItemsContainer parseLine(String readLine, int lineNum) throws APIException {
		String[] lineContents = readLine.split(":");
		if (lineContents.length != 2) {
			throw new APIException(
					"Line format should be => <max package weight> : (<item index>,<item weight>,<item cost>");
		}
		ItemsContainer itemList = new ItemsContainer(Double.valueOf(lineContents[0]));
		List<Item> items = parseItems(lineContents[1], lineNum);
		for (Item item: items) {
			itemList.addItem(item);
		}
		return itemList;
	}

	private static List<Item> parseItems(String itemsString, int lineNum) throws APIException {
		/* Input line format should be
		 * <MaxPkgWeight> : (<item1 index>,<item1 weight>,<item1 cost>) (<item2 index>,<item2 weight>,<item2 cost>) 
		 * and so on*/
		List<Item> items = new ArrayList<>();
		String[] itemsArray = itemsString.split(" ");
		for (String itemStr : itemsArray) {
			if (!itemStr.isEmpty()) {				
				itemStr = itemStr.replace("(", "").replace(")", "").replace("€", "");
				String[] itemWeightCost = itemStr.split(",");
				if (itemWeightCost.length != 3) {
					throw new APIException("Invalid data at line number: " + lineNum);
				}
				Item item = getItem(itemWeightCost, lineNum);
				items.add(item);
				// Sort items by cost in descending order
				items = items.stream()
						.sorted(Comparator.comparingDouble(Item::getCost).reversed()
								.thenComparing(Comparator.comparingDouble(Item::getWeight)))
						.collect(Collectors.toList());
			}
		}
		return items;
	}

	private static Item getItem(String[] itemWeightCost, int lineNum) throws APIException {
		Item item = new Item();
		try {
			item.setIndex(Integer.valueOf(itemWeightCost[0]));
			item.setWeight(Double.valueOf(itemWeightCost[1]));
			item.setCost(Integer.valueOf(itemWeightCost[2]));
		} catch (NumberFormatException e) {
			throw new APIException("Error in parsing item parameters at line number: " + lineNum);
		}
		return item;
	}

}
