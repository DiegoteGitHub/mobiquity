package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.util.Constants;

public class ItemsContainer {
	
	private List<Item> items;
	private double maxPackageWeight;
	
	public ItemsContainer(double maxPackageWeight) throws APIException {
		if (maxPackageWeight > Constants.MAX_ALLOWED_WEIGHT) {
			throw new APIException("Item list weight cannot exceed: " + Constants.MAX_ALLOWED_WEIGHT);
		}
		this.maxPackageWeight = maxPackageWeight;
		items = new ArrayList<>();
	}
	
	public double getMaxPackageWeight() {
		return maxPackageWeight;
	}
	
	public void addItem(Item item) throws APIException {
		if (items.size() < Constants.MAX_ALLOWED_ITEMS_PER_PACKAGE) {			
			items.add(item);
		} else {
			throw new APIException("Cannot add more than: " + Constants.MAX_ALLOWED_ITEMS_PER_PACKAGE + " items");
		}
	}
	
	public List<Item> getItems() {
		return Collections.unmodifiableCollection(items).stream().collect(Collectors.toList());
	}
}
