package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.util.Constants;

public class Package {
	
	private List<Integer> itemIndexes = new ArrayList<>();
	private double packageWeight = 0;
	private double maxWeight = 0;
	private double totalCost = 0;
	
	public Package(double maxWeight) throws APIException {
		if (maxWeight <= Constants.MAX_ALLOWED_WEIGHT) {			
			this.maxWeight = maxWeight;
		} else {
			throw new APIException("Max weight cannot exceed: " + Constants.MAX_ALLOWED_WEIGHT);
		}
	}

	public List<Integer> getItemIndexes() {
		return Collections.unmodifiableCollection(itemIndexes).stream().collect(Collectors.toList());
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public double getPackageWeight() {
		return packageWeight;
	}
	
	public boolean addItem(double weight, int index, double cost) {
		if (weight + packageWeight <= maxWeight) {
			itemIndexes.add(index);
			packageWeight += weight;
			totalCost += cost;
			return true;
		}
		return false;
	}
}
